package com.mttbrt.digres.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.mttbrt.digres.dto.response.ResponseDTO;
import com.mttbrt.digres.dto.response.data.item.UserResDTO;
import com.mttbrt.digres.dto.response.data.item.UsersResDTO;
import com.mttbrt.digres.service.impl.UserServiceImpl;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
public class UsersControllerTest {

  @InjectMocks
  private UsersController usersController;
  @Mock
  private UserServiceImpl userService;

  @Test
  void get_users_successfully() {
    final ResponseDTO expectedRes = new ResponseDTO(new UsersResDTO(
        List.of(
            new UserResDTO("test1", Set.of("STAFF")),
            new UserResDTO("test2", Set.of("STAFF"))
        )));

    when(userService.getUsers()).thenReturn(expectedRes);
    ResponseEntity<?> responseEntity = usersController.getUsers();

    assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HttpStatus.OK.value());
    assertThat(responseEntity.getHeaders().getLocation()).isNull();
    assertThat((ResponseDTO) responseEntity.getBody()).isEqualTo(expectedRes);
  }

  @Test
  void add_new_user_successfully() {
//    final ResponseDTO expectedRes = new ResponseDTO(new UsersResDTO(
//        List.of(new UserResDTO("test", Set.of("STAFF")))));
//
//    when(userService.addUser(any(AddUserReqDTO.class))).thenReturn(expectedRes);
//    ResponseEntity<?> responseEntity = usersController.addUser(new AddUserReqDTO("test", "password", Set.of("STAFF")));
//
//    assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HttpStatus.CREATED.value());
//    assertThat(responseEntity.getHeaders().getLocation()).isEqualTo(URI.create(USERS_ENDPOINT + '/' + "test"));
//    assertThat((ResponseDTO) responseEntity.getBody()).isEqualTo(expectedRes);
  }

  @Test
  void bad_request_while_adding_new_user() {
//    final ResponseDTO expectedRes = new ResponseDTO(new ErrorResDTO(
//        HttpStatus.BAD_REQUEST.value(),
//        "User already exists.",
//        List.of(new SingleErrorResDTO("An account with the given username already exists.",
//            "POST: " + USERS_ENDPOINT + '/' + "test"))));
//
//    when(userService.addUser(any(AddUserReqDTO.class))).thenReturn(expectedRes);
//    ResponseEntity<?> responseEntity = usersController.addUser(new AddUserReqDTO("test", "password", Set.of("STAFF")));
//
//    assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HttpStatus.BAD_REQUEST.value());
//    assertThat(responseEntity.getHeaders().getLocation()).isNull();
//    assertThat((ResponseDTO) responseEntity.getBody()).isEqualTo(expectedRes);
  }

  @Test
  void internal_server_error_while_adding_new_user() {
//    final ResponseDTO expectedRes = new ResponseDTO((IItem) null);
//
//    when(userService.addUser(any(AddUserReqDTO.class))).thenReturn(expectedRes);
//    ResponseEntity<?> responseEntity = usersController.addUser(new AddUserReqDTO("test", "password", Set.of("STAFF")));
//
//    assertThat(responseEntity.getStatusCodeValue()).isEqualTo(
//        HttpStatus.INTERNAL_SERVER_ERROR.value());
//    assertThat(responseEntity.getHeaders().getLocation()).isNull();
//    assertThat((ResponseDTO) responseEntity.getBody()).isEqualTo(null);
  }

  @Test
  void get_user_successfully() {
//    final ResponseDTO expectedRes = new ResponseDTO(new UsersResDTO(
//        List.of(new UserResDTO("test", Set.of("STAFF")))));
//
//    when(userService.getUserByUsername(any(String.class))).thenReturn(expectedRes);
//    ResponseEntity<?> responseEntity = usersController.getUser("test");
//
//    assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HttpStatus.OK.value());
//    assertThat(responseEntity.getHeaders().getLocation()).isNull();
//    assertThat((ResponseDTO) responseEntity.getBody()).isEqualTo(expectedRes);
  }

  @Test
  void bad_request_while_getting_user() {
//    final ResponseDTO expectedRes = new ResponseDTO(new ErrorResDTO(
//        HttpStatus.BAD_REQUEST.value(),
//        "User does not exist.",
//        List.of(new SingleErrorResDTO("An account with the given username does not exist.",
//            "GET: " + USERS_ENDPOINT + '/' + "test"))));
//
//    when(userService.getUserByUsername(any(String.class))).thenReturn(expectedRes);
//    ResponseEntity<?> responseEntity = usersController.getUser("test");
//
//    assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HttpStatus.BAD_REQUEST.value());
//    assertThat(responseEntity.getHeaders().getLocation()).isNull();
//    assertThat((ResponseDTO) responseEntity.getBody()).isEqualTo(expectedRes);
  }

  @Test
  void internal_server_error_while_getting_user() {
//    final ResponseDTO expectedRes = new ResponseDTO((IItem) null);
//
//    when(userService.getUserByUsername(any(String.class))).thenReturn(expectedRes);
//    ResponseEntity<?> responseEntity = usersController.getUser("test");
//
//    assertThat(responseEntity.getStatusCodeValue()).isEqualTo(
//        HttpStatus.INTERNAL_SERVER_ERROR.value());
//    assertThat(responseEntity.getHeaders().getLocation()).isNull();
//    assertThat((ResponseDTO) responseEntity.getBody()).isEqualTo(null);
  }

  @Test
  void update_new_user_successfully() {
//    final ResponseDTO expectedRes = new ResponseDTO(new UsersResDTO(
//        List.of(new UserResDTO("test", Set.of("ADMIN", "STAFF")))));
//
//    when(userService.updateUser(any(String.class), any(UpdateUserReqDTO.class))).thenReturn(expectedRes);
//    ResponseEntity<?> responseEntity = usersController.updateUser("test", new UpdateUserReqDTO("password", Set.of("ADMIN", "STAFF")));
//
//    assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HttpStatus.OK.value());
//    assertThat(responseEntity.getHeaders().getLocation()).isNull();
//    assertThat((ResponseDTO) responseEntity.getBody()).isEqualTo(expectedRes);
  }

  @Test
  void bad_request_while_updating_new_user() {
//    final ResponseDTO expectedRes = new ResponseDTO(new ErrorResDTO(
//        HttpStatus.BAD_REQUEST.value(),
//        "User does not exist.",
//        List.of(new SingleErrorResDTO("An account with the given username does not exist.",
//            "PUT: " + USERS_ENDPOINT + '/' + "test"))));
//
//    when(userService.updateUser(any(String.class), any(UpdateUserReqDTO.class))).thenReturn(expectedRes);
//    ResponseEntity<?> responseEntity = usersController.updateUser("test", new UpdateUserReqDTO("password", Set.of("ADMIN", "STAFF")));
//
//    assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HttpStatus.BAD_REQUEST.value());
//    assertThat(responseEntity.getHeaders().getLocation()).isNull();
//    assertThat((ResponseDTO) responseEntity.getBody()).isEqualTo(expectedRes);
  }

  @Test
  void internal_server_error_while_updating_new_user() {
//    final ResponseDTO expectedRes = new ResponseDTO((IItem) null);
//
//    when(userService.updateUser(any(String.class), any(UpdateUserReqDTO.class))).thenReturn(expectedRes);
//    ResponseEntity<?> responseEntity = usersController.updateUser("test", new UpdateUserReqDTO("password", Set.of("ADMIN", "STAFF")));
//
//    assertThat(responseEntity.getStatusCodeValue()).isEqualTo(
//        HttpStatus.INTERNAL_SERVER_ERROR.value());
//    assertThat(responseEntity.getHeaders().getLocation()).isNull();
//    assertThat((ResponseDTO) responseEntity.getBody()).isEqualTo(null);
  }

  @Test
  void delete_new_user_successfully() {
//    final ResponseDTO expectedRes = new ResponseDTO(new UsersResDTO(
//        List.of(new UserResDTO("test", Set.of("STAFF")))));
//
//    when(userService.deleteUser(any(String.class))).thenReturn(expectedRes);
//    ResponseEntity<?> responseEntity = usersController.deleteUser("test");
//
//    assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HttpStatus.OK.value());
//    assertThat(responseEntity.getHeaders().getLocation()).isNull();
//    assertThat((ResponseDTO) responseEntity.getBody()).isEqualTo(expectedRes);
  }

  @Test
  void bad_request_while_deleting_new_user() {
//    final ResponseDTO expectedRes = new ResponseDTO(new ErrorResDTO(
//        HttpStatus.BAD_REQUEST.value(),
//        "User does not exist.",
//        List.of(new SingleErrorResDTO("An account with the given username does not exist.",
//            "DELETE: " + USERS_ENDPOINT + '/' + "test"))));
//
//    when(userService.deleteUser(any(String.class))).thenReturn(expectedRes);
//    ResponseEntity<?> responseEntity = usersController.deleteUser("test");
//
//    assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HttpStatus.BAD_REQUEST.value());
//    assertThat(responseEntity.getHeaders().getLocation()).isNull();
//    assertThat((ResponseDTO) responseEntity.getBody()).isEqualTo(expectedRes);
  }

  @Test
  void internal_server_error_while_deleting_new_user() {
//    final ResponseDTO expectedRes = new ResponseDTO((IItem) null);
//
//    when(userService.deleteUser(any(String.class))).thenReturn(expectedRes);
//    ResponseEntity<?> responseEntity = usersController.deleteUser("test");
//
//    assertThat(responseEntity.getStatusCodeValue()).isEqualTo(
//        HttpStatus.INTERNAL_SERVER_ERROR.value());
//    assertThat(responseEntity.getHeaders().getLocation()).isNull();
//    assertThat((ResponseDTO) responseEntity.getBody()).isEqualTo(null);
  }

}
