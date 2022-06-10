package com.example.masterwork.movie.models;

import com.example.masterwork.recommendation.models.Recommendation;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
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
