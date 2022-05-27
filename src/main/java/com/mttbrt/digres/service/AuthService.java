package com.mttbrt.digres.service;

import com.mttbrt.digres.domain.dto.DataDTO;
import com.mttbrt.digres.domain.dto.ResponseDTO;
import com.mttbrt.digres.domain.entity.Authority;
import java.net.URISyntaxException;
import java.util.Set;
import javax.servlet.http.HttpServletResponse;

public interface AuthService {

  ResponseDTO registerUser(DataDTO data);

  ResponseDTO logoutUser(HttpServletResponse response);

}
