package com.example.masterwork.movie;

import com.example.masterwork.movie.models.Movie;
import com.example.masterwork.movie.models.MovieDTO;

public interface MovieService {

  MovieDTO convertToDTO(Movie movie);

}
