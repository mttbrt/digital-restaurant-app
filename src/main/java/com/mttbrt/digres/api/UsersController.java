package com.mttbrt.digres.api;

import com.mttbrt.digres.dto.request.AddUserReqDTO;
import com.mttbrt.digres.dto.request.UpdateUserReqDTO;
import com.mttbrt.digres.dto.response.BasicResDTO;
import com.mttbrt.digres.dto.response.ResponseDTO;
import com.mttbrt.digres.exception.FoundException;
import com.mttbrt.digres.exception.NotFoundException;
import com.mttbrt.digres.service.UserService;
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
@RolesAllowed("ADMIN")
@RequestMapping("/api/v1/users")
public class UsersController {

  private final UserService userService;

  public UsersController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping()
  public ResponseEntity<?> getUsers() {
    ResponseDTO response = userService.getUsers();
    return ResponseEntity.ok()
        .lastModified(response.getModified())
        .body(response);
  }

  @GetMapping("/{userId}")
  public ResponseEntity<?> getUser(@PathVariable long userId) throws NotFoundException {
    ResponseDTO response = userService.getUser(userId);
    return ResponseEntity.ok()
        .lastModified(response.getModified())
        .body(response);
  }

  @PostMapping()
  public ResponseEntity<?> addUser(@Valid @RequestBody AddUserReqDTO request)
      throws FoundException {
    BasicResDTO response = userService.addUser(request);
    return ResponseEntity.created(response.getLocation())
        .lastModified(response.getModified()).body(null);
  }

  @PutMapping("/{userId}")
  public ResponseEntity<?> updateUser(@PathVariable long userId,
      @Valid @RequestBody UpdateUserReqDTO request) throws NotFoundException {
    BasicResDTO response = userService.updateUser(userId, request);
    return ResponseEntity.ok()
        .lastModified(response.getModified())
        .body(null);
  }

  @DeleteMapping("/{userId}")
  public ResponseEntity<?> deleteUser(@PathVariable long userId) throws NotFoundException {
    userService.deleteUser(userId);
    return ResponseEntity.ok().body(null);
  }

}
