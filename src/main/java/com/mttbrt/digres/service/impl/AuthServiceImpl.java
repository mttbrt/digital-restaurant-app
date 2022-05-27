package com.mttbrt.digres.service.impl;

import static com.mttbrt.digres.domain.ErrorCode.USER_REGISTRATION_ERROR;
import static com.mttbrt.digres.utils.PreprocessingHelper.castRequest;
import static com.mttbrt.digres.utils.PreprocessingHelper.createError;
import static com.mttbrt.digres.utils.StaticVariables.AUTHORITY_PREFIX;
import static com.mttbrt.digres.utils.StaticVariables.AUTH_ENDPOINT;
import static com.mttbrt.digres.utils.StaticVariables.REGISTER_ENDPOINT;

import com.mttbrt.digres.domain.dto.DataDTO;
import com.mttbrt.digres.domain.dto.PositiveResponseDTO;
import com.mttbrt.digres.domain.dto.ResponseDTO;
import com.mttbrt.digres.domain.dto.resource.RegisterResourceDTO;
import com.mttbrt.digres.domain.dto.resource.attribute.RegisterDTO;
import com.mttbrt.digres.domain.entity.Authority;
import com.mttbrt.digres.domain.entity.User;
import com.mttbrt.digres.repository.AuthorityRepository;
import com.mttbrt.digres.repository.UserRepository;
import com.mttbrt.digres.service.AuthService;
import com.mttbrt.digres.utils.JWTHelper;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

  private final UserRepository userRepository;
  private final AuthorityRepository authRepository;
  private final PasswordEncoder passwordEncoder;
  private final JWTHelper jwtHelper;

  public AuthServiceImpl(UserRepository userRepository, AuthorityRepository authRepository,
      PasswordEncoder passwordEncoder, JWTHelper jwtHelper) {
    this.userRepository = userRepository;
    this.authRepository = authRepository;
    this.passwordEncoder = passwordEncoder;
    this.jwtHelper = jwtHelper;
  }

  @Override
  public ResponseDTO registerUser(DataDTO data) {
    String reqPath = AUTH_ENDPOINT + REGISTER_ENDPOINT;

    ResponseDTO res = castRequest(reqPath, data, RegisterResourceDTO.class);
    if (res != null) {
      return res;
    }

    RegisterDTO registerReq = ((RegisterResourceDTO) data.getData()).getAttributes();
    if (userRepository.findByUsername(registerReq.getUsername()) != null) {
      return createError(HttpStatus.BAD_REQUEST.value(),
          USER_REGISTRATION_ERROR,
          "User already exists.",
          "An account with the given username already exists.",
          reqPath);
    }

    User newUser = new User(
        registerReq.getUsername(),
        passwordEncoder.encode(registerReq.getPassword()),
        getAuthorities(registerReq.getRoles()));
    userRepository.save(newUser);

    return new PositiveResponseDTO("/api/v1/user/" + registerReq.getUsername());
  }

  @Override
  public ResponseDTO logoutUser(HttpServletResponse response) {
    jwtHelper.removeAuthentication(response);
    return new PositiveResponseDTO("ok");
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
