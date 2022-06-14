package com.example.masterwork.viewer.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class RegistrationResDTO {

  private int id;
  private String username;

  public RegistrationResDTO(Viewer viewer) {
    this.id = viewer.getId();
    this.username = viewer.getUsername();
  }

}
