package com.mttbrt.digres.service.impl;

import com.mttbrt.digres.domain.entity.Authority;
import com.mttbrt.digres.domain.entity.User;
import com.mttbrt.digres.repository.AuthorityRepository;
import com.mttbrt.digres.repository.UserRepository;
import com.mttbrt.digres.service.AuthService;
import java.util.HashSet;
import java.util.Set;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

  private static final String AUTHORITY_PREFIX = "ROLE_";

  private final UserRepository userRepository;
  private final AuthorityRepository authRepository;
  private final PasswordEncoder passwordEncoder;

  public AuthServiceImpl(UserRepository userRepository, AuthorityRepository authRepository,
      PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.authRepository = authRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public boolean isUserRegistered(String username) {
    return userRepository.findByUsername(username) != null;
  }

  @Override
  public void registerUser(String username, String password, Set<String> roles) {
    Set<Authority> authorities = getAuthorities(roles);
    User newUser = new User(username, passwordEncoder.encode(password), authorities);
    userRepository.save(newUser);
  }

  @Override
  public Set<Authority> getAuthorities(Set<String> roles) {
    Set<Authority> authorities = new HashSet<>();

    roles.forEach(role -> {
      Authority authority = authRepository.findByName(AuthServiceImpl.AUTHORITY_PREFIX + role);
      authorities.add(authority);
    });

    return authorities;
  }

}
