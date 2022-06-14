package com.example.masterwork.genre.models;

import com.example.masterwork.movie.models.Movie;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "genres")
public class Genre {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String type;
  @OneToMany(mappedBy = "genre", cascade = CascadeType.ALL)
  private List<Movie> movies;

}
