package com.example.masterwork.movie;

import com.example.masterwork.movie.models.Movie;
import com.example.masterwork.movie.models.MovieDTO;
import com.example.masterwork.movie.models.MovieDetailsDTO;
import com.example.masterwork.movie.models.MovieListDTO;

public interface MovieService {

  MovieDTO convertToDTO(Movie movie);

  MovieListDTO fetchTopRated(Integer limit);

  MovieDetailsDTO fetchMovieById(int id);

  Movie getMovieById(int id);

  MovieDetailsDTO fetchMovieByTitle(String title);

  MovieDetailsDTO convertToDetailsDTO(Movie movie);

}
