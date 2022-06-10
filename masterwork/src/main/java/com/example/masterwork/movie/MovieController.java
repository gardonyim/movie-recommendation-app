package com.example.masterwork.movie;

import com.example.masterwork.movie.models.MovieListDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MovieController {

  private MovieService movieService;

  @Autowired
  public MovieController(MovieService movieService) {
    this.movieService = movieService;
  }

  @GetMapping("/movie/topRated")
  public ResponseEntity<MovieListDTO> topRatedMovies(@RequestParam(required = false) Integer limit) {
    return ResponseEntity.ok(movieService.fetchTopRated(limit));
  }
}
