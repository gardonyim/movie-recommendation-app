package com.example.masterwork.exception.exceptions;

public class DirectorNotFoundException extends ResourceNotFoundException {

  public DirectorNotFoundException() {
    super("Director is not in the database");
  }
}
