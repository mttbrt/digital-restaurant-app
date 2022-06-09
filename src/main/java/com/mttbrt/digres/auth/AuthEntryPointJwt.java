package com.mttbrt.digres.auth;

import static com.mttbrt.digres.utils.PreprocessingHelper.createError;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mttbrt.digres.dto.response.ResponseDTO;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException authException) throws IOException {
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

    ResponseDTO res = createError(HttpStatus.BAD_REQUEST.value(),
        "Request not valid.",
        "The request is not valid.",
        request.getServletPath());

    final ObjectMapper mapper = new ObjectMapper();
    mapper.writeValue(response.getOutputStream(), res);
  }

}
