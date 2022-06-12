package com.example.masterwork.actor;

import com.example.masterwork.actor.models.Actor;
import com.example.masterwork.actor.models.ActorDTO;
import com.example.masterwork.actor.models.ActorListDTO;
import com.example.masterwork.exception.exceptions.ActorNotFoundException;
import com.example.masterwork.exception.exceptions.RequestCauseConflictException;
import com.example.masterwork.movie.models.MovieDTO;
import com.example.masterwork.movie.models.MovieListDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class ActorServiceImpl implements ActorService {

  private ActorRepository actorRepository;

  @Autowired
  public ActorServiceImpl(ActorRepository actorRepository) {
    this.actorRepository = actorRepository;
  }

  @Override
  public MovieListDTO fetchMoviesByActor(int id) {
    Actor actor = actorRepository.findById(id).orElseThrow(ActorNotFoundException::new);
    return new MovieListDTO(actor.getMovies().stream()
        .map(MovieDTO::new)
        .collect(Collectors.toList()));
  }

  @Override
  public ActorListDTO fetchAllActors() {
    return new ActorListDTO(actorRepository.findAll().stream()
        .map(ActorDTO::new)
        .collect(Collectors.toList()));
  }

  @Override
  public ActorDTO addActor(ActorDTO actorDTO) {
    if (actorRepository.findActorByName(actorDTO.getName()).isPresent()) {
      throw new RequestCauseConflictException("Actor/actress is already in the database");
    }
    return new ActorDTO(actorRepository.save(Actor.builder().name(actorDTO.getName()).build()));
  }

  @Override
  public Actor getActorById(int id) {
    return actorRepository.findById(id).orElseThrow(ActorNotFoundException::new);
  }

}
