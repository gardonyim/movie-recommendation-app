package com.example.masterwork.genre;

import com.example.masterwork.genre.models.Genre;
import com.example.masterwork.genre.models.GenreDTO;
import com.example.masterwork.genre.models.GenreListDTO;

public interface GenreService {

  Genre getGenreById(int id);

  GenreDTO addGenre(GenreDTO genreDTO);

  GenreListDTO fetchAllGenres();

}
