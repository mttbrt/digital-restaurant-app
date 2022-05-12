package com.digital.utils;

import com.digital.domain.auth.UserDetailsImpl;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.util.Date;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

@Component
public class JWTUtils {

  private final static String COOKIE_PATH = "/api/v1";

  @Value("${jwt.secret}")
  private String jwtSecret;
  @Value("${jwt.expirationMs}")
  private int jwtExpirationMs;
  @Value("${jwt.cookie.name}")
  private String jwtCookieName;

  public ResponseCookie generateJWTCookie(UserDetailsImpl userPrincipal) {
    String jwt = generateJWTFromUsername(userPrincipal.getUsername());
    return ResponseCookie.from(jwtCookieName, jwt)
        .path(COOKIE_PATH)
        .maxAge(60 * 60 * 6)
        .httpOnly(true)
        .build();
  }

  public ResponseCookie generateEmptyJWTCookie() {
    return ResponseCookie.from(jwtCookieName, "")
        .path(COOKIE_PATH)
        .maxAge(0)
        .build();
  }

  public String getJWTFromCookies(HttpServletRequest request) {
    Cookie cookie = WebUtils.getCookie(request, jwtCookieName);
    return cookie != null ? cookie.getValue() : null;
  }

  public String getUsernameFromJWT(String token) {
    return Jwts.parser()
        .setSigningKey(jwtSecret)
        .parseClaimsJws(token)
        .getBody()
        .getSubject();
  }

  public boolean validateJWT(String authToken) {
    try {
      Jwts.parser()
          .setSigningKey(jwtSecret)
          .parseClaimsJws(authToken);
      return true;
    } catch (SignatureException e) {
    } catch (MalformedJwtException e) {
    } catch (ExpiredJwtException e) {
    } catch (UnsupportedJwtException e) {
    } catch (IllegalArgumentException e) {
    }
    return false;
  }

  private String generateJWTFromUsername(String username) {
    Date now = new Date();

    return Jwts.builder()
        .setSubject(username)
        .setIssuedAt(now)
        .setExpiration(new Date(now.getTime() + jwtExpirationMs))
        .signWith(SignatureAlgorithm.HS512, jwtSecret)
        .compact();
  }

}
