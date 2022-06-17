package com.example.masterwork.genre.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class GenreDTO {

  private Integer id;
  @NotNull(message = "Genre type is required")
  private String type;

  public GenreDTO(Genre genre) {
    this.id = genre.getId();
    this.type = genre.getType();
  }

}
