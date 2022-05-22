package com.mttbrt.digres.domain.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mttbrt.digres.domain.dto.ErrorsDTO;
import com.mttbrt.digres.domain.dto.resource.ErrorDTO;
import com.mttbrt.digres.domain.dto.resource.SourceDTO;
import java.io.IOException;
import java.util.Collections;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

public class AuthEntryPointJwt implements AuthenticationEntryPoint {

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException authException) throws IOException {
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

    ErrorsDTO errors = new ErrorsDTO(
        Collections.singletonList(new ErrorDTO(
            HttpStatus.UNAUTHORIZED.value(),
            "invalid-token",
            "Invalid token provided.",
            "The token provided is not valid.",
            new SourceDTO(request.getServletPath())
        ))
    );

    final ObjectMapper mapper = new ObjectMapper();
    mapper.writeValue(response.getOutputStream(), errors);
  }

}
