package com.example.masterwork.authentication;

import com.example.masterwork.authentication.models.LoginDTO;
import com.example.masterwork.authentication.models.TokenDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class AuthController {

  private AuthService authService;

  @Autowired
  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  @PostMapping("/login")
  public ResponseEntity<TokenDTO> handleLogin(@Valid @RequestBody LoginDTO loginDTO) {
    return ResponseEntity.ok(authService.authenticate(loginDTO));
  }
}
