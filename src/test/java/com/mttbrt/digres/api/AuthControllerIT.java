package com.mttbrt.digres.api;

import static com.mttbrt.digres.utils.StaticVariables.LOGIN_ENDPOINT;
import static com.mttbrt.digres.utils.StaticVariables.LOGOUT_ENDPOINT;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mttbrt.digres.dto.response.ResponseDTO;
import com.mttbrt.digres.dto.response.error.ErrorResDTO;
import com.mttbrt.digres.dto.response.error.SingleErrorResDTO;
import com.mttbrt.digres.utils.JWTHelper;
import java.util.List;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.Base64Utils;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
public class AuthControllerIT {

  private static String JWT_COOKIE_NAME;
  private static String CSRF_COOKIE_NAME;

  private static Cookie userJWTCookie;

  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ObjectMapper objectMapper;

  @BeforeAll
  public static void init(@Autowired JWTHelper jwtHelper,
      @Value("${csrf.cookie.name}") String csrfCookieName,
      @Value("${jwt.cookie.name}") String jwtCookieName,
      @Value("${jwt.cookie.path}") String jwtCookiePath,
      @Value("${jwt.cookie.domain}") String jwtCookieDomain,
      @Value("${jwt.cookie.maxAge}") int jwtCookieMaxAge) {
    CSRF_COOKIE_NAME = csrfCookieName;
    JWT_COOKIE_NAME = jwtCookieName;

    String userJWT = jwtHelper.generateJwt("user");
    userJWTCookie = jwtHelper.prepareCookie(jwtCookieName, userJWT, jwtCookieDomain,
        jwtCookiePath, true, jwtCookieMaxAge);
  }

  @Test
  public void login_existing_user() throws Exception {
    mockMvc.perform(post(LOGIN_ENDPOINT)
            .header(HttpHeaders.AUTHORIZATION,
                "Basic " + Base64Utils.encodeToString("user:password".getBytes())))
        .andExpect(status().isOk())
        .andExpect(cookie().exists(JWT_COOKIE_NAME))
        .andExpect(cookie().httpOnly(JWT_COOKIE_NAME, true));
  }

  @Test
  public void login_non_existing_user() throws Exception {
    mockMvc.perform(post(LOGIN_ENDPOINT)
            .header(HttpHeaders.AUTHORIZATION,
                "Basic " + Base64Utils.encodeToString("user1:password".getBytes())))
        .andExpect(status().isUnauthorized());
  }

  @Test
  public void logout_user_successfully() throws Exception {
    mockMvc.perform(post(LOGOUT_ENDPOINT)
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

    mockMvc.perform(post(LOGOUT_ENDPOINT).with(csrf()))
        .andExpect(status().isBadRequest())
        .andExpect(content().json(objectMapper.writeValueAsString(res)));
  }

}