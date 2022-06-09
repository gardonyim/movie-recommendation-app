package com.example.masterwork.actor;

import com.example.masterwork.movie.models.MovieListDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ActorController {

  private ActorService actorService;

  @Autowired
  public ActorController(ActorService actorService) {
    this.actorService = actorService;
  }

  @GetMapping("/actor/{id}/movies")
  public ResponseEntity<MovieListDTO> moviesByActor(@PathVariable int id) {
    return ResponseEntity.ok(actorService.fetchMoviesByActor(id));
  }
  
}
