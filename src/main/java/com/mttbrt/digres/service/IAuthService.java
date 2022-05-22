package com.mttbrt.digres.service;

import com.mttbrt.digres.domain.entity.Authority;
import java.util.Set;

public interface IAuthService {

  boolean isUserRegistered(String username);

  void registerUser(String username, String password, Set<String> roles);

  Set<Authority> getAuthorities(Set<String> roles);

}
