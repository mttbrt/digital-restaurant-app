package com.mttbrt.digres.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

@Component
public class JWTHelper {

  private final UserDetailsService userDetailsService;
  @Value("${csrf.cookie.name}")
  private String CSRF_COOKIE_NAME;
  @Value("${csrf.cookie.path}")
  private String CSRF_COOKIE_PATH;
  @Value("${csrf.cookie.domain}")
  private String CSRF_COOKIE_DOMAIN;
  @Value("${jwt.cookie.name}")
  private String JWT_COOKIE_NAME;
  @Value("${jwt.cookie.path}")
  private String JWT_COOKIE_PATH;
  @Value("${jwt.cookie.domain}")
  private String JWT_COOKIE_DOMAIN;
  @Value("${jwt.cookie.maxAge}")
  private int JWT_COOKIE_MAXAGE;
  @Value("${jwt.secret}")
  private String JWT_SECRET;
  @Value("${jwt.expirationTime}")
  private int JWT_EXPIRATION_TIME;

  public JWTHelper(UserDetailsService userDetailsService) {
    this.userDetailsService = userDetailsService;
  }

  public void addAuthentication(HttpServletResponse res, Authentication auth) {
    // create new JWT
    String jwt = generateJwt(auth.getName());

    // set JWT as cookie
    res.addCookie(prepareCookie(JWT_COOKIE_NAME, jwt, JWT_COOKIE_DOMAIN, JWT_COOKIE_PATH, true,
        JWT_COOKIE_MAXAGE));
  }

  public Authentication getAuthentication(HttpServletRequest request) {
    Cookie cookie = WebUtils.getCookie(request, JWT_COOKIE_NAME);
    String token = cookie != null ? cookie.getValue() : null;

    if (token != null) {
      // validate JWT
      Claims claims = Jwts.parser()
          .setSigningKey(JWT_SECRET)
          .parseClaimsJws(token)
          .getBody();

      String username = claims.getSubject();
      if (username != null) {
        // verify user
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(username, null,
            userDetails.getAuthorities());
      }
    }
    return null;
  }

  public void removeAuthentication(HttpServletResponse res) {
    res.addCookie(
        prepareCookie(JWT_COOKIE_NAME, null, JWT_COOKIE_DOMAIN, JWT_COOKIE_PATH, true, 0));
    res.addCookie(
        prepareCookie(CSRF_COOKIE_NAME, null, CSRF_COOKIE_DOMAIN, CSRF_COOKIE_PATH, false, 0));
  }

  public String generateJwt(String subjectName) {
    Date now = new Date();
    return Jwts.builder()
        .setSubject(subjectName)
        .setIssuedAt(now)
        .setExpiration(new Date(now.getTime() + JWT_EXPIRATION_TIME))
        .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
        .compact();
  }

  public Cookie prepareCookie(String name, String content, String domain, String path,
      boolean httpOnly, int maxAge) {
    Cookie cookie = new Cookie(name, content);
    cookie.setDomain(domain);
    cookie.setPath(path);
    cookie.setHttpOnly(httpOnly);
    cookie.setMaxAge(maxAge);
    return cookie;
  }

}
