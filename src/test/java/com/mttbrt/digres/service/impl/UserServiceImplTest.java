package com.mttbrt.digres.service.impl;

import static com.mttbrt.digres.dto.response.Errors.USER_ALREDY_PRESENT;
import static com.mttbrt.digres.dto.response.Errors.USER_NOT_FOUND;
import static com.mttbrt.digres.utils.StaticVariables.USERS_ENDPOINT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.mttbrt.digres.domain.Authority;
import com.mttbrt.digres.domain.User;
import com.mttbrt.digres.dto.request.AddUserReqDTO;
import com.mttbrt.digres.dto.request.UpdateUserReqDTO;
import com.mttbrt.digres.dto.response.BasicResDTO;
import com.mttbrt.digres.dto.response.ResponseDTO;
import com.mttbrt.digres.dto.response.data.item.UserResDTO;
import com.mttbrt.digres.dto.response.data.item.UsersResDTO;
import com.mttbrt.digres.exception.FoundException;
import com.mttbrt.digres.exception.NotFoundException;
import com.mttbrt.digres.repository.AuthorityDao;
import com.mttbrt.digres.repository.UserDao;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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
  void get_user_by_id() throws NotFoundException {
    Authority staffAuthority = new Authority("ROLE_STAFF");
    User existingUser = new User("test", "password", Set.of(staffAuthority));

    final ResponseDTO expectedRes = new ResponseDTO(
        new UsersResDTO(
            List.of(new UserResDTO("test", Set.of("STAFF")))
        ));

    when(userDao.findById(any(Long.class))).thenReturn(Optional.of(existingUser));
    ResponseDTO responseEntity = userService.getUser(1L);

    assertThat(responseEntity).isEqualTo(expectedRes);
  }

  @Test
  void get_non_existing_user_by_id() {
    when(userDao.findById(any(Long.class))).thenReturn(Optional.empty());

    NotFoundException thrown = assertThrows(
        NotFoundException.class,
        () -> userService.getUser(2L));

    assertEquals(USER_NOT_FOUND, thrown.getErr());
    assertEquals("GET " + USERS_ENDPOINT + '/' + 2, thrown.getLocation());
  }

  @Test
  void add_non_existing_user() throws FoundException {
    User savedUser = new User();
    savedUser.setId(1L);
    final BasicResDTO expectedRes = new BasicResDTO(savedUser.getModified(),
        USERS_ENDPOINT + '/' + 1);

    when(userDao.findByUsername(any(String.class))).thenReturn(Optional.empty());
    when(userDao.save(any(User.class))).thenReturn(savedUser);
    BasicResDTO responseEntity = userService.addUser(
        new AddUserReqDTO("test", "password", Set.of("STAFF")));

    assertThat(responseEntity).isEqualTo(expectedRes);
  }

  @Test
  void add_existing_user() {
    when(userDao.findByUsername(any(String.class))).thenReturn(Optional.of(new User()));

    FoundException thrown = assertThrows(
        FoundException.class,
        () -> userService.addUser(new AddUserReqDTO("test", "password", Set.of("STAFF"))));

    assertEquals(USER_ALREDY_PRESENT, thrown.getErr());
    assertEquals("POST " + USERS_ENDPOINT, thrown.getLocation());
  }

  @Test
  void update_existing_user() throws NotFoundException {
    User existingUser = new User();
    existingUser.setId(1L);
    final BasicResDTO expectedRes = new BasicResDTO(existingUser.getModified());

    when(userDao.findById(any(Long.class))).thenReturn(Optional.of(existingUser));
    when(userDao.save(any(User.class))).thenReturn(existingUser);
    BasicResDTO responseEntity = userService.updateUser(existingUser.getId(),
        new UpdateUserReqDTO("password", Set.of("STAFF")));

    assertThat(responseEntity).isEqualTo(expectedRes);
  }

  @Test
  void update_non_existing_user() {
    when(userDao.findById(any(Long.class))).thenReturn(Optional.empty());

    NotFoundException thrown = assertThrows(
        NotFoundException.class,
        () -> userService.updateUser(1L,
            new UpdateUserReqDTO("password", Set.of("STAFF"))));

    assertEquals(USER_NOT_FOUND, thrown.getErr());
    assertEquals("PUT " + USERS_ENDPOINT + '/' + 1, thrown.getLocation());
  }

  @Test
  void delete_existing_user() throws NotFoundException {
    User existingUser = new User();
    existingUser.setId(1L);
    final BasicResDTO expectedRes = new BasicResDTO();

    when(userDao.findById(any(Long.class))).thenReturn(Optional.of(existingUser));
    BasicResDTO responseEntity = userService.deleteUser(existingUser.getId());

    assertThat(responseEntity).isEqualTo(expectedRes);
  }

  @Test
  void delete_non_existing_user() {
    when(userDao.findById(any(Long.class))).thenReturn(Optional.empty());

    NotFoundException thrown = assertThrows(
        NotFoundException.class,
        () -> userService.deleteUser(1L));

    assertEquals(USER_NOT_FOUND, thrown.getErr());
    assertEquals("DELETE " + USERS_ENDPOINT + '/' + 1, thrown.getLocation());
  }

}
