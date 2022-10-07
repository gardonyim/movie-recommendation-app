package com.example.masterwork.movie;

import com.example.masterwork.movie.models.MovieDetailsDTO;
import com.example.masterwork.movie.models.MovieListDTO;
import com.example.masterwork.movie.models.MovieReqDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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
  public ResponseEntity<MovieDetailsDTO> getMoviById(@PathVariable int id) {
    return ResponseEntity.ok(movieService.fetchMovieById(id));
  }

  @GetMapping("/movie")
  public ResponseEntity<MovieListDTO> getMovieByTitle(@RequestParam(required = false) String title, Integer limit) {
    return ResponseEntity.ok(movieService.fetchMovieByTitle(title, limit));
  }

  @PostMapping("/movie")
  public ResponseEntity<MovieDetailsDTO> addMovie(@Valid @RequestBody MovieReqDTO reqDTO) {
    return ResponseEntity.status(201).body(movieService.createMovie(reqDTO));
  }

}
