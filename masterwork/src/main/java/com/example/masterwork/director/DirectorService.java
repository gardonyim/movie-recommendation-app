package com.example.masterwork.director;

import com.example.masterwork.movie.models.MovieListDTO;

public interface DirectorService {

  MovieListDTO fetchMoviesByDirector(int id);

}
