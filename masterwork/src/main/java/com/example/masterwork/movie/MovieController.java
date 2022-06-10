package com.example.masterwork.movie;

import com.example.masterwork.movie.models.Movie;
import com.example.masterwork.movie.models.MovieListDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
  public ResponseEntity<MovieListDTO> getTopRatedMovies(@RequestParam(required = false) Integer limit) {
    return ResponseEntity.ok(movieService.fetchTopRated(limit));
  }

  @GetMapping("/movie/{id}")
  public ResponseEntity<Movie> getMoviById(@PathVariable int id) {
    return ResponseEntity.ok(movieService.fetchMovieById(id));
  }

}
