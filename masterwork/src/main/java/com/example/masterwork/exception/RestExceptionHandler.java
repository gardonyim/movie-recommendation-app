package com.example.masterwork.exception;

import com.example.masterwork.exception.model.ErrorDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {

  @ExceptionHandler(RequestCauseConflictException.class)
  public ResponseEntity<ErrorDTO> handleConflictCausedByRequest(RequestCauseConflictException e) {
    return ResponseEntity.status(409).body(new ErrorDTO(e.getMessage()));
  }

}
