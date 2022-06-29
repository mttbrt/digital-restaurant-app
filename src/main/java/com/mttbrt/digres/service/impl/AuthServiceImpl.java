package com.mttbrt.digres.service.impl;

import static com.mttbrt.digres.utils.PreprocessingHelper.createError;
import static com.mttbrt.digres.utils.StaticVariables.AUTHORITY_PREFIX;
import static com.mttbrt.digres.utils.StaticVariables.AUTH_NAMESPACE;
import static com.mttbrt.digres.utils.StaticVariables.REGISTER_ENDPOINT;

import com.mttbrt.digres.domain.Authority;
import com.mttbrt.digres.domain.User;
import com.mttbrt.digres.dto.request.UserReqDTO;
import com.mttbrt.digres.dto.response.ResponseDTO;
import com.mttbrt.digres.dto.response.item.UserResDTO;
import com.mttbrt.digres.dto.response.item.UsersResDTO;
import com.mttbrt.digres.repository.AuthorityDao;
import com.mttbrt.digres.repository.UserDao;
import com.mttbrt.digres.service.AuthService;
import com.mttbrt.digres.utils.JWTHelper;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

  private final UserDao userDao;
  private final AuthorityDao authRepository;
  private final PasswordEncoder passwordEncoder;
  private final JWTHelper jwtHelper;

  public AuthServiceImpl(UserDao userDao, AuthorityDao authRepository,
      PasswordEncoder passwordEncoder, JWTHelper jwtHelper) {
    this.userDao = userDao;
    this.authRepository = authRepository;
    this.passwordEncoder = passwordEncoder;
    this.jwtHelper = jwtHelper;
  }

  @Override
  public ResponseDTO registerUser(UserReqDTO request) {
    String reqPath = AUTH_NAMESPACE + REGISTER_ENDPOINT;

    if (userDao.findByUsername(request.getUsername()) != null) {
      return createError(HttpStatus.BAD_REQUEST.value(),
          "User already exists.",
          "An account with the given username already exists.",
          reqPath);
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
  public boolean logoutUser(HttpServletResponse response) {
    try {
      jwtHelper.removeAuthentication(response);
    } catch (Exception e) {
      return false;
    }
    return true;
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
