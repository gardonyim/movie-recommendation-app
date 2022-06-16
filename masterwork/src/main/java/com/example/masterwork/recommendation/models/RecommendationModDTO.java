package com.example.masterwork.recommendation.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecommendationModDTO {

  @NotNull(message = "Rating is required")
  private Integer rating;
  private String recommendationText;

}
