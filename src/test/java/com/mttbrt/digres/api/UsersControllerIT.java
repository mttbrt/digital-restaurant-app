package com.mttbrt.digres.api;

import static com.mttbrt.digres.utils.StaticVariables.USERS_ENDPOINT;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mttbrt.digres.domain.Authority;
import com.mttbrt.digres.domain.User;
import com.mttbrt.digres.dto.request.AddUserReqDTO;
import com.mttbrt.digres.dto.response.ResponseDTO;
import com.mttbrt.digres.dto.response.error.ErrorResDTO;
import com.mttbrt.digres.dto.response.error.SingleErrorResDTO;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
public class UsersControllerIT {

  // TODO: to complete test cases

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
  public void add_new_user_successfully() throws Exception {
//    AddUserReqDTO req = createRegisterRequest("test", "password", Set.of(staffAuthority));
//    ResponseDTO res = new ResponseDTO(
//        new UsersResDTO(List.of(new UserResDTO(req.getUsername(), req.getRoles()))));
//
//    performPostRequest(USERS_ENDPOINT, adminJWTCookie, objectMapper.writeValueAsString(req),
//        objectMapper.writeValueAsString(res), status().isCreated());
//
//    User addedUser = userDao.findByUsername(req.getUsername());
//    assertNotNull(addedUser);
  }

  @Test
  public void add_existing_user() throws Exception {
    AddUserReqDTO req = createRegisterRequest("user", "password", Set.of(staffAuthority));
    ResponseDTO res = new ResponseDTO(
        new ErrorResDTO(HttpStatus.BAD_REQUEST.value(), "User already exists.",
            List.of(new SingleErrorResDTO("An account with the given username already exists.",
                USERS_ENDPOINT))));

    performPostRequest(USERS_ENDPOINT, adminJWTCookie, objectMapper.writeValueAsString(req),
        objectMapper.writeValueAsString(res), status().isBadRequest());
  }

  @Test
  public void add_user_as_non_admin() throws Exception {
    AddUserReqDTO req = createRegisterRequest("user", "password", Set.of(staffAuthority));

    performPostRequest(USERS_ENDPOINT, userJWTCookie, objectMapper.writeValueAsString(req), null,
        status().isForbidden());
  }

  @Test
  public void add_user_with_malformed_input() throws Exception {
    AddUserReqDTO req = createRegisterRequest("user", "password", null);
    ResponseDTO res = new ResponseDTO(
        new ErrorResDTO(HttpStatus.BAD_REQUEST.value(), "Incorrect request parameters.",
            List.of(
                new SingleErrorResDTO("must not be blank", "password"),
                new SingleErrorResDTO("At least one role must be provided.", "roles"),
                new SingleErrorResDTO("must not be blank", "username")
            )
        ));

    performPostRequest(USERS_ENDPOINT, userJWTCookie,
        objectMapper.writeValueAsString(req)
            .replace("username", "usr")
            .replace("password", "psw"),
        objectMapper.writeValueAsString(res), status().isBadRequest());
  }

  private AddUserReqDTO createRegisterRequest(String username, String password,
      Set<Authority> authorities) {
    User user = new User(username, password, authorities);
    return new AddUserReqDTO(user.getUsername(), user.getPassword(), user.getRoles());
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