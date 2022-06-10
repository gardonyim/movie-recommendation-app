package com.example.masterwork.recommendation.models;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class RecommendationDTO {

  private Integer id;
  @NotNull(message = "Rating is required")
  private int rating;
  private String recommendationText;
  @NotNull(message = "Movie ID is required")
  private int movieId;

  public RecommendationDTO(Recommendation recommendation) {
    this.id = recommendation.getId();
    this.rating = recommendation.getRating();
    this.recommendationText = recommendation.getRecommendationText();
    this.movieId = recommendation.getMovie().getId();
  }

}