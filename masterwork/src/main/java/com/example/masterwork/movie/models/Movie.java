package com.example.masterwork.movie.models;

import com.example.masterwork.actor.models.Actor;
import com.example.masterwork.director.models.Director;
import com.example.masterwork.recommendation.models.Recommendation;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

@Getter
@Setter
@Entity(name = "movies")
public class Movie {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String title;
  private Integer releaseYear;
  private Integer length;
  private Double averageRating;
  @ManyToOne
  private Director director;
  @ManyToMany(mappedBy = "movies")
  private List<Actor> cast;
  @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
  private List<Recommendation> recommendations;

}
