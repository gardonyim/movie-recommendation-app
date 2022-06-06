package com.example.masterwork.security;

import com.example.masterwork.exception.InvalidUsername;
import com.example.masterwork.viewer.ViewerService;
import com.example.masterwork.viewer.model.Viewer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.filter.OncePerRequestFilter;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.masterwork.security.SecurityConfiguration.PRIVATE_ENDPOINTS;

public class JwtTokenValidatorFilter extends OncePerRequestFilter {

  private ViewerService viewerService;

  @Autowired
  public JwtTokenValidatorFilter(ViewerService viewerService) {
    this.viewerService = viewerService;
  }

  @Override
  public Environment getEnvironment() {
    return super.getEnvironment();
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    if (shouldNotFilter(request)) {
      filterChain.doFilter(request, response);
      return;
    }
    String jwt = request.getHeader("Authorization").substring(7).trim();

    SecretKey key = Keys.hmacShaKeyFor(
        getJWT_KEY().getBytes(StandardCharsets.UTF_8));

    Authentication auth = new UsernamePasswordAuthenticationToken(convert(jwt, key), null, null);
    SecurityContextHolder.getContext().setAuthentication(auth);

    filterChain.doFilter(request, response);
  }

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) {
    String path = request.getServletPath();
    List<String> privateEndpoints = Arrays.stream(PRIVATE_ENDPOINTS)
        .map(endpoint -> endpoint.replaceAll("/\\*\\*", ""))
        .collect(Collectors.toList());
    return !privateEndpoints.stream().anyMatch(e -> path.startsWith(e));
  }

  private Viewer convert(String jwt, SecretKey key) {
    Claims claims = Jwts.parserBuilder()
        .setSigningKey(key)
        .build()
        .parseClaimsJws(jwt)
        .getBody();
    String username = String.valueOf(claims.get("username"));
    return viewerService.fetchByUsername(username).orElseThrow(InvalidUsername::new);
  }

  private String getJWT_KEY() {
    return getEnvironment().getProperty("JWT_KEY");
  }

}
