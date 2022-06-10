package com.example.masterwork.director;

import com.example.masterwork.movie.models.MovieListDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DirectorController {

  private DirectorService directorService;

  @Autowired
  public DirectorController(DirectorService directorService) {
    this.directorService = directorService;
  }

  @GetMapping("/director/{id}/movies")
  public ResponseEntity<MovieListDTO> moviesByDirector(@PathVariable int id) {
    return ResponseEntity.ok(directorService.fetchMoviesByDirector(id));
  }

}