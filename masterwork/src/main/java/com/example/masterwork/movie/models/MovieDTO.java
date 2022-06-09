package com.example.masterwork.movie.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovieDTO {

  private Integer id;
  private String title;

  public MovieDTO(Movie movie) {
    this.id = movie.getId();
    this.title = movie.getTitle();
  }
}
