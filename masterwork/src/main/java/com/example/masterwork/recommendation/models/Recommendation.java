package com.example.masterwork.recommendation.models;

import com.example.masterwork.movie.models.Movie;
import com.example.masterwork.viewer.model.Viewer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "recommendations")
public class Recommendation {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  @Column(columnDefinition = "TEXT")
  private String recommendationText;
  private Integer rating;
  @ManyToOne
  private Movie movie;
  @ManyToOne
  private Viewer viewer;

}
