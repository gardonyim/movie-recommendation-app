package com.example.masterwork.movie;

import com.example.masterwork.exception.exceptions.MovieNotFoundException;
import com.example.masterwork.movie.models.Movie;
import com.example.masterwork.movie.models.MovieDTO;
import com.example.masterwork.movie.models.MovieDetailsDTO;
import com.example.masterwork.movie.models.MovieListDTO;
import com.example.masterwork.recommendation.models.Recommendation;
import com.example.masterwork.recommendation.models.RecommendationDTO;
import com.example.masterwork.recommendation.models.RecommendationListItemDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class MovieServiceImpl implements MovieService {

  private MovieRepository movieRepository;

  @Value("defaultLimitForLists")
  private String defaultLimit;

  @Autowired
  public MovieServiceImpl(MovieRepository movieRepository) {
    this.movieRepository = movieRepository;
  }

  @Override
  public MovieDTO convertToDTO(Movie movie) {
    return MovieDTO.builder()
        .id(movie.getId())
        .title(movie.getTitle())
        .avarageRating(movie.getRecommendations().stream()
            .mapToInt(Recommendation::getRating)
            .average()
            .orElse(new Double(null)))
        .build();
  }

  @Override
  public MovieListDTO fetchTopRated(Integer limit) {
    limit = limit <= 0 ? Integer.parseInt(defaultLimit) : limit;
    return new MovieListDTO(movieRepository.findAll().stream()
        .map(this::convertToDTO)
        .sorted((m1, m2) -> m2.getAvarageRating().compareTo(m1.getAvarageRating()))
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
    return convertToDetailsDTO(movieRepository.findMovieByTitle(title).orElseThrow(MovieNotFoundException::new));
  }

  @Override
  public MovieDetailsDTO convertToDetailsDTO(Movie movie) {
    return MovieDetailsDTO.builder()
        .id(movie.getId())
        .title(movie.getTitle())
        .director(movie.getDirector().getName())
        .cast(movie.getCast())
        .releaseYear(movie.getReleaseYear())
        .length(movie.getLength())
        .recommendations(movie.getRecommendations().stream()
            .map(r -> new RecommendationListItemDTO(r))
            .collect(Collectors.toList()))
        .build();
  }

}
