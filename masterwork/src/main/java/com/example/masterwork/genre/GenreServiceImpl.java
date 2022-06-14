package com.example.masterwork.genre;

import com.example.masterwork.exception.exceptions.GenreNotFoundException;
import com.example.masterwork.genre.models.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
