package com.mttbrt.digres.service.impl;

import static org.assertj.core.api.Assertions.assertThat;

import com.mttbrt.digres.utils.JWTHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletResponse;

@ExtendWith(MockitoExtension.class)
public class AuthServiceImplTest {

  @InjectMocks
  private AuthServiceImpl authService;
  @Mock
  private JWTHelper jwtHelper;

  @Test
  void logout_user_successfully() {
    boolean response = authService.logoutUser(new MockHttpServletResponse());
    assertThat(response).isTrue();
  }

  @Test
  void error_while_logging_out_user() {
    Mockito.doThrow(new RuntimeException()).when(jwtHelper)
        .removeAuthentication(new MockHttpServletResponse());
    boolean response = authService.logoutUser(new MockHttpServletResponse());
    assertThat(response).isFalse();
  }

}
