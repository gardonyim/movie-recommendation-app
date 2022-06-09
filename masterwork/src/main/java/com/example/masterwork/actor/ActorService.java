package com.example.masterwork.actor;

import com.example.masterwork.movie.models.MovieListDTO;

public interface ActorService {

  MovieListDTO fetchMoviesByActor(int id);

}
