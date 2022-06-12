package com.example.masterwork.actor;

import com.example.masterwork.actor.models.Actor;
import com.example.masterwork.actor.models.ActorDTO;
import com.example.masterwork.actor.models.ActorListDTO;
import com.example.masterwork.movie.models.MovieListDTO;

public interface ActorService {

  MovieListDTO fetchMoviesByActor(int id);

  ActorListDTO fetchAllActors();

  ActorDTO addActor(ActorDTO actorDTO);

  Actor getActorById(int id);

}
