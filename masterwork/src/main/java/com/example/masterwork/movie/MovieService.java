package com.example.masterwork.movie;

import com.example.masterwork.movie.models.Movie;
import com.example.masterwork.movie.models.MovieDetailsDTO;
import com.example.masterwork.movie.models.MovieListDTO;
import com.example.masterwork.movie.models.MovieReqDTO;

public interface MovieService {

  MovieListDTO fetchTopRated(Integer limit);

  MovieDetailsDTO fetchMovieById(int id);

  Movie getMovieById(int id);

  MovieListDTO fetchMovieByTitle(String title, Integer limit);

  Movie getMovieByTitle(String title);

  MovieDetailsDTO convertToDetailsDTO(Movie movie);

  void updateRating(Movie movie, Integer rating);

  MovieDetailsDTO createMovie(MovieReqDTO reqDTO);

  Movie save(Movie movie);

}
