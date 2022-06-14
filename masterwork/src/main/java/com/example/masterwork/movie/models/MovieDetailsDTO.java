package com.example.masterwork.movie.models;

import com.example.masterwork.actor.models.ActorDTO;
import com.example.masterwork.recommendation.models.RecommendationListItemDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class MovieDetailsDTO {

  private int id;
  private String title;
  private String director;
  private List<ActorDTO> cast;
  private String genre;
  private Integer releaseYear;
  private Integer length;
  private Double averageRating;
  private List<RecommendationListItemDTO> recommendations;

}
