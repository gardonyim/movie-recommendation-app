package com.example.masterwork.director.models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
public class DirectorListDTO {

  private List<DirectorDTO> directors;

  public DirectorListDTO(List<DirectorDTO> directors) {
    this.directors = directors;
  }
}
