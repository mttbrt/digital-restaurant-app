package com.digital.service;

import com.digital.model.AppUserDetails;
import com.digital.model.entity.User;
import com.digital.repository.UserRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AppUserDetailsService implements UserDetailsService {

  private UserRepository userRepository;

  @Autowired
  public void setUserRepository(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByUsername(username);
    if (user == null) {
      throw new UsernameNotFoundException(username);
    }

    if (user.getUsername().equals("user"))
      return new AppUserDetails(user, Collections.singletonList(new SimpleGrantedAuthority("ROLE_STAFF")));
    else if (user.getUsername().equals("admin"))
      return new AppUserDetails(user, Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN")));
    return new AppUserDetails(user, null);
  }

}
