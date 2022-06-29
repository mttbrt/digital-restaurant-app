package com.mttbrt.digres.service.impl;

import static com.mttbrt.digres.utils.StaticVariables.AUTH_NAMESPACE;
import static com.mttbrt.digres.utils.StaticVariables.REGISTER_ENDPOINT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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
  private UserDao userDao;
  @Mock
  private AuthorityDao authRepository;
  @Mock
  private PasswordEncoder passwordEncoder;
  @Mock
  private JWTHelper jwtHelper;

  private final String username;
  private final Set<String> roles;
  private final UserReqDTO req;

  public AuthServiceImplTest() {
    username = "user";
    roles = Set.of("ROLE_STAFF");
    req = new UserReqDTO(username, "password", roles);
  }

  @Test
  void register_non_existing_user() {
    final ResponseDTO expectedRes = new ResponseDTO(new UsersResDTO(List.of(new UserResDTO(username, roles))));

    when(userDao.findByUsername(any(String.class))).thenReturn(null);
    ResponseDTO responseEntity = authService.registerUser(req);

    assertThat(responseEntity).isEqualTo(expectedRes);
  }

  @Test
  void register_existing_user() {
    final ResponseDTO expectedRes = new ResponseDTO(new ErrorResDTO(
        HttpStatus.BAD_REQUEST.value(),
        "User already exists.",
        List.of(new SingleErrorResDTO("An account with the given username already exists.",
        AUTH_NAMESPACE + REGISTER_ENDPOINT))));

    when(userDao.findByUsername(any(String.class))).thenReturn(new User());
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
