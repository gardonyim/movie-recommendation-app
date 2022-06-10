package com.example.masterwork.director.models;

import lombok.Getter;
import lombok.Setter;

@Getter
public class DirectorDTO {

  private int id;
  private String name;

  public DirectorDTO(Director director) {
    this.id = director.getId();
    this.name = director.getName();
  }
}
