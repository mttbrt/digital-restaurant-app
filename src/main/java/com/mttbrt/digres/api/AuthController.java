package com.mttbrt.digres.api;

import static com.mttbrt.digres.utils.StaticVariables.LOGOUT_ENDPOINT;

import com.mttbrt.digres.dto.request.RegisterUserDTO;
import com.mttbrt.digres.dto.response.ResponseDTO;
import com.mttbrt.digres.service.AuthService;
import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

  private final AuthService authService;

  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  @RolesAllowed("ADMIN")
  @PostMapping("/register")
  public ResponseEntity<?> register(@Valid @RequestBody RegisterUserDTO request) {
    ResponseDTO response = authService.registerUser(request);
    if (response.getError() != null) {
      return ResponseEntity.badRequest().body(response);
    } else if (response.getData() != null) {
      return ResponseEntity.ok().body(response);
    }
    return ResponseEntity.internalServerError().build();
  }

  @PostMapping(LOGOUT_ENDPOINT)
  public ResponseEntity<?> logout(HttpServletResponse res) {
    if (authService.logoutUser(res)) {
      return ResponseEntity.ok().build();
    }
    return ResponseEntity.badRequest().build();
  }

}
