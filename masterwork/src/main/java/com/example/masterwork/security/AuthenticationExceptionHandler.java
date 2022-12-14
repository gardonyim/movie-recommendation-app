package com.example.masterwork.security;

import com.example.masterwork.exception.model.ErrorDTO;
import com.google.gson.Gson;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class AuthenticationExceptionHandler implements AuthenticationEntryPoint {
  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
    String token = request.getHeader("Authorization");
    String errorMessage = token == null ? "No authentication token is provided!" : "Authentication token is invalid!";
    ErrorDTO error = new ErrorDTO(errorMessage);
    response.setStatus(401);
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    String errorJson = new Gson().toJson(error);
    PrintWriter out = response.getWriter();
    out.println(errorJson);
    out.flush();
  }
}
