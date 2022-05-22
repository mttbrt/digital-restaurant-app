package com.mttbrt.digres.api;

import com.mttbrt.digres.domain.auth.UserDetailsImpl;
import com.mttbrt.digres.domain.dto.DataDTO;
import com.mttbrt.digres.domain.dto.ErrorsDTO;
import com.mttbrt.digres.domain.dto.resource.ErrorDTO;
import com.mttbrt.digres.domain.dto.resource.IResource;
import com.mttbrt.digres.domain.dto.resource.LoginResourceDTO;
import com.mttbrt.digres.domain.dto.resource.RegisterResourceDTO;
import com.mttbrt.digres.domain.dto.resource.SourceDTO;
import com.mttbrt.digres.domain.dto.resource.attribute.LoginDTO;
import com.mttbrt.digres.domain.dto.resource.attribute.RegisterDTO;
import com.mttbrt.digres.service.IAuthService;
import com.mttbrt.digres.utils.JWTHelper;
import java.net.HttpCookie;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import javax.annotation.security.RolesAllowed;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/auth/")
public class AuthApi {

  private final AuthenticationManager authenticationManager;
  private final IAuthService authService;
  private final JWTHelper jwtHelper;

  @Autowired
  public AuthApi(AuthenticationManager authenticationManager, IAuthService authService,
      JWTHelper jwtHelper) {
    this.authenticationManager = authenticationManager;
    this.authService = authService;
    this.jwtHelper = jwtHelper;
  }

  // TODO: fix register and handle CSRF token refresh

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
          createError(HttpStatus.BAD_REQUEST.value(), "user-registration-error",
              "Username already existing.",
              "A user with the given username already exists.", reqPath));
    }

    authService.registerUser(registerReq.getUsername(), registerReq.getPassword(),
        registerReq.getRoles());

    return ResponseEntity.created(new URI("/api/v1/user/" + registerReq.getUsername())).body(null);
  }

  @PostMapping("/logout")
  public ResponseEntity<?> logout(HttpServletResponse response) {
    jwtHelper.removeAuthentication(response);
    return ResponseEntity.ok().body(null);
  }

  // -------------------- utils functions --------------------

  private ResponseEntity<?> castRequest(String source, DataDTO obj, Class<?> expectedClass) {
    IResource resource = obj.getData();

    if (expectedClass.isInstance(resource)) {
      return null;
    } else {
      return ResponseEntity.badRequest().body(
          createError(HttpStatus.BAD_REQUEST.value(), "data-casting-error",
              "Incorrect data object.",
              "The data object provided does not match the API endpoint requirements.", source));
    }
  }

  private ResponseEntity<?> handleValidationErrors(String source, Errors errors) {
    ArrayList<ErrorDTO> errorsRes = new ArrayList<>();
    errors.getFieldErrors().forEach(err -> errorsRes.add(
        new ErrorDTO(HttpStatus.BAD_REQUEST.value(), "json-validation-error",
            "Incorrect request parameters.",
            "Field '" + err.getField() + "' " + err.getDefaultMessage(), new SourceDTO(source))));
    return ResponseEntity.badRequest().body(new ErrorsDTO(errorsRes));
  }

  private ErrorsDTO createError(int status, String code, String title, String details,
      String source) {
    ErrorDTO error = new ErrorDTO(status, code, title, details, new SourceDTO(source));
    return new ErrorsDTO(Collections.singletonList(error));
  }

}
