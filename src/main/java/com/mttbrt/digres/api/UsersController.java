package com.mttbrt.digres.api;

import com.mttbrt.digres.dto.request.UserReqDTO;
import com.mttbrt.digres.service.AuthService;
import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UsersController {

  private final AuthService authService;

  public UsersController(AuthService authService) {
    this.authService = authService;
  }

  @RolesAllowed({"STAFF", "ADMIN"})
  @GetMapping()
  public ResponseEntity<?> getUsers() {
    return ResponseEntity.ok().body("get users");
  }

  @RolesAllowed("ADMIN")
  @PostMapping()
  public ResponseEntity<?> addUser(@Valid @RequestBody UserReqDTO request) {
    return ResponseEntity.ok().body("add user: " + request);
  }

  @RolesAllowed({"STAFF", "ADMIN"})
  @GetMapping("/{id}")
  public ResponseEntity<?> getUser(@PathVariable Long id) {
    return ResponseEntity.ok().body("get user: " + id);
  }

  @RolesAllowed("ADMIN")
  @PutMapping("/{id}")
  public ResponseEntity<?> updateUser(@PathVariable Long id, @Valid @RequestBody UserReqDTO request) {
    return ResponseEntity.ok().body("update user: " + id + " - " + request);
  }

  @RolesAllowed("ADMIN")
  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteUser(@PathVariable Long id) {
    return ResponseEntity.ok().body("delete user: " + id);
  }

}
