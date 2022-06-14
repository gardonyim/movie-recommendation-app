package com.example.masterwork.movie;

import com.example.masterwork.movie.models.Movie;
import com.example.masterwork.movie.models.MovieListDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.List;

import static com.example.masterwork.TestUtils.testMovieBuilder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestPropertySource(properties = {
    "defaultLimit=10",
})
public class MovieServiceTest {

  @Mock
  MovieRepository movieRepository;

  @InjectMocks
  MovieServiceImpl movieService;

  @Test
  public void when_fetchTopRatedWithNoLimit_should_returnProperListOfDefaultLimitMovies() {
    int expectedLimit = 10;
    List<Movie> movies = new ArrayList<>();
    for (double i = 1; i < 19; i++) {
      movies.add(testMovieBuilder().averageRating(i / 2 + 1).build());
    }
    when(movieRepository.findAll()).thenReturn(movies);

    MovieListDTO actual = movieService.fetchTopRated(null);

    assertEquals(expectedLimit, actual.getMovies().size());
  }

  @Test
  public void when_fetchTopRatedWithZeroLimit_should_returnProperListOfDefaultLimitMovies() {
    int expectedLimit = 10;
    List<Movie> movies = new ArrayList<>();
    for (double i = 1; i < 19; i++) {
      movies.add(testMovieBuilder().averageRating(i / 2 + 1).build());
    }
    when(movieRepository.findAll()).thenReturn(movies);

    MovieListDTO actual = movieService.fetchTopRated(0);

    assertEquals(expectedLimit, actual.getMovies().size());
  }

  @Test
  public void when_fetchTopRatedWithLimit_should_returnProperListOfLimitNumberOfMovies() {
    int expectedLimit = 5;
    List<Movie> movies = new ArrayList<>();
    for (double i = 1; i < 19; i++) {
      movies.add(testMovieBuilder().averageRating(i / 2 + 1).build());
    }
    when(movieRepository.findAll()).thenReturn(movies);

    MovieListDTO actual = movieService.fetchTopRated(expectedLimit);

    assertEquals(expectedLimit, actual.getMovies().size());
  }

}
