package com.example.masterwork.genre;

import com.example.masterwork.genre.models.GenreDTO;
import com.example.masterwork.genre.models.GenreListDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class GenreController {

  private GenreService genreService;

  @Autowired
  public GenreController(GenreService genreService) {
    this.genreService = genreService;
  }

  @PostMapping("/genre")
  public ResponseEntity<GenreDTO> addGenre(@Valid @RequestBody GenreDTO genreDTO) {
    return ResponseEntity.status(201).body(genreService.addGenre(genreDTO));
  }

  @GetMapping("/genre/all")
  public ResponseEntity<GenreListDTO> getGenres() {
    return ResponseEntity.ok(genreService.fetchAllGenres());
  }

}
