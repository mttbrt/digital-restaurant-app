package com.mttbrt.digres.service.impl;

import static com.mttbrt.digres.utils.PreprocessingHelper.createError;
import static com.mttbrt.digres.utils.StaticVariables.API_NAMESPACE;
import static com.mttbrt.digres.utils.StaticVariables.AUTHORITY_PREFIX;
import static com.mttbrt.digres.utils.StaticVariables.USERS_ENDPOINT;

import com.mttbrt.digres.domain.Authority;
import com.mttbrt.digres.domain.User;
import com.mttbrt.digres.dto.request.AddUserReqDTO;
import com.mttbrt.digres.dto.request.UpdateUserReqDTO;
import com.mttbrt.digres.dto.response.ResponseDTO;
import com.mttbrt.digres.dto.response.data.item.UserResDTO;
import com.mttbrt.digres.dto.response.data.item.UsersResDTO;
import com.mttbrt.digres.repository.AuthorityDao;
import com.mttbrt.digres.repository.UserDao;
import com.mttbrt.digres.service.UserService;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  private final UserDao userDao;
  private final AuthorityDao authRepository;
  private final PasswordEncoder passwordEncoder;

  public UserServiceImpl(UserDao userDao, AuthorityDao authRepository,
      PasswordEncoder passwordEncoder) {
    this.userDao = userDao;
    this.authRepository = authRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public ResponseDTO getUsers() {
    List<UserResDTO> userList = userDao.findAll()
        .stream()
        .map(user -> new UserResDTO(user.getUsername(), user.getRoles()))
        .collect(Collectors.toList());
    UsersResDTO userRes = new UsersResDTO(userList);

    return new ResponseDTO(userRes);
  }

  @Override
  public ResponseDTO addUser(AddUserReqDTO request) {
    if (userDao.findByUsername(request.getUsername()) != null) {
      return createError(HttpStatus.BAD_REQUEST.value(),
          "User already exists.",
          "An account with the given username already exists.",
          "POST: " + USERS_ENDPOINT);
    }

    User newUser = new User(
        request.getUsername(),
        passwordEncoder.encode(request.getPassword()),
        getAuthorities(request.getRoles()));
    userDao.save(newUser);

    List<UserResDTO> users = List.of(new UserResDTO(request.getUsername(), request.getRoles()));
    UsersResDTO data = new UsersResDTO(users);
    return new ResponseDTO(data);
  }

  @Override
  public ResponseDTO getUserByUsername(String username) {
    User user = userDao.findByUsername(username);
    if (user == null) {
      return createError(HttpStatus.BAD_REQUEST.value(),
          "User does not exist.",
          "An account with the given username does not exist.",
          "GET: " + USERS_ENDPOINT + '/' + username);
    }

    List<UserResDTO> users = List.of(new UserResDTO(user.getUsername(), user.getRoles()));
    UsersResDTO data = new UsersResDTO(users);
    return new ResponseDTO(data);
  }

  @Override
  public ResponseDTO updateUser(String username, UpdateUserReqDTO request) {
    User user = userDao.findByUsername(username);
    if (user == null) {
      return createError(HttpStatus.BAD_REQUEST.value(),
          "User does not exist.",
          "An account with the given username does not exist.",
          "PUT: " + USERS_ENDPOINT + '/' + username);
    }

    user.setPassword(passwordEncoder.encode(request.getPassword()));
    user.setAuthorities(getAuthorities(request.getRoles()));
    userDao.save(user);

    List<UserResDTO> users = List.of(new UserResDTO(username, request.getRoles()));
    UsersResDTO data = new UsersResDTO(users);
    return new ResponseDTO(data);
  }

  @Override
  public ResponseDTO deleteUser(String username) {
    User user = userDao.findByUsername(username);
    if (user == null) {
      return createError(HttpStatus.BAD_REQUEST.value(),
          "User does not exist.",
          "An account with the given username does not exist.",
          "DELETE: " + USERS_ENDPOINT + '/' + username);
    }
    userDao.delete(user);

    List<UserResDTO> users = List.of(new UserResDTO(user.getUsername(), user.getRoles()));
    UsersResDTO data = new UsersResDTO(users);
    return new ResponseDTO(data);
  }

  private Set<Authority> getAuthorities(Set<String> roles) {
    Set<Authority> authorities = new HashSet<>();

    roles.forEach(role -> {
      Authority authority = authRepository.findByName(AUTHORITY_PREFIX + role);
      authorities.add(authority);
    });

    return authorities;
  }

}
