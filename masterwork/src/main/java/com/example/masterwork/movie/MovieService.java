package com.example.masterwork.movie;

import com.example.masterwork.movie.models.Movie;
import com.example.masterwork.movie.models.MovieDTO;
import com.example.masterwork.movie.models.MovieListDTO;

public interface MovieService {

  MovieDTO convertToDTO(Movie movie);

  MovieListDTO fetchTopRated(Integer limit);

  Movie fetchMovieById(int id);

  Movie fetchMovieByTitle(String title);

}
