package com.mttbrt.digres.api;

import static com.mttbrt.digres.utils.StaticVariables.AUTH_NAMESPACE;
import static com.mttbrt.digres.utils.StaticVariables.LOGIN_ENDPOINT;
import static com.mttbrt.digres.utils.StaticVariables.LOGOUT_ENDPOINT;
import static com.mttbrt.digres.utils.StaticVariables.REGISTER_ENDPOINT;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mttbrt.digres.domain.Authority;
import com.mttbrt.digres.domain.User;
import com.mttbrt.digres.dto.request.UserReqDTO;
import com.mttbrt.digres.dto.response.ErrorResDTO;
import com.mttbrt.digres.dto.response.ResponseDTO;
import com.mttbrt.digres.dto.response.SingleErrorResDTO;
import com.mttbrt.digres.dto.response.item.UserResDTO;
import com.mttbrt.digres.dto.response.item.UsersResDTO;
import com.mttbrt.digres.repository.AuthorityDao;
import com.mttbrt.digres.repository.UserDao;
import com.mttbrt.digres.utils.JWTHelper;
import java.util.List;
import java.util.Set;
import javax.servlet.http.Cookie;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.util.Base64Utils;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
public class AuthControllerIT {

  private static final String registerEndpoint = AUTH_NAMESPACE + REGISTER_ENDPOINT;
  private static final String logoutEndpoint = AUTH_NAMESPACE + LOGOUT_ENDPOINT;
  private static final String loginEndpoint = AUTH_NAMESPACE + LOGIN_ENDPOINT;

  private static String JWT_COOKIE_NAME;
  private static String CSRF_COOKIE_NAME;

  private static Cookie adminJWTCookie;
  private static Cookie userJWTCookie;
  private static Authority staffAuthority;

  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ObjectMapper objectMapper;
  @Autowired
  private UserDao userDao;

  @BeforeAll
  public static void init(@Autowired JWTHelper jwtHelper,
      @Autowired AuthorityDao authorityDao,
      @Value("${csrf.cookie.name}") String csrfCookieName,
      @Value("${jwt.cookie.name}") String jwtCookieName,
      @Value("${jwt.cookie.path}") String jwtCookiePath,
      @Value("${jwt.cookie.domain}") String jwtCookieDomain,
      @Value("${jwt.cookie.maxAge}") int jwtCookieMaxAge) {
    CSRF_COOKIE_NAME = csrfCookieName;
    JWT_COOKIE_NAME = jwtCookieName;

    String adminJWT = jwtHelper.generateJwt("admin");
    adminJWTCookie = jwtHelper.prepareCookie(jwtCookieName, adminJWT, jwtCookieDomain,
        jwtCookiePath, true, jwtCookieMaxAge);

    String userJWT = jwtHelper.generateJwt("user");
    userJWTCookie = jwtHelper.prepareCookie(jwtCookieName, userJWT, jwtCookieDomain,
        jwtCookiePath, true, jwtCookieMaxAge);

    Authority staffAuth = authorityDao.findByName("ROLE_STAFF");
    assert staffAuth != null;
    staffAuthority = staffAuth;
  }

  @Test
  public void register_new_user_successfully() throws Exception {
    UserReqDTO req = createRegisterRequest("test", "password", Set.of(staffAuthority));
    ResponseDTO res = new ResponseDTO(
        new UsersResDTO(List.of(new UserResDTO(req.getUsername(), req.getRoles()))));

    performPostRequest(registerEndpoint, adminJWTCookie, objectMapper.writeValueAsString(req),
        objectMapper.writeValueAsString(res), status().isCreated());

    User addedUser = userDao.findByUsername(req.getUsername());
    assertNotNull(addedUser);
  }

  @Test
  public void register_existing_user() throws Exception {
    UserReqDTO req = createRegisterRequest("user", "password", Set.of(staffAuthority));
    ResponseDTO res = new ResponseDTO(
        new ErrorResDTO(HttpStatus.BAD_REQUEST.value(), "User already exists.",
            List.of(new SingleErrorResDTO("An account with the given username already exists.",
                registerEndpoint))));

    performPostRequest(registerEndpoint, adminJWTCookie, objectMapper.writeValueAsString(req),
        objectMapper.writeValueAsString(res), status().isBadRequest());
  }

