package com.example.masterwork.movie;

import com.example.masterwork.movie.models.Movie;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface MovieRepository extends CrudRepository<Movie, Integer> {

  @Override
  List<Movie> findAll();

  Optional<Movie> findMovieByTitle(String title);

  List<Movie> findMovieByTitleContainingIgnoreCase(String title);

}
