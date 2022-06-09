package com.example.masterwork.exception.exceptions;

public class ActorNotFoundException extends ResourceNotFoundException {

  public ActorNotFoundException() {
    super("Actor/actress is not in the database");
  }
}