  @Test
  public void register_user_as_non_admin() throws Exception {
    UserReqDTO req = createRegisterRequest("user", "password", Set.of(staffAuthority));

    performPostRequest(registerEndpoint, userJWTCookie, objectMapper.writeValueAsString(req), null,
        status().isForbidden());
  }

  @Test
  public void register_user_with_malformed_input() throws Exception {
    UserReqDTO req = createRegisterRequest("user", "password", null);
    ResponseDTO res = new ResponseDTO(
        new ErrorResDTO(HttpStatus.BAD_REQUEST.value(), "Incorrect request parameters.",
            List.of(
                new SingleErrorResDTO("must not be blank", "password"),
                new SingleErrorResDTO("At least one role must be provided.", "roles"),
                new SingleErrorResDTO("must not be blank", "username")
            )
        ));

    performPostRequest(registerEndpoint, userJWTCookie,
        objectMapper.writeValueAsString(req)
            .replace("username", "usr")
            .replace("password", "psw"),
        objectMapper.writeValueAsString(res), status().isBadRequest());
  }

  @Test
  public void logout_user_successfully() throws Exception {
    mockMvc.perform(post(logoutEndpoint)
            .with(csrf())
            .cookie(userJWTCookie))
        .andExpect(status().isOk())
        .andExpect(cookie().maxAge(JWT_COOKIE_NAME, 0))
        .andExpect(cookie().value(JWT_COOKIE_NAME, (String) null))
        .andExpect(cookie().maxAge(CSRF_COOKIE_NAME, 0))
        .andExpect(cookie().value(CSRF_COOKIE_NAME, (String) null));
  }

  @Test
  public void logout_non_logged_user() throws Exception {
    ResponseDTO res = new ResponseDTO(
        new ErrorResDTO(HttpStatus.BAD_REQUEST.value(), "Request not valid.",
            List.of(new SingleErrorResDTO("The request is not valid.", ""))
        ));

    mockMvc.perform(post(logoutEndpoint).with(csrf()))
        .andExpect(status().isBadRequest())
        .andExpect(content().json(objectMapper.writeValueAsString(res)));
  }

  @Test
  public void login_existing_user() throws Exception {
    mockMvc.perform(post(loginEndpoint)
            .header(HttpHeaders.AUTHORIZATION, "Basic " + Base64Utils.encodeToString("user:password".getBytes())))
        .andExpect(status().isOk())
        .andExpect(cookie().exists(JWT_COOKIE_NAME))
        .andExpect(cookie().httpOnly(JWT_COOKIE_NAME, true));
  }

  @Test
  public void login_non_existing_user() throws Exception {
    mockMvc.perform(post(loginEndpoint)
            .header(HttpHeaders.AUTHORIZATION, "Basic " + Base64Utils.encodeToString("user1:password".getBytes())))
        .andExpect(status().isUnauthorized());
  }

  // TODO:
  // - test login method [DONE]
  // - improve errors' message and location
  // - write little docs in readme merge into dev and master
  // new branch:
  // - users controller: addNewUser (which will be the same as register), deleteUser, addUserRole, removeUserRole

  private UserReqDTO createRegisterRequest(String username, String password,
      Set<Authority> authorities) {
    User user = new User(username, password, authorities);
    return new UserReqDTO(user.getUsername(), user.getPassword(), user.getRoles());
  }

  private void performPostRequest(String endpoint, Cookie jwtCookie, String reqContent,
      String expectedContent, ResultMatcher expectedStatus)
      throws Exception {
    mockMvc.perform(post(endpoint)
            .with(csrf())
            .cookie(jwtCookie)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(reqContent))
        .andExpect(expectedStatus)
        .andExpect(
            expectedContent != null ? content().json(expectedContent) : content().string(""));
  }

}