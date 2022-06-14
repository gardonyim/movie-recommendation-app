package com.example.masterwork.exception.exceptions;

public class GenreNotFoundException extends ResourceNotFoundException {

  public GenreNotFoundException() {
    super("Genre is not in the database");
  }

}
