package com.mttbrt.digres.api;

import static com.mttbrt.digres.utils.StaticVariables.LOGOUT_ENDPOINT;

import com.mttbrt.digres.domain.dto.DataDTO;
import com.mttbrt.digres.domain.dto.ResponseDTO;
import com.mttbrt.digres.service.AuthService;
import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthApi {

  private final AuthService authService;

  public AuthApi(AuthService authService) {
    this.authService = authService;
  }

  @RolesAllowed("ADMIN")
  @PostMapping("/register")
  public ResponseDTO register(@Valid @RequestBody DataDTO data) {
    return authService.registerUser(data);
  }

  @PostMapping(LOGOUT_ENDPOINT)
  public ResponseDTO logout(HttpServletResponse res) {
    return authService.logoutUser(res);
  }

}
