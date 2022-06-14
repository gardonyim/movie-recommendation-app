package com.example.masterwork.movie;

import com.example.masterwork.actor.ActorServiceImpl;
import com.example.masterwork.actor.models.Actor;
import com.example.masterwork.actor.models.ActorDTO;
import com.example.masterwork.director.DirectorServiceImpl;
import com.example.masterwork.exception.exceptions.MovieNotFoundException;
import com.example.masterwork.exception.exceptions.RequestCauseConflictException;
import com.example.masterwork.genre.GenreServiceImpl;
import com.example.masterwork.movie.models.Movie;
import com.example.masterwork.movie.models.MovieDetailsDTO;
import com.example.masterwork.movie.models.MovieListDTO;
import com.example.masterwork.recommendation.models.Recommendation;
import com.example.masterwork.recommendation.models.RecommendationListItemDTO;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.example.masterwork.TestUtils.defaultActor;
import static com.example.masterwork.TestUtils.defaultDetailsDTO;
import static com.example.masterwork.TestUtils.defaultDirector;
import static com.example.masterwork.TestUtils.defaultGenre;
import static com.example.masterwork.TestUtils.defaultMovie;
import static com.example.masterwork.TestUtils.defaultRecommendation;
import static com.example.masterwork.TestUtils.defaultReqDTO;
import static com.example.masterwork.TestUtils.testActorBuilder;
import static com.example.masterwork.TestUtils.testMovieBuilder;
import static com.example.masterwork.TestUtils.testRecommendationBuilder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestPropertySource(properties = {
    "defaultLimit=10",
})
public class MovieServiceTest {

  @Mock
  MovieRepository movieRepository;
  @Mock
  ActorServiceImpl actorService;
  @Mock
  DirectorServiceImpl directorService;
  @Mock
  GenreServiceImpl genreService;

  @InjectMocks
  MovieServiceImpl movieService;

  @Disabled
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

  @Disabled
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

  @Disabled
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

  @Test
  public void when_getMovieByValidId_should_returnMovie() {
    Movie expected = defaultMovie();
    when(movieRepository.findById(anyInt())).thenReturn(Optional.of(expected));

    Movie actual = movieService.getMovieById(1);

    assertEquals(expected, actual);
  }

  @Test
  public void when_getMovieByInvalidId_should_throwException() {
    when(movieRepository.findById(anyInt())).thenReturn(Optional.empty());

    assertThrows(MovieNotFoundException.class, () -> {
      movieService.getMovieById(0);
    });
  }

  @Test
  public void when_getMovieByValidTitle_should_returnMovie() {
    Movie expected = defaultMovie();
    when(movieRepository.findMovieByTitle(anyString())).thenReturn(Optional.of(expected));

    Movie actual = movieService.getMovieByTitle("test title");

    assertEquals(expected, actual);
  }

  @Test
  public void when_getMovieByInvalidTitle_shouldThrowException() {
    when(movieRepository.findMovieByTitle(anyString())).thenReturn(Optional.empty());

    assertThrows(MovieNotFoundException.class, () -> {
      movieService.getMovieByTitle("nonexisting movie");
    });
  }

  @Test
  public void when_convertToDetailsDTO_shouldReturnValidDTO() {
    Actor actor = defaultActor();
    Recommendation recommendation = defaultRecommendation();
    Movie movie = testMovieBuilder().cast(Collections.singletonList(actor)).recommendations(Collections.singletonList(recommendation)).build();
    MovieDetailsDTO expected = new MovieDetailsDTO(
        movie.getId(),
        movie.getTitle(),
        movie.getDirector().getName(),
        Collections.singletonList(new ActorDTO(actor)),
        movie.getGenre().getType(),
        movie.getReleaseYear(),
        movie.getLength(),
        movie.getAverageRating(),
        Collections.singletonList(new RecommendationListItemDTO(recommendation))
    );

    MovieDetailsDTO actual = movieService.convertToDetailsDTO(movie);

    assertEquals(expected, actual);
  }

  @Test
  public void when_updateRating_should_setAverageRatingInMovie() {
    Movie movie = testMovieBuilder().averageRating(5.0)
        .recommendations(Collections.singletonList(testRecommendationBuilder().rating(5).build())).build();

    movieService.updateRating(movie, 10);

    assertEquals(7.5, movie.getAverageRating());
  }

  @Test
  public void when_createNewMovie_should_returnProperDTO() {
    when(movieRepository.findMovieByTitle(anyString())).thenReturn(Optional.empty());
    when(directorService.getDirectorById(anyInt())).thenReturn(defaultDirector());
    when(genreService.getGenreById(anyInt())).thenReturn(defaultGenre());
    when(actorService.getActorById(anyInt())).thenReturn(testActorBuilder().id(888).name("Test Actor").build());
    when(movieRepository.save(any(Movie.class))).thenReturn(testMovieBuilder()
        .id(666)
        .director(defaultDirector())
        .cast(Collections.singletonList(testActorBuilder().id(888).build()))
        .recommendations(Collections.singletonList(testRecommendationBuilder().id(555).rating(5).build()))
        .genre(defaultGenre())
        .averageRating(5.0)
        .build());
    MovieDetailsDTO expected = defaultDetailsDTO();

    MovieDetailsDTO actual = movieService.createMovie(defaultReqDTO());

    assertEquals(expected, actual);
  }

  @Test
  public void when_createExistingMovie_should_throwException() {
    when(movieRepository.findMovieByTitle(anyString())).thenReturn(Optional.of(new Movie()));

    Throwable exception = assertThrows(RequestCauseConflictException.class, () -> {
      movieService.createMovie(defaultReqDTO());
    });

    assertEquals("Movie is already in the database", exception.getMessage());
  }

}
