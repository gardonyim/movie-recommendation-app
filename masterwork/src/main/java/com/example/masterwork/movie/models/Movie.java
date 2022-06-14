package com.example.masterwork.movie.models;

import com.example.masterwork.actor.models.Actor;
import com.example.masterwork.director.models.Director;
import com.example.masterwork.genre.models.Genre;
import com.example.masterwork.recommendation.models.Recommendation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "movies")
public class Movie {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String title;
  @ManyToOne
  private Genre genre;
  private Integer releaseYear;
  private Integer length;
  private Double averageRating;
  @ManyToOne
  private Director director;
  @ManyToMany
  @JoinTable(
      name = "actor_movie",
      joinColumns = @JoinColumn(name = "movie_id"),
      inverseJoinColumns = @JoinColumn(name = "actor_id")
  )
  private List<Actor> cast;
  @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
  private List<Recommendation> recommendations;

}
