package com.example.masterwork.actor;

import com.example.masterwork.actor.models.Actor;
import com.example.masterwork.exception.exceptions.ActorNotFoundException;
import com.example.masterwork.movie.MovieService;
import com.example.masterwork.movie.models.MovieListDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class ActorServiceImpl implements ActorService {

  private ActorRepository actorRepository;
  private MovieService movieService;

  @Autowired
  public ActorServiceImpl(ActorRepository actorRepository, MovieService movieService) {
    this.actorRepository = actorRepository;
    this.movieService = movieService;
  }

  @Override
  public MovieListDTO fetchMoviesByActor(int id) {
    Actor actor = actorRepository.findById(id).orElseThrow(ActorNotFoundException::new);
    return new MovieListDTO(actor.getMovies().stream()
        .map(m -> movieService.convertToDTO(m))
        .collect(Collectors.toList()));
  }
}
