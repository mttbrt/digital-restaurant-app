package com.mttbrt.digres.service;

import com.mttbrt.digres.dto.request.UserReqDTO;
import com.mttbrt.digres.dto.response.ResponseDTO;
import javax.servlet.http.HttpServletResponse;

public interface AuthService {

  ResponseDTO registerUser(UserReqDTO data);

  boolean logoutUser(HttpServletResponse response);

}
