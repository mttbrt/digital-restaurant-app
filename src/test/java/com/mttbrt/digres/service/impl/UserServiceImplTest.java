package com.mttbrt.digres.service.impl;

import static com.mttbrt.digres.utils.StaticVariables.USERS_ENDPOINT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.mttbrt.digres.domain.Authority;
import com.mttbrt.digres.domain.User;
import com.mttbrt.digres.dto.request.AddUserReqDTO;
import com.mttbrt.digres.dto.request.UpdateUserReqDTO;
import com.mttbrt.digres.dto.response.ResponseDTO;
import com.mttbrt.digres.dto.response.data.item.UserResDTO;
import com.mttbrt.digres.dto.response.data.item.UsersResDTO;
import com.mttbrt.digres.dto.response.error.ErrorResDTO;
import com.mttbrt.digres.dto.response.error.SingleErrorResDTO;
import com.mttbrt.digres.repository.AuthorityDao;
import com.mttbrt.digres.repository.UserDao;
import java.util.Collections;
import java.util.Set;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

  @InjectMocks
  private UserServiceImpl userService;
  @Mock
  private UserDao userDao;
  @Mock
  private AuthorityDao authRepository;
  @Mock
  private PasswordEncoder passwordEncoder;

  @Test
  void get_user_list() {
    Authority staffAuthority = new Authority("ROLE_STAFF");
    List<User> existingUsers = List.of(
        new User("test1", "password", Set.of(staffAuthority)),
        new User("test2", "password", Set.of(staffAuthority)));

    final ResponseDTO expectedRes = new ResponseDTO(
        new UsersResDTO(
            List.of(
                new UserResDTO("test1", Set.of("STAFF")),
                new UserResDTO("test2", Set.of("STAFF")))
        ));

    when(userDao.findAll()).thenReturn(existingUsers);
    ResponseDTO responseEntity = userService.getUsers();

    assertThat(responseEntity).isEqualTo(expectedRes);
  }

  @Test
  void get_empty_user_list() {
    List<User> existingUsers = Collections.emptyList();
    final ResponseDTO expectedRes = new ResponseDTO(new UsersResDTO(Collections.emptyList()));

    when(userDao.findAll()).thenReturn(existingUsers);
    ResponseDTO responseEntity = userService.getUsers();

    assertThat(responseEntity).isEqualTo(expectedRes);
  }

  @Test
  void add_non_existing_user() {
    final ResponseDTO expectedRes = new ResponseDTO(
        new UsersResDTO(List.of(new UserResDTO("test", Set.of("STAFF")))));

    when(userDao.findByUsername(any(String.class))).thenReturn(null);
    ResponseDTO responseEntity = userService.addUser(new AddUserReqDTO("test", "password", Set.of("STAFF")));

    assertThat(responseEntity).isEqualTo(expectedRes);
  }

  @Test
  void add_existing_user() {
    final ResponseDTO expectedRes = new ResponseDTO(new ErrorResDTO(
        HttpStatus.BAD_REQUEST.value(),
        "User already exists.",
        List.of(new SingleErrorResDTO("An account with the given username already exists.",
            "POST: " + USERS_ENDPOINT))));

    when(userDao.findByUsername(any(String.class))).thenReturn(new User());
    ResponseDTO responseEntity = userService.addUser(new AddUserReqDTO("test", "password", Set.of("STAFF")));

    assertThat(responseEntity).isEqualTo(expectedRes);
  }

  @Test
  void get_user_by_username() {
    Authority staffAuthority = new Authority("ROLE_STAFF");
    User existingUser = new User("test", "password", Set.of(staffAuthority));

    final ResponseDTO expectedRes = new ResponseDTO(
        new UsersResDTO(
            List.of(new UserResDTO("test", Set.of("STAFF")))
        ));

    when(userDao.findByUsername(any(String.class))).thenReturn(existingUser);
    ResponseDTO responseEntity = userService.getUserByUsername(existingUser.getUsername());

    assertThat(responseEntity).isEqualTo(expectedRes);
  }

  @Test
  void get_non_existing_user_by_username() {
    final ResponseDTO expectedRes = new ResponseDTO(new ErrorResDTO(
        HttpStatus.BAD_REQUEST.value(),
        "User does not exist.",
        List.of(new SingleErrorResDTO("An account with the given username does not exist.",
            "GET: " + USERS_ENDPOINT + '/' + "test"))));

    when(userDao.findByUsername(any(String.class))).thenReturn(null);
    ResponseDTO responseEntity = userService.getUserByUsername("test");

    assertThat(responseEntity).isEqualTo(expectedRes);
  }

  @Test
  void update_existing_user() {
    Authority staffAuthority = new Authority("ROLE_STAFF");
    User existingUser = new User("test", "password", Set.of(staffAuthority));

    final ResponseDTO expectedRes = new ResponseDTO(
        new UsersResDTO(
            List.of(new UserResDTO("test", Set.of("STAFF", "ADMIN")))
        ));

    when(userDao.findByUsername(any(String.class))).thenReturn(existingUser);
    ResponseDTO responseEntity = userService.updateUser(existingUser.getUsername(),
        new UpdateUserReqDTO("password", Set.of("STAFF", "ADMIN")));

    assertThat(responseEntity).isEqualTo(expectedRes);
  }

  @Test
  void update_non_existing_user() {
    final ResponseDTO expectedRes = new ResponseDTO(new ErrorResDTO(
        HttpStatus.BAD_REQUEST.value(),
        "User does not exist.",
        List.of(new SingleErrorResDTO("An account with the given username does not exist.",
            "PUT: " + USERS_ENDPOINT + '/' + "test"))));

    when(userDao.findByUsername(any(String.class))).thenReturn(null);
    ResponseDTO responseEntity = userService.updateUser("test",
        new UpdateUserReqDTO("password", Set.of("STAFF", "ADMIN")));

    assertThat(responseEntity).isEqualTo(expectedRes);
  }

  @Test
  void delete_existing_user() {
    Authority staffAuthority = new Authority("ROLE_STAFF");
    User existingUser = new User("test", "password", Set.of(staffAuthority));

    final ResponseDTO expectedRes = new ResponseDTO(
        new UsersResDTO(
            List.of(new UserResDTO("test", Set.of("STAFF")))
        ));

    when(userDao.findByUsername(any(String.class))).thenReturn(existingUser);
    ResponseDTO responseEntity = userService.deleteUser(existingUser.getUsername());

    assertThat(responseEntity).isEqualTo(expectedRes);
  }

  @Test
  void delete_non_existing_user() {
    final ResponseDTO expectedRes = new ResponseDTO(new ErrorResDTO(
        HttpStatus.BAD_REQUEST.value(),
        "User does not exist.",
        List.of(new SingleErrorResDTO("An account with the given username does not exist.",
            "DELETE: " + USERS_ENDPOINT + '/' + "test"))));

    when(userDao.findByUsername(any(String.class))).thenReturn(null);
    ResponseDTO responseEntity = userService.deleteUser("test");

    assertThat(responseEntity).isEqualTo(expectedRes);
  }

}
