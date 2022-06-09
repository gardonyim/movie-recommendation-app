package com.example.masterwork.exception;

import com.example.masterwork.exception.exceptions.InvalidCredentialsException;
import com.example.masterwork.exception.exceptions.RequestCauseConflictException;
import com.example.masterwork.exception.exceptions.RequestForbiddenResourceException;
import com.example.masterwork.exception.model.ErrorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {

  private ExceptionService exceptionService;

  @Autowired
  public RestExceptionHandler(ExceptionService exceptionService) {
    this.exceptionService = exceptionService;
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorDTO> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
    ErrorDTO errorDTO = exceptionService.createErrorDTO(e);
    return ResponseEntity.status(400).body(errorDTO);
  }

  @ExceptionHandler(InvalidCredentialsException.class)
  public ResponseEntity<ErrorDTO> handleInvalidCredentials(InvalidCredentialsException e) {
    return ResponseEntity.status(401).body(new ErrorDTO(e.getMessage()));
  }

  @ExceptionHandler(RequestForbiddenResourceException.class)
  public ResponseEntity<ErrorDTO> handleRequestForbiddenResource(RequestForbiddenResourceException e) {
    return ResponseEntity.status(403).body(new ErrorDTO(e.getMessage()));
  }

  @ExceptionHandler(RequestCauseConflictException.class)
  public ResponseEntity<ErrorDTO> handleConflictCausedByRequest(RequestCauseConflictException e) {
    return ResponseEntity.status(409).body(new ErrorDTO(e.getMessage()));
  }

}
