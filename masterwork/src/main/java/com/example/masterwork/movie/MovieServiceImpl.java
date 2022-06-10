package com.example.masterwork.movie;

import com.example.masterwork.movie.models.Movie;
import com.example.masterwork.movie.models.MovieDTO;
import com.example.masterwork.movie.models.MovieListDTO;
import com.example.masterwork.recommendation.models.Recommendation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class MovieServiceImpl implements MovieService {

  private MovieRepository movieRepository;

  @Value("defaultLimitForLists")
  private Integer defaultLimit;

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
    limit = (limit == null || limit <= 0) ? defaultLimit : limit;
    return new MovieListDTO(movieRepository.findAll().stream()
        .map(m -> convertToDTO(m))
        .sorted((m1, m2) -> m2.getAvarageRating().compareTo(m1.getAvarageRating()))
        .limit(limit)
        .collect(Collectors.toList()));
  }

}
