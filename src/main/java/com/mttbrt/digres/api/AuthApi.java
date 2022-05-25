package com.mttbrt.digres.api;

import static com.mttbrt.digres.domain.ErrorCode.USER_REGISTRATION_ERROR;
import static com.mttbrt.digres.utils.PreprocessingHelper.castRequest;
import static com.mttbrt.digres.utils.PreprocessingHelper.createError;
import static com.mttbrt.digres.utils.PreprocessingHelper.handleValidationErrors;

import com.mttbrt.digres.domain.dto.DataDTO;
import com.mttbrt.digres.domain.dto.resource.RegisterResourceDTO;
import com.mttbrt.digres.domain.dto.resource.attribute.RegisterDTO;
import com.mttbrt.digres.service.AuthService;
import com.mttbrt.digres.utils.JWTHelper;
import java.net.URI;
import java.net.URISyntaxException;
import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${app.auth.endpoint}")
public class AuthApi {

  private final AuthService authService;
  private final JWTHelper jwtHelper;

  public AuthApi(AuthService authService, JWTHelper jwtHelper) {
    this.authService = authService;
    this.jwtHelper = jwtHelper;
  }

  @RolesAllowed("ADMIN")
  @PostMapping("/register")
  public ResponseEntity<?> register(@Valid @RequestBody DataDTO data, HttpServletRequest request,
      Errors errors) throws URISyntaxException {
    String reqPath = request.getServletPath();

    if (errors.hasErrors()) {
      return handleValidationErrors(reqPath, errors);
    }

    ResponseEntity<?> res = castRequest(reqPath, data, RegisterResourceDTO.class);
    if (res != null) {
      return res;
    }

    RegisterDTO registerReq = ((RegisterResourceDTO) data.getData()).getAttributes();
    if (authService.isUserRegistered(registerReq.getUsername())) {
      return ResponseEntity.badRequest().body(
        createError(HttpStatus.BAD_REQUEST.value(),
            USER_REGISTRATION_ERROR,
          "User already exists.",
          "An account with the given username already exists.",
          reqPath));
    }

    authService.registerUser(
        registerReq.getUsername(),
        registerReq.getPassword(),
        registerReq.getRoles());

    return ResponseEntity.created(new URI("/api/v1/user/" + registerReq.getUsername())).body(null);
  }

  @PostMapping("/logout")
  public ResponseEntity<?> logout(HttpServletResponse response) {
    jwtHelper.removeAuthentication(response);
    return ResponseEntity.ok().body(null);
  }

}
