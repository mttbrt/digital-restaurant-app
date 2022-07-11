package com.mttbrt.digres.config.filter;

import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class ResponseHeadersFilter extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(final HttpServletRequest request,
      final HttpServletResponse response, final FilterChain filterChain)
      throws ServletException, IOException {

    ZonedDateTime zdt = ZonedDateTime.now().withZoneSameInstant(ZoneId.of("GMT"));
    response.addHeader("Expires", zdt.format(DateTimeFormatter.RFC_1123_DATE_TIME));
    response.addHeader("Cache-Control", "no-cache");
    response.addHeader("Pragma", "no-cache");

    filterChain.doFilter(request, response);
  }

}
