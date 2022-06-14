package com.example.masterwork.director;

import com.example.masterwork.director.models.Director;
import com.example.masterwork.director.models.DirectorDTO;
import com.example.masterwork.director.models.DirectorListDTO;
import com.example.masterwork.exception.exceptions.DirectorNotFoundException;
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

import static com.example.masterwork.TestUtils.defaultDirector;
import static com.example.masterwork.TestUtils.defaultMovie;
import static com.example.masterwork.TestUtils.testDirectorBuilder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DirectorServiceTest {

  @Mock
  DirectorRepository directorRepository;

  @InjectMocks
  DirectorServiceImpl directorService;

  @Test
  public void when_fetchMoviesByDirectorWithValidId_should_returnProperDtoList() {
    Movie movie = defaultMovie();
    Director director = testDirectorBuilder().movies(Collections.singletonList(movie)).build();
    when(directorRepository.findById(anyInt())).thenReturn(Optional.of(director));
    MovieListDTO expected = new MovieListDTO(Collections.singletonList(new MovieDTO(movie)));

    MovieListDTO actual = directorService.fetchMoviesByDirector(1);

    assertEquals(expected, actual);
  }

  @Test
  public void when_fetchMoviesByDirectorWithInvalidId_should_throwException() {
    when(directorRepository.findById(anyInt())).thenReturn(Optional.empty());

    assertThrows(DirectorNotFoundException.class, () -> {
      directorService.getDirectorById(0);
    });
  }

  @Test
  public void when_fetchAllDirectors_should_returnProperListOfDTOs() {
    Director director = defaultDirector();
    when(directorRepository.findAll()).thenReturn(Collections.singletonList(director));
    DirectorListDTO expected = new DirectorListDTO(Collections.singletonList(new DirectorDTO(director)));

    DirectorListDTO actual = directorService.fetchAllDirectors();

    assertEquals(expected, actual);
  }

  @Test
  public void when_addNewDirector_should_returnProperDTO() {
    when(directorRepository.findFirstByName(anyString())).thenReturn(Optional.empty());
    Director director = defaultDirector();
    when(directorRepository.save(any(Director.class))).thenReturn(director);
    DirectorDTO expected = new DirectorDTO(director);

    DirectorDTO actual = directorService.addDirector(new DirectorDTO(director));

    assertEquals(expected, actual);
  }

  @Test
  public void when_addExistingDirector_should_throwException() {
    when(directorRepository.findFirstByName(anyString())).thenReturn(Optional.of(new Director()));

    Throwable exception = assertThrows(RequestCauseConflictException.class, () -> {
      directorService.addDirector(new DirectorDTO(defaultDirector()));
    });
    assertEquals("Director is already in the database", exception.getMessage());
  }

  @Test
  public void when_getDirectorByBValidId_should_returnDirector() {
    Director expected = defaultDirector();
    when(directorRepository.findById(anyInt())).thenReturn(Optional.of(expected));

    Director actual = directorService.getDirectorById(1);

    assertEquals(expected, actual);
  }

  @Test
  public void when_getDirectorByInvalidId_should_throwException() {
    when(directorRepository.findById(anyInt())).thenReturn(Optional.empty());

    assertThrows(DirectorNotFoundException.class, () -> {
      directorService.getDirectorById(0);
    });
  }

}
