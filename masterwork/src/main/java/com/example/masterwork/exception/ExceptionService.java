package com.example.masterwork.exception;

import com.example.masterwork.exception.model.ErrorDTO;
import org.springframework.web.bind.MethodArgumentNotValidException;

public interface ExceptionService {

  ErrorDTO createErrorDTO(MethodArgumentNotValidException e);

}
