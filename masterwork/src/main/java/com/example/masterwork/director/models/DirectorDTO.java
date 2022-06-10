package com.example.masterwork.director.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class DirectorDTO {

  private int id;
  @NotNull(message = "Name is required")
  private String name;

  public DirectorDTO(Director director) {
    this.id = director.getId();
    this.name = director.getName();
  }
}
