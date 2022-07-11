package com.mttbrt.digres.service.impl;

import static com.mttbrt.digres.dto.response.Errors.USER_ALREDY_PRESENT;
import static com.mttbrt.digres.dto.response.Errors.USER_NOT_FOUND;
import static com.mttbrt.digres.utils.ConvertUserToResDTO.convertToDTO;
import static com.mttbrt.digres.utils.StaticVariables.AUTHORITY_PREFIX;
import static com.mttbrt.digres.utils.StaticVariables.USERS_ENDPOINT;

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
import com.mttbrt.digres.service.UserService;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
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
    AtomicReference<OffsetDateTime> latestModified = new AtomicReference<>(OffsetDateTime.MIN);
    List<UserResDTO> userList = userDao.findAll().stream().map(user -> {
      if (user.getModified().isAfter(latestModified.get())) {
        latestModified.set(user.getModified());
      }
      return convertToDTO(user);
    }).collect(Collectors.toList());
    UsersResDTO userRes = new UsersResDTO(userList);

    return new ResponseDTO(userRes,
        latestModified.get().isAfter(OffsetDateTime.MIN) ? latestModified.get()
            : OffsetDateTime.now());
  }

  @Override
  public ResponseDTO getUser(long userId) throws NotFoundException {
    Optional<User> user = userDao.findById(userId);
    if (user.isEmpty()) {
      throw new NotFoundException(USER_NOT_FOUND, "GET " + USERS_ENDPOINT + '/' + userId);
    }

    List<UserResDTO> users = List.of(convertToDTO(user.get()));
    UsersResDTO data = new UsersResDTO(users);
    return new ResponseDTO(data, user.get().getModified());
  }

  @Override
  public BasicResDTO addUser(AddUserReqDTO request) throws FoundException {
    Optional<User> user = userDao.findByUsername(request.getUsername());
    if (user.isPresent()) {
      throw new FoundException(USER_ALREDY_PRESENT, "POST " + USERS_ENDPOINT);
    }

    User newUser = new User(request.getUsername(), passwordEncoder.encode(request.getPassword()),
        getAuthorities(request.getRoles()));
    User saved = userDao.save(newUser);

    return new BasicResDTO(saved.getModified(), USERS_ENDPOINT + '/' + saved.getId());
  }

  @Override
  public BasicResDTO updateUser(long userId, UpdateUserReqDTO request) throws NotFoundException {
    Optional<User> user = userDao.findById(userId);
    if (user.isEmpty()) {
      throw new NotFoundException(USER_NOT_FOUND, "PUT " + USERS_ENDPOINT + '/' + userId);
    }

    User userObj = user.get();
    userObj.setPassword(passwordEncoder.encode(request.getPassword()));
    userObj.setAuthorities(getAuthorities(request.getRoles()));
    User saved = userDao.save(userObj);

    return new BasicResDTO(saved.getModified());
  }

  @Override
  public BasicResDTO deleteUser(long userId) throws NotFoundException {
    Optional<User> user = userDao.findById(userId);
    if (user.isEmpty()) {
      throw new NotFoundException(USER_NOT_FOUND, "DELETE " + USERS_ENDPOINT + '/' + userId);
    }

    userDao.delete(user.get());

    return new BasicResDTO();
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
