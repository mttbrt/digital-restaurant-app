package com.mttbrt.digres.config.filter;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import com.mttbrt.digres.utils.JWTHelper;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

@Component
public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

  private static final String LOGIN_URL = "/api/v1/auth/login";
  private JWTHelper jwtHelper;

  public JWTLoginFilter(AuthenticationManager authManager) {
    super(new AntPathRequestMatcher(LOGIN_URL));
    setAuthenticationManager(authManager);
  }

  public void setJwtHelper(JWTHelper jwtHelper) {
    this.jwtHelper = jwtHelper;
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
      throws IOException {
    String headerUsrPsw = req.getHeader(AUTHORIZATION).substring(6); // remove "Basic "
    String decoded = new String(Base64.decodeBase64(headerUsrPsw));
    String[] usr_psw = decoded.split(":");

    return getAuthenticationManager().authenticate(
        new UsernamePasswordAuthenticationToken(usr_psw[0], usr_psw[1])
    );
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res,
      FilterChain chain, Authentication auth) {
    jwtHelper.addAuthentication(res, auth);
    SecurityContextHolder.getContext().setAuthentication(auth);
  }

}
