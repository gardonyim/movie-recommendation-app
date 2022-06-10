package com.example.masterwork.director;

import com.example.masterwork.director.models.DirectorDTO;
import com.example.masterwork.director.models.DirectorListDTO;
import com.example.masterwork.movie.models.MovieListDTO;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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

  @GetMapping("/director/all")
  public ResponseEntity<DirectorListDTO> getDirectors() {
    return ResponseEntity.ok(directorService.fetchAllDirectors());
  }

  @PostMapping("/director")
  public ResponseEntity<DirectorDTO> addDirector(@Valid @RequestBody DirectorDTO directorDTO) {
    return ResponseEntity.status(201).body(directorService.addDirector(directorDTO));
  }

}
