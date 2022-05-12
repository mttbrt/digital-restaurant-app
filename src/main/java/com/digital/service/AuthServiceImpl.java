package com.digital.service;

import com.digital.domain.auth.UserDetailsImpl;
import com.digital.domain.entity.Authority;
import com.digital.domain.entity.User;
import com.digital.repository.AuthorityRepository;
import com.digital.repository.UserRepository;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl {

  private static final String AUTHORITY_PREFIX = "ROLE_";

  private final UserRepository userRepository;
  private final AuthorityRepository authRepository;
  private final PasswordEncoder passwordEncoder;

  @Autowired
  public AuthServiceImpl(UserRepository userRepository,
      AuthorityRepository authRepository,
      PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.authRepository = authRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public boolean isUserRegistered(String username) {
    return userRepository.findByUsername(username) != null;
  }

  public void registerUser(String username, String password, Set<String> roles) {
    Set<Authority> authorities = getAuthorities(roles);
    User newUser = new User(username, passwordEncoder.encode(password), authorities);
    userRepository.save(newUser);
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
