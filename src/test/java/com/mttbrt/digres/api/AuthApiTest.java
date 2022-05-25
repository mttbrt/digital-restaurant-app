package com.mttbrt.digres.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.mttbrt.digres.domain.dto.DataDTO;
import com.mttbrt.digres.domain.dto.resource.RegisterResourceDTO;
import com.mttbrt.digres.domain.dto.resource.attribute.RegisterDTO;
import com.mttbrt.digres.service.AuthService;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.validation.AbstractBindingResult;

@ExtendWith(MockitoExtension.class)
public class AuthApiTest {

  @InjectMocks
  private AuthApi authApi;
  @Mock
  private AuthService authService;
  @Mock
  private AbstractBindingResult errors;
  @Mock
  private MockHttpServletRequest request;

  @Test
  public void testRegister() throws URISyntaxException {
    DataDTO data = new DataDTO(
        new RegisterResourceDTO("REG01", "registration",
            new RegisterDTO("user", "password", new HashSet<>(List.of("STAFF")))
        ));

    when(authService.isUserRegistered(any(String.class))).thenReturn(false);
    ResponseEntity<?> res = authApi.register(data, request, errors);

    verify(authService).registerUser("user", "password", new HashSet<>(List.of("STAFF")));
    assertThat(res.getStatusCodeValue()).isEqualTo(201);
  }

}
