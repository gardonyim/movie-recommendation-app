package com.example.masterwork.exception.model;

public class ErrorDTO {

  private String status;
  private String message;

  public ErrorDTO(String message) {
    this.status = "error";
    this.message = message;
  }
}
