package com.mttbrt.digres.api;

import static com.mttbrt.digres.utils.StaticVariables.AUTH_ENDPOINT;
import static com.mttbrt.digres.utils.StaticVariables.REGISTER_ENDPOINT;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.mttbrt.digres.dto.request.RegisterUserDTO;
import com.mttbrt.digres.dto.response.ResponseDTO;
import com.mttbrt.digres.dto.response.item.UserDTO;
import com.mttbrt.digres.dto.response.item.UsersDTO;
import com.mttbrt.digres.service.AuthService;
import com.mttbrt.digres.utils.JWTHelper;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.context.event.annotation.BeforeTestExecution;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;

@WebMvcTest(AuthController.class)
@MockBeans({@MockBean(AuthService.class), @MockBean(JWTHelper.class)})
public class AuthControllerTest {

  @Autowired
  private AuthService authService;
  @Autowired
  private MockMvc mvc;

  @Test
  @WithMockUser(roles = "STAFF") // TODO: check why it works also with roles="STAFF"
  void register_new_user() throws Exception {
    final String username = "user1";
    final String password = "password";
    final Set<String> roles = Set.of("ROLE_STAFF");
    final ObjectWriter writer = new ObjectMapper().writer();

    final RegisterUserDTO req = new RegisterUserDTO(username, password, roles);
    final String reqJSON = writer.writeValueAsString(req);

    final ResponseDTO res = new ResponseDTO(new UsersDTO(List.of(new UserDTO(username, roles))));
    final String resJSON = writer.writeValueAsString(res);

    when(authService.registerUser(any(RegisterUserDTO.class))).thenReturn(res);

    mvc.perform(MockMvcRequestBuilders
            .post(AUTH_ENDPOINT + REGISTER_ENDPOINT)
            .with(csrf().asHeader())
            .contentType(MediaType.APPLICATION_JSON)
            .content(reqJSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().json(resJSON));
  }

}
