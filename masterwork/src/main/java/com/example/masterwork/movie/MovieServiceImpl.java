package com.example.masterwork.movie;

import com.example.masterwork.actor.ActorService;
import com.example.masterwork.actor.models.ActorDTO;
import com.example.masterwork.director.DirectorService;
import com.example.masterwork.exception.exceptions.MovieNotFoundException;
import com.example.masterwork.exception.exceptions.RequestCauseConflictException;
import com.example.masterwork.genre.GenreService;
import com.example.masterwork.movie.models.Movie;
import com.example.masterwork.movie.models.MovieDTO;
import com.example.masterwork.movie.models.MovieDetailsDTO;
import com.example.masterwork.movie.models.MovieListDTO;
import com.example.masterwork.movie.models.MovieReqDTO;
import com.example.masterwork.recommendation.models.RecommendationListItemDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class MovieServiceImpl implements MovieService {

  private MovieRepository movieRepository;
  private ActorService actorService;
  private DirectorService directorService;
  private GenreService genreService;

  private String defaultLimit;

  @Autowired
  public MovieServiceImpl(MovieRepository movieRepository, ActorService actorService, DirectorService directorService,
                          GenreService genreService, @Value("${defaultLimitForLists:}") String defaultLimit) {
    this.movieRepository = movieRepository;
    this.actorService = actorService;
    this.directorService = directorService;
    this.genreService = genreService;
    this.defaultLimit = defaultLimit;
  }

  @Override
  public MovieListDTO fetchTopRated(Integer limit) {
    limit = limit == null || limit <= 0 ? Integer.parseInt(defaultLimit) : limit;
    return new MovieListDTO(movieRepository.findAll().stream()
        .map(MovieDTO::new)
        .sorted((m1, m2) -> m2.getAverageRating() == null ? -1 : m2.getAverageRating().compareTo(m1.getAverageRating()))
        .limit(limit)
        .collect(Collectors.toList()));
  }

  @Override
  public MovieDetailsDTO fetchMovieById(int id) {
    return convertToDetailsDTO(getMovieById(id));
  }

  @Override
  public Movie getMovieById(int id) {
    return movieRepository.findById(id).orElseThrow(MovieNotFoundException::new);
  }

  @Override
  public MovieDetailsDTO fetchMovieByTitle(String title) {
    return convertToDetailsDTO(getMovieByTitle(title));
  }

  @Override
  public Movie getMovieByTitle(String title) {
    return movieRepository.findMovieByTitle(title).orElseThrow(MovieNotFoundException::new);
  }

  @Override
  public MovieDetailsDTO convertToDetailsDTO(Movie movie) {
    return MovieDetailsDTO.builder()
        .id(movie.getId())
        .title(movie.getTitle())
        .director(movie.getDirector().getName())
        .cast(movie.getCast().stream()
            .map(ActorDTO::new)
            .collect(Collectors.toList()))
        .genre(movie.getGenre().getType())
        .releaseYear(movie.getReleaseYear())
        .length(movie.getLength())
        .averageRating(movie.getAverageRating())
        .recommendations(movie.getRecommendations().stream()
            .map(RecommendationListItemDTO::new)
            .collect(Collectors.toList()))
        .build();
  }

  @Override
  public void updateRating(Movie movie, int rating) {
    IntStream ratings = movie.getRecommendations().stream()
            .mapToInt(r -> r.getRating());
    movie.setAverageRating(IntStream.concat(ratings, IntStream.of(rating))
        .average()
        .orElseGet(null));
  }

  @Override
  public MovieDetailsDTO createMovie(MovieReqDTO reqDTO) {
    validateTitle(reqDTO.getTitle());
    return convertToDetailsDTO(movieRepository.save(convertToMovie(reqDTO)));
  }

  private void validateTitle(String title) {
    if (movieRepository.findMovieByTitle(title).isPresent()) {
      throw new RequestCauseConflictException("Movie is already in the database");
    }
  }

  private Movie convertToMovie(MovieReqDTO reqDTO) {
    return Movie.builder()
        .title(reqDTO.getTitle())
        .director(directorService.getDirectorById(reqDTO.getDirectorId()))
        .length(reqDTO.getLength())
        .releaseYear(reqDTO.getReleaseYear())
        .genre(genreService.getGenreById(reqDTO.getGenreId()))
        .cast(reqDTO.getActorIdList().stream()
            .map(id -> actorService.getActorById(id))
            .collect(Collectors.toList()))
        .recommendations(new ArrayList<>())
        .build();
  }

}
