package com.mttbrt.digres.api;

import static com.mttbrt.digres.utils.StaticVariables.USERS_ENDPOINT;

import com.mttbrt.digres.dto.request.AddUserReqDTO;
import com.mttbrt.digres.dto.request.UpdateUserReqDTO;
import com.mttbrt.digres.dto.response.ResponseDTO;
import com.mttbrt.digres.service.UserService;
import java.net.URI;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import org.hibernate.internal.util.ZonedDateTimeComparator;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
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
    return ResponseEntity.ok()
        .lastModified(System.currentTimeMillis())
        .body(userService.getUsers());
  }

  @GetMapping("/{userId}")
  public ResponseEntity<?> getUser(@PathVariable String userId) {
    ResponseDTO response = userService.getUserByUsername(userId);
    if (response.getError() != null) {
      return ResponseEntity.badRequest().body(response);
    } else if (response.getData() != null) {
      return ResponseEntity.ok().body(response);
    }
    return ResponseEntity.internalServerError().build();
  }

  @PostMapping()
  public ResponseEntity<?> addUser(@Valid @RequestBody AddUserReqDTO request) {
    ResponseDTO response = userService.addUser(request);
    if (response.getError() != null) {
      return ResponseEntity.badRequest().body(response);
    } else if (response.getData() != null) {
      return ResponseEntity.created(URI.create(USERS_ENDPOINT + '/' + request.getUsername())).body(response);
    }
    return ResponseEntity.internalServerError().build();
  }

  @PutMapping("/{username}")
  public ResponseEntity<?> updateUser(@PathVariable String username,
      @Valid @RequestBody UpdateUserReqDTO request) {
    ResponseDTO response = userService.updateUser(username, request);
    if (response.getError() != null) {
      return ResponseEntity.badRequest().body(response);
    } else if (response.getData() != null) {
      return ResponseEntity.ok().body(response);
    }
    return ResponseEntity.internalServerError().build();
  }

  @DeleteMapping("/{username}")
  public ResponseEntity<?> deleteUser(@PathVariable String username) {
    ResponseDTO response = userService.deleteUser(username);
    if (response.getError() != null) {
      return ResponseEntity.badRequest().body(response);
    } else if (response.getData() != null) {
      return ResponseEntity.ok().body(response);
    }
    return ResponseEntity.internalServerError().build();
  }

}
