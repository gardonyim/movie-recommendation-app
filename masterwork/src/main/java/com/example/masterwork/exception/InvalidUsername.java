package com.example.masterwork.exception;

import org.springframework.security.core.AuthenticationException;

public class InvalidUsername extends AuthenticationException {
  public InvalidUsername() {
    super("Invalid username");
  }
}
