package com.mttbrt.digres.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.mttbrt.digres.dto.response.ResponseDTO;
import com.mttbrt.digres.service.impl.AuthServiceImpl;
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
