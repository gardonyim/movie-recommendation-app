package com.example.masterwork.authentication.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
public class LoginDTO {

  @NotNull(message = "Username is required")
  private String username;
  @NotNull(message = "Password is required")
  private String password;

}
