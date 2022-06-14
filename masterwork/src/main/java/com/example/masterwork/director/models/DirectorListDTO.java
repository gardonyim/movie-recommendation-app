package com.example.masterwork.director.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;

@Getter
@EqualsAndHashCode
public class DirectorListDTO {

  private List<DirectorDTO> directors;

  public DirectorListDTO(List<DirectorDTO> directors) {
    this.directors = directors;
  }
}
