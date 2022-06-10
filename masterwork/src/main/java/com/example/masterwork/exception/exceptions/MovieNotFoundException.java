package com.example.masterwork.exception.exceptions;

public class MovieNotFoundException extends ResourceNotFoundException {

  public MovieNotFoundException() {
    super("Movie is not in the database.");
  }
}
