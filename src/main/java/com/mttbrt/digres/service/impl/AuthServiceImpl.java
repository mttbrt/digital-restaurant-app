package com.mttbrt.digres.service.impl;

import com.mttbrt.digres.service.AuthService;
import com.mttbrt.digres.utils.JWTHelper;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

  private final JWTHelper jwtHelper;

  public AuthServiceImpl(JWTHelper jwtHelper) {
    this.jwtHelper = jwtHelper;
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

}
