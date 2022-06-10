package com.example.masterwork.actor;

import com.example.masterwork.actor.models.ActorDTO;
import com.example.masterwork.actor.models.ActorListDTO;
import com.example.masterwork.movie.models.MovieListDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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

  @GetMapping("/actor/all")
  public ResponseEntity<ActorListDTO> getAllActors() {
    return ResponseEntity.ok(actorService.fetchAllActors());
  }

  @PostMapping("/actor")
  public ResponseEntity<ActorDTO> addActor(@Valid @RequestBody ActorDTO actorDTO) {
    return ResponseEntity.status(201).body(actorService.addActor(actorDTO));
  }

}
