package com.mttbrt.digres.service.impl;

import static com.mttbrt.digres.utils.StaticVariables.AUTH_ENDPOINT;
import static com.mttbrt.digres.utils.StaticVariables.REGISTER_ENDPOINT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.mttbrt.digres.domain.User;
import com.mttbrt.digres.dto.request.RegisterUserDTO;
import com.mttbrt.digres.dto.response.ErrorDTO;
import com.mttbrt.digres.dto.response.ResponseDTO;
import com.mttbrt.digres.dto.response.SingleErrorDTO;
import com.mttbrt.digres.dto.response.item.UserDTO;
import com.mttbrt.digres.dto.response.item.UsersDTO;
import com.mttbrt.digres.repository.AuthorityRepository;
import com.mttbrt.digres.repository.UserRepository;
import com.mttbrt.digres.utils.JWTHelper;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class AuthServiceImplTest {

  @InjectMocks
  private AuthServiceImpl authService;
  @Mock
  private UserRepository userRepository;
  @Mock
  private AuthorityRepository authRepository;
  @Mock
  private PasswordEncoder passwordEncoder;
  @Mock
  private JWTHelper jwtHelper;

  private final String username;
  private final Set<String> roles;
  private final RegisterUserDTO req;

  public AuthServiceImplTest() {
    username = "user";
    roles = Set.of("ROLE_STAFF");
    req = new RegisterUserDTO(username, "password", roles);
  }

  @Test
  void register_non_existing_user() {
    final ResponseDTO expectedRes = new ResponseDTO(new UsersDTO(List.of(new UserDTO(username, roles))));

    when(userRepository.findByUsername(any(String.class))).thenReturn(null);
    ResponseDTO responseEntity = authService.registerUser(req);

    assertThat(responseEntity).isEqualTo(expectedRes);
  }

  @Test
  void register_existing_user() {
    final ResponseDTO expectedRes = new ResponseDTO(new ErrorDTO(
        HttpStatus.BAD_REQUEST.value(),
        "User already exists.",
        List.of(new SingleErrorDTO("An account with the given username already exists.",
        AUTH_ENDPOINT + REGISTER_ENDPOINT))));

    when(userRepository.findByUsername(any(String.class))).thenReturn(new User());
    ResponseDTO responseEntity = authService.registerUser(req);

    assertThat(responseEntity).isEqualTo(expectedRes);
  }

  @Test
  void logout_user_successfully() {
    boolean response = authService.logoutUser(new MockHttpServletResponse());
    assertThat(response).isTrue();
  }

  @Test
  void error_while_logging_out_user() {
    Mockito.doThrow(new RuntimeException()).when(jwtHelper).removeAuthentication(new MockHttpServletResponse());
    boolean response = authService.logoutUser(new MockHttpServletResponse());
    assertThat(response).isFalse();
  }

}
