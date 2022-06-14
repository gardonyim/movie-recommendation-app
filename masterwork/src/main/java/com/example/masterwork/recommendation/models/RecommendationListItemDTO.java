package com.example.masterwork.recommendation.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class RecommendationListItemDTO {

  private int id;
  private int rating;
  private String recommendationText;

  public RecommendationListItemDTO(Recommendation recommendation) {
    this.id = recommendation.getId();
    this.rating = recommendation.getRating();
    this.recommendationText = recommendation.getRecommendationText();
  }

}
