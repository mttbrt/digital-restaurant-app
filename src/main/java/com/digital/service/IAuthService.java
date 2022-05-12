package com.digital.service;

import com.digital.domain.entity.Authority;
import com.digital.service.impl.AuthServiceImpl;
import java.util.HashSet;
import java.util.Set;

public interface IAuthService {

  boolean isUserRegistered(String username);

  void registerUser(String username, String password, Set<String> roles);

  Set<Authority> getAuthorities(Set<String> roles);

}
