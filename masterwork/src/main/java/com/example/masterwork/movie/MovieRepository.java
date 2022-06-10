package com.example.masterwork.movie;

import com.example.masterwork.movie.models.Movie;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MovieRepository extends CrudRepository<Movie, Integer> {

  List<Movie> findAll();

}
