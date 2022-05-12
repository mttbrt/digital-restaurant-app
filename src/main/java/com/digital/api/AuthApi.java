package com.digital.api;

import com.digital.domain.auth.UserDetailsImpl;
import com.digital.domain.dto.DataDTO;
import com.digital.domain.dto.ErrorsDTO;
import com.digital.domain.dto.content.ErrorDTO;
import com.digital.domain.dto.content.ResourceDTO;
import com.digital.domain.dto.content.attribute.LoginReqDTO;
import com.digital.domain.dto.content.attribute.RegisterReqDTO;
import com.digital.service.IAuthService;
import com.digital.utils.JWTUtils;
import java.util.ArrayList;
import java.util.Collections;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
  private final JWTUtils jwtUtils;

  @Autowired
  public AuthApi(
      AuthenticationManager authenticationManager,
      IAuthService authService,
      JWTUtils jwtUtils) {
    this.authenticationManager = authenticationManager;
    this.authService = authService;
    this.jwtUtils = jwtUtils;
  }

  @PreAuthorize("ROLE_ADMIN")
  @PostMapping("/register")
  public ResponseEntity<?> register(@Valid @RequestBody DataDTO data) {
    ResponseEntity<?> res = validateObj(data, "/api/v1/auth/register", "users", RegisterReqDTO.class);
    if (res != null) {
      return res;
    }

    RegisterReqDTO registerReq = (RegisterReqDTO) data.getData().getAttributes();

    if (authService.isUserRegistered(registerReq.getUsername())) {
      return ResponseEntity.badRequest()
          .body("username already exists!"); // TODO: return proper response
    }

    authService.registerUser(
        registerReq.getUsername(),
        registerReq.getPassword(),
        registerReq.getRoles()
    );

    return ResponseEntity.ok("User registered successfully!"); // TODO: return proper response
  }

  private <T> ResponseEntity<?> validateObj(
      DataDTO obj,
      String source,
      String expectedType,
      Class<T> expectedClass) {
    ResourceDTO resource = obj.getData();

    if (!resource.getType().equals(expectedType)) {
      ErrorDTO error = new ErrorDTO(
          1,
          HttpStatus.BAD_REQUEST,
          "ERR1",
          "Data type " + resource.getType() + " was not expected.",
          null,
          source
      );
      ErrorsDTO errors = new ErrorsDTO(Collections.singletonList(error));
      return ResponseEntity.badRequest().body(errors);
    }

    if (expectedClass.isInstance(resource.getAttributes())) {
      return null;
    } else {
      ErrorDTO error = new ErrorDTO(
          2,
          HttpStatus.BAD_REQUEST,
          "ERR2",
          "The attributes provided were not expected.",
          null,
          source
      );
      ErrorsDTO errors = new ErrorsDTO(Collections.singletonList(error));
      return ResponseEntity.badRequest().body(errors);
    }
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@Valid @RequestBody LoginReqDTO loginReqDTO) {
    // authenticate user
    Authentication auth = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginReqDTO.getUsername(),
            loginReqDTO.getPassword()));

    // save the authenticated user in the SecurityContext
    SecurityContextHolder.getContext().setAuthentication(auth);

    UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
    ResponseCookie jwtCookie = jwtUtils.generateJWTCookie(userDetails);

    return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
        .body("Authenticated :)"); // TODO: return proper response
  }

  @PostMapping("/logout")
  public ResponseEntity<?> logout() {
    ResponseCookie cookie = jwtUtils.generateEmptyJWTCookie();

    return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
        .body("Logged out :("); // TODO: return proper response
  }

}
