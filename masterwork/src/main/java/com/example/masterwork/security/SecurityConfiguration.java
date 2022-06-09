package com.example.masterwork.security;

import com.example.masterwork.viewer.ViewerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  public static final String[] PRIVATE_ENDPOINTS = {
      "/movie/**",
      "/director/**",
      "/actor/**"
  };
  private ViewerService viewerService;

  @Autowired
  public SecurityConfiguration(ViewerService viewerService) {
    this.viewerService = viewerService;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .cors()
        .and()
        .addFilterBefore(new JwtTokenValidatorFilter(viewerService),
            BasicAuthenticationFilter.class)
        .authorizeRequests()
        .antMatchers(PRIVATE_ENDPOINTS).authenticated()
        .antMatchers("/**").permitAll()
        .and()
        .csrf().disable()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .exceptionHandling().authenticationEntryPoint(new AuthenticationExceptionHandler());
  }
}
