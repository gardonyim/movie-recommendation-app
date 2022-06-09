package com.example.masterwork.exception.exceptions;

import org.springframework.security.core.AuthenticationException;

public class InvalidCredentialsException extends AuthenticationException {
  public InvalidCredentialsException() {
    super("Invalid username");
  }

  public InvalidCredentialsException(String msg) {
    super(msg);
  }
}
