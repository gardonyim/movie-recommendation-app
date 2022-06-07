package com.example.masterwork.viewer.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class RegistrationReqDTO {

  @NotNull(message = "Username is required")
  private String username;
  @NotNull(message = "Password is required")
  @Size(min = 8, message = "Password should be al least 8 characters long")
  private String password;
  @Email(message = "Valid e-mail address is required")
  private String email;

}
