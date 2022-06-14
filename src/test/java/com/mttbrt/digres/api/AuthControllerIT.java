package com.mttbrt.digres.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mttbrt.digres.domain.Authority;
import com.mttbrt.digres.domain.User;
import com.mttbrt.digres.dto.request.RegisterUserDTO;
import com.mttbrt.digres.repository.AuthorityDao;
import com.mttbrt.digres.repository.UserDao;
import com.mttbrt.digres.utils.JWTHelper;
import java.util.Set;
import javax.servlet.http.Cookie;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerIT {

  @Value("${jwt.cookie.name}")
  private String JWT_COOKIE_NAME;
  @Value("${jwt.cookie.path}")
  private String JWT_COOKIE_PATH;
  @Value("${jwt.cookie.domain}")
  private String JWT_COOKIE_DOMAIN;
  @Value("${jwt.cookie.maxAge}")
  private int JWT_COOKIE_MAXAGE;

  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ObjectMapper objectMapper;
  @Autowired
  private JWTHelper jwtHelper;
  @Autowired
  private UserDao userDao;

  @Test
  public void test1() throws Exception {
    RegisterUserDTO req = new RegisterUserDTO("test", "password", Set.of("ROLE_STAFF"));

    String jwt = jwtHelper.generateJwt("admin");
    Cookie jwtCookie = jwtHelper.prepareCookie(JWT_COOKIE_NAME, jwt, JWT_COOKIE_DOMAIN, JWT_COOKIE_PATH, true,
        JWT_COOKIE_MAXAGE);

    mockMvc.perform(post("/api/v1/auth/register")
            .with(csrf())
            .cookie(jwtCookie)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(req)))
        .andExpect(status().isCreated())
        .andExpect(content().json("{\"apiVersion\":\"v1\",\"data\":{\"users\":[{\"username\":\"test\",\"roles\":[\"ROLE_STAFF\"]}]}}"));

    User foundUser = userDao.findByUsername("test");
    assertNotNull(foundUser);
  }

}