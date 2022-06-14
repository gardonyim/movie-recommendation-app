package com.example.masterwork.actor;

import com.example.masterwork.actor.models.Actor;
import com.example.masterwork.actor.models.ActorDTO;
import com.example.masterwork.actor.models.ActorListDTO;
import com.example.masterwork.exception.exceptions.ActorNotFoundException;
import com.example.masterwork.exception.exceptions.RequestCauseConflictException;
import com.example.masterwork.movie.models.Movie;
import com.example.masterwork.movie.models.MovieDTO;
import com.example.masterwork.movie.models.MovieListDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static com.example.masterwork.TestUtils.defaultActor;
import static com.example.masterwork.TestUtils.defaultMovie;
import static com.example.masterwork.TestUtils.testActorBuilder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ActorServiceTest {

  @Mock
  ActorRepository actorRepository;

  @InjectMocks
  ActorServiceImpl actorService;

  @Test
  public void when_fetchMoviesByActorWithValidId_should_returnProperListOfMovies() {
    Movie movie = defaultMovie();
    Actor actor = testActorBuilder().movies(Collections.singletonList(movie)).build();
    when(actorRepository.findById(anyInt())).thenReturn(Optional.of(actor));
    MovieListDTO expected = new MovieListDTO(Collections.singletonList(new MovieDTO(movie)));

    MovieListDTO actual = actorService.fetchMoviesByActor(1);

    assertEquals(expected, actual);
  }

  @Test
  public void when_fetchMoviesByActorWithInvalidId_should_throwException() {
    when(actorRepository.findById(anyInt())).thenReturn(Optional.empty());

    Throwable exception = assertThrows(ActorNotFoundException.class, () -> {
      actorService.fetchMoviesByActor(0);
    });

    assertEquals("Actor/actress is not in the database", exception.getMessage());
  }

  @Test
  public void when_fetchAllActors_should_returnProperListOfActorDTOs() {
    Actor actor = defaultActor();
    when(actorRepository.findAll()).thenReturn(Collections.singletonList(actor));
    ActorListDTO expected = new ActorListDTO(Collections.singletonList(new ActorDTO(actor)));

    ActorListDTO actual = actorService.fetchAllActors();

    assertEquals(expected, actual);
  }

  @Test
  public void when_addActorValidDTO_should_returnProperActor() {
    Actor actor = defaultActor();
    when(actorRepository.findActorByName(anyString())).thenReturn(Optional.empty());
    when(actorRepository.save(any(Actor.class))).thenReturn(actor);
    ActorDTO expected = new ActorDTO(actor);

    ActorDTO actual = actorService.addActor(new ActorDTO(actor));

    assertEquals(expected, actual);
  }

  @Test
  public void when_addActorWithExistingName_should_throwException() {
    when(actorRepository.findActorByName(anyString())).thenReturn(Optional.of(new Actor()));

    Throwable exception = assertThrows(RequestCauseConflictException.class, () -> {
      actorService.addActor(new ActorDTO(defaultActor()));
    });

    assertEquals("Actor/actress is already in the database", exception.getMessage());
  }

  @Test
  public void when_getActorByValidId_should_returnProperActor() {
    Actor actor = defaultActor();
    when(actorRepository.findById(anyInt())).thenReturn(Optional.of(actor));

    Actor actual = actorService.getActorById(1);

    assertEquals(actor, actual);
  }

  @Test
  public void when_getActorByInvalidId_should_throwException() {
    when(actorRepository.findById(anyInt())).thenReturn(Optional.empty());

    assertThrows(ActorNotFoundException.class, () -> {
      actorService.getActorById(0);
    });
  }

}
