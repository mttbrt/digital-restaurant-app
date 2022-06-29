package com.mttbrt.digres.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.mttbrt.digres.dto.request.UserReqDTO;
import com.mttbrt.digres.dto.response.ErrorResDTO;
import com.mttbrt.digres.dto.response.ResponseDTO;
import com.mttbrt.digres.dto.response.SingleErrorResDTO;
import com.mttbrt.digres.dto.response.item.IItem;
import com.mttbrt.digres.dto.response.item.UserResDTO;
import com.mttbrt.digres.dto.response.item.UsersResDTO;
import com.mttbrt.digres.service.impl.AuthServiceImpl;
import java.net.URI;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

  @InjectMocks
  private AuthController authController;
  @Mock
  private AuthServiceImpl authService;

  private final String username;
  private final Set<String> roles;
  private final UserReqDTO req;

  public AuthControllerTest() {
    username = "user";
    roles = Set.of("ROLE_STAFF");
    req = new UserReqDTO(username, "password", roles);
  }

  @Test
  void register_new_user_successfully() {
    final ResponseDTO expectedRes = new ResponseDTO(new UsersResDTO(List.of(new UserResDTO(username, roles))));

    when(authService.registerUser(any(UserReqDTO.class))).thenReturn(expectedRes);
    ResponseEntity<?> responseEntity = authController.register(req);

    assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HttpStatus.CREATED.value());
    assertThat(responseEntity.getHeaders().getLocation()).isEqualTo(
        URI.create("/users/" + username));
    assertThat((ResponseDTO) responseEntity.getBody()).isEqualTo(expectedRes);
  }

  @Test
  void bad_request_while_registering_new_user() {
    final ResponseDTO expectedRes = new ResponseDTO(new ErrorResDTO(
        HttpStatus.BAD_REQUEST.value(),
        "Incorrect request parameters.",
        List.of(new SingleErrorResDTO("Username cannot be blank.", "username"))));

    when(authService.registerUser(any(UserReqDTO.class))).thenReturn(expectedRes);
    ResponseEntity<?> responseEntity = authController.register(req);

    assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    assertThat(responseEntity.getHeaders().getLocation()).isNull();
    assertThat((ResponseDTO) responseEntity.getBody()).isEqualTo(expectedRes);
  }

  @Test
  void internal_server_error_while_registering_new_user() {
    final ResponseDTO expectedRes = new ResponseDTO((IItem) null);

    when(authService.registerUser(any(UserReqDTO.class))).thenReturn(expectedRes);
    ResponseEntity<?> responseEntity = authController.register(req);

    assertThat(responseEntity.getStatusCodeValue()).isEqualTo(
        HttpStatus.INTERNAL_SERVER_ERROR.value());
    assertThat(responseEntity.getHeaders().getLocation()).isNull();
    assertThat((ResponseDTO) responseEntity.getBody()).isEqualTo(null);
  }

  @Test
  void logout_user_successfully() {
    when(authService.logoutUser(any(HttpServletResponse.class))).thenReturn(true);
    ResponseEntity<?> responseEntity = authController.logout(new MockHttpServletResponse());

    assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HttpStatus.OK.value());
    assertThat(responseEntity.getHeaders().getLocation()).isNull();
    assertThat((ResponseDTO) responseEntity.getBody()).isEqualTo(null);
  }

  @Test
  void internal_server_error_while_logging_out_user() {
    when(authService.logoutUser(any(HttpServletResponse.class))).thenReturn(false);
    ResponseEntity<?> responseEntity = authController.logout(new MockHttpServletResponse());

    assertThat(responseEntity.getStatusCodeValue()).isEqualTo(
        HttpStatus.INTERNAL_SERVER_ERROR.value());
    assertThat(responseEntity.getHeaders().getLocation()).isNull();
    assertThat((ResponseDTO) responseEntity.getBody()).isEqualTo(null);
  }

}
