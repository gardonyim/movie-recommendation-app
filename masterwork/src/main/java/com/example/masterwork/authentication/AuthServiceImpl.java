package com.example.masterwork.authentication;

import com.example.masterwork.authentication.models.LoginDTO;
import com.example.masterwork.authentication.models.TokenDTO;
import com.example.masterwork.exception.exceptions.InvalidCredentialsException;
import com.example.masterwork.exception.exceptions.RequestForbiddenResourceException;
import com.example.masterwork.viewer.ViewerService;
import com.example.masterwork.viewer.model.Viewer;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

  private ViewerService viewerService;
  private PasswordEncoder passwordEncoder;

  @Value("${security.jwt-key:}")
  private String jwtKey;

  @Autowired
  public AuthServiceImpl(ViewerService viewerService, PasswordEncoder passwordEncoder) {
    this.viewerService = viewerService;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public TokenDTO authenticate(LoginDTO loginDTO) {
    Optional<Viewer> viewer = viewerService.fetchByUsername(loginDTO.getUsername());
    if (viewer.isPresent()) {
      if (passwordEncoder.matches(loginDTO.getPassword(), viewer.get().getPassword())) {
        if (viewer.get().getEnabled()) {
          return new TokenDTO("ok", generateJwtString(viewer.get()));
        } else {
          throw new RequestForbiddenResourceException("Unactivated account");
        }
      }
    }
    throw new InvalidCredentialsException("Username or password is incorrect.");
  }

  private String generateJwtString(Viewer viewer) {
    SecretKey key = Keys.hmacShaKeyFor(jwtKey.getBytes(StandardCharsets.UTF_8));
    return Jwts.builder()
        .setSubject("masterwork")
        .claim("username", viewer.getUsername())
        .setIssuedAt(Date.from(Instant.now()))
        .setExpiration(Date.from(Instant.now().plus(1, ChronoUnit.HOURS)))
        .signWith(key)
        .compact();
  }

}
