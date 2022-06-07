package com.example.masterwork.exception;

import com.example.masterwork.exception.model.ErrorDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class ExceptionServiceImpl implements ExceptionService {
  @Override
  public ErrorDTO createErrorDTO(MethodArgumentNotValidException e) {
    List<String> errorMessages = new ArrayList<>();
    e.getBindingResult().getAllErrors().forEach((objectError -> {
      String errorMessage = objectError.getDefaultMessage();
      errorMessages.add(errorMessage.toLowerCase());
    }));
    errorMessages.sort(Comparator.naturalOrder());
    String message = String.join(", ", errorMessages);
    return new ErrorDTO(message.substring(0, 1).toUpperCase() + message.substring(1));
  }

}
