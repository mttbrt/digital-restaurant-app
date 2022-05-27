package com.mttbrt.digres.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
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
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.AbstractBindingResult;

@ExtendWith(MockitoExtension.class)
public class AuthApiTest {

  @InjectMocks
  private AuthApi authApi;
  @Mock
  private AuthService authService;
  @Mock
  private AbstractBindingResult errors;

  @Test
  public void test_register_existing_user() throws URISyntaxException {
//    RegisterDTO registerDTO = new RegisterDTO("user", "password", new HashSet<>(List.of("STAFF")));
//    DataDTO data = new DataDTO(new RegisterResourceDTO("REG01", "registration", registerDTO));
//
//    when(authService.isUserRegistered(any(String.class))).thenReturn(true);
//    ResponseEntity<?> res = authApi.register(data, errors);
//
//    verify(authService).isUserRegistered(registerDTO.getUsername());
//    verify(authService, never()).registerUser(registerDTO.getUsername(), registerDTO.getPassword(), registerDTO.getRoles());
//    assertThat(res.getStatusCodeValue()).isEqualTo(400);
  }

  @Test
  public void test_register_new_user() throws URISyntaxException {
//    RegisterDTO registerDTO = new RegisterDTO("user", "password", new HashSet<>(List.of("STAFF")));
//    DataDTO data = new DataDTO(new RegisterResourceDTO("REG01", "registration", registerDTO));
//
//    when(authService.isUserRegistered(any(String.class))).thenReturn(false);
//    ResponseEntity<?> res = authApi.register(data, errors);
//
//    verify(authService).isUserRegistered(registerDTO.getUsername());
//    verify(authService).registerUser(registerDTO.getUsername(), registerDTO.getPassword(), registerDTO.getRoles());
//    assertThat(res.getStatusCodeValue()).isEqualTo(201);
  }

  @Test
  public void test_register_new_user_with_validation_errors() throws URISyntaxException {
//    RegisterDTO registerDTO = new RegisterDTO("user", "password", new HashSet<>(List.of("STAFF")));
//    DataDTO data = new DataDTO(new RegisterResourceDTO("REG01", "registration", null));
//
////    errors.addError();
//
//    when(authService.isUserRegistered(any(String.class))).thenReturn(false);
//    ResponseEntity<?> res = authApi.register(data, errors);
//
//    verify(authService).isUserRegistered(registerDTO.getUsername());
//    verify(authService).registerUser(registerDTO.getUsername(), registerDTO.getPassword(), registerDTO.getRoles());
//    assertThat(res.getStatusCodeValue()).isEqualTo(201);
  }

}
