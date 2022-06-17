package com.example.masterwork.genre;

import com.example.masterwork.exception.exceptions.GenreNotFoundException;
import com.example.masterwork.exception.exceptions.RequestCauseConflictException;
import com.example.masterwork.genre.models.Genre;
import com.example.masterwork.genre.models.GenreDTO;
import com.example.masterwork.genre.models.GenreListDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class GenreServiceImpl implements GenreService {

  private GenreRepository genreRepository;

  @Autowired
  public GenreServiceImpl(GenreRepository genreRepository) {
    this.genreRepository = genreRepository;
  }

  @Override
  public Genre getGenreById(int id) {
    return genreRepository.findById(id).orElseThrow(GenreNotFoundException::new);
  }

  @Override
  public GenreDTO addGenre(GenreDTO genreDTO) {
    if (genreRepository.findByType(genreDTO.getType()).isPresent()) {
      throw new RequestCauseConflictException("Genre is already present in the database");
    }
    Genre genre = new Genre();
    genre.setType(genreDTO.getType());
    return new  GenreDTO(genreRepository.save(genre));
  }

  @Override
  public GenreListDTO fetchAllGenres() {
    return new GenreListDTO(genreRepository.findAll().stream()
        .map(GenreDTO::new)
        .collect(Collectors.toList()));
  }

}
