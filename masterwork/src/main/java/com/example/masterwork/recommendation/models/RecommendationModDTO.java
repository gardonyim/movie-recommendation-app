package com.example.masterwork.recommendation.models;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class RecommendationModDTO {

  @NotNull(message = "Rating is required")
  private Integer rating;
  private String recommendationText;

}
