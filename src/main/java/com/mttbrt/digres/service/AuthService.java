package com.mttbrt.digres.service;

import com.mttbrt.digres.dto.request.RegisterUserDTO;
import com.mttbrt.digres.dto.response.ResponseDTO;
import javax.servlet.http.HttpServletResponse;

public interface AuthService {

  ResponseDTO registerUser(RegisterUserDTO data);

  boolean logoutUser(HttpServletResponse response);

}
