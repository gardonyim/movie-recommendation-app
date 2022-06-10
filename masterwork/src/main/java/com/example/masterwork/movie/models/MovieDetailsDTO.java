package com.example.masterwork.movie.models;

import com.example.masterwork.actor.models.Actor;
import com.example.masterwork.recommendation.models.RecommendationListItemDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class MovieDetailsDTO {

  private int id;
  private String title;
  private String director;
  private List<Actor> cast;
  private Integer releaseYear;
  private Integer length;
  private Double averageRating;
  private List<RecommendationListItemDTO> recommendations;

}
