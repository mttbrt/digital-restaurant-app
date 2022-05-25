package com.mttbrt.digres.config;

import com.mttbrt.digres.config.filter.JWTAuthenticationFilter;
import com.mttbrt.digres.config.filter.JWTLoginFilter;
import com.mttbrt.digres.domain.auth.AuthEntryPointJwt;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final UserDetailsService userDetailsService;
  @Value("${csrf.cookie.name}")
  private String CSRF_COOKIE_NAME;
  @Value("${csrf.cookie.path}")
  private String CSRF_COOKIE_PATH;
  @Value("${csrf.cookie.domain}")
  private String CSRF_COOKIE_DOMAIN;
  @Value("${app.auth.endpoint}")
  private String AUTH_ENDPOINT;

  public SecurityConfig(UserDetailsService userDetailsService) {
    this.userDetailsService = userDetailsService;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .cors()
        .and()
        .csrf()
        .ignoringAntMatchers(AUTH_ENDPOINT + "/login")
        .csrfTokenRepository(getCsrfTokenRepository())
        .and()
        .exceptionHandling()
        .authenticationEntryPoint(authEntryPointJwt())
        .and()
        // do not create any session
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authorizeRequests()
        .antMatchers(AUTH_ENDPOINT + "/login", AUTH_ENDPOINT + "/logout")
        .permitAll()
        .anyRequest()
        .authenticated()
        .and()
        .addFilterAfter(jwtLoginFilter(), ExceptionTranslationFilter.class)
        .addFilterAfter(jwtAuthenticationFilter(), ExceptionTranslationFilter.class);
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Bean
  public JWTLoginFilter jwtLoginFilter() throws Exception {
    return new JWTLoginFilter(authenticationManagerBean());
  }

  @Bean
  public JWTAuthenticationFilter jwtAuthenticationFilter() {
    return new JWTAuthenticationFilter();
  }

  @Bean
  public AuthEntryPointJwt authEntryPointJwt() {
    return new AuthEntryPointJwt();
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowedOrigins(List.of("http://localhost:3000/"));
    config.setAllowedMethods(List.of("GET", "POST", "OPTION"));
    config.setAllowCredentials(true); // to let the client access the cookies
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", config);
    return source;
  }

  private CsrfTokenRepository getCsrfTokenRepository() {
    CookieCsrfTokenRepository tokenRepository = CookieCsrfTokenRepository.withHttpOnlyFalse();
    tokenRepository.setCookieName(CSRF_COOKIE_NAME);
    tokenRepository.setCookieDomain(CSRF_COOKIE_DOMAIN);
    tokenRepository.setCookiePath(CSRF_COOKIE_PATH);
    return tokenRepository;
  }

}
