package com.example.masterwork.movie.models;

import com.example.masterwork.recommendation.models.Recommendation;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class MovieDTO {

  private Integer id;
  private String title;
  private Double avarageRating;

}
