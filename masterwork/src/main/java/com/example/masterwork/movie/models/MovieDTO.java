package com.example.masterwork.movie.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class MovieDTO {

  private Integer id;
  private String title;
  private Double averageRating;

  public MovieDTO(Movie movie) {
    this.id = movie.getId();
    this.title = movie.getTitle();
    this.averageRating = movie.getAverageRating();
  }

}
