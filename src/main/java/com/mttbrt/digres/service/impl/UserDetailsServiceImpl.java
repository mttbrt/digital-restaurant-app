package com.mttbrt.digres.service.impl;

import com.mttbrt.digres.auth.UserDetailsImpl;
import com.mttbrt.digres.domain.User;
import com.mttbrt.digres.repository.UserDao;
import java.util.Optional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserDao userDao;

  public UserDetailsServiceImpl(UserDao userDao) {
    this.userDao = userDao;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<User> user = userDao.findByUsername(username);
    if (user.isEmpty()) {
      throw new UsernameNotFoundException(username);
    }
    return new UserDetailsImpl(user.get());
  }

}
