package com.digital.api;

import com.digital.domain.auth.UserDetailsImpl;
import com.digital.domain.dto.LoginRequest;
import com.digital.domain.dto.RegisterRequest;
import com.digital.service.AuthServiceImpl;
import com.digital.utils.JWTUtils;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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
  private final AuthServiceImpl authService;
  private final JWTUtils jwtUtils;

  @Autowired
  public AuthApi(
      AuthenticationManager authenticationManager,
      AuthServiceImpl authService,
      JWTUtils jwtUtils) {
    this.authenticationManager = authenticationManager;
    this.authService = authService;
    this.jwtUtils = jwtUtils;
  }

  @PreAuthorize("ROLE_ADMIN")
  @PostMapping("/register")
  public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest registerRequest) {
    if (authService.isUserRegistered(registerRequest.getUsername())) {
      return ResponseEntity.badRequest().body("username already exists!"); // TODO: return proper response
    }

    authService.registerUser(
        registerRequest.getUsername(),
        registerRequest.getPassword(),
        registerRequest.getRoles()
    );

    return ResponseEntity.ok("User registered successfully!"); // TODO: return proper response
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
    // authenticate user
    Authentication auth = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            loginRequest.getUsername(),
            loginRequest.getPassword()
        ));

    // save the authenticated user in the SecurityContext
    SecurityContextHolder.getContext().setAuthentication(auth);

    UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
    ResponseCookie jwtCookie = jwtUtils.generateJWTCookie(userDetails);

    return ResponseEntity.ok()
        .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
        .body("Authenticated :)"); // TODO: return proper response
  }

  @PostMapping("/logout")
  public ResponseEntity<?> logout() {
    ResponseCookie cookie = jwtUtils.generateEmptyJWTCookie();

    return ResponseEntity.ok()
        .header(HttpHeaders.SET_COOKIE, cookie.toString())
        .body("Logged out :("); // TODO: return proper response
  }

}
