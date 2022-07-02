package com.mttbrt.digres.service;

import javax.servlet.http.HttpServletResponse;

public interface AuthService {

  boolean logoutUser(HttpServletResponse response);

}
