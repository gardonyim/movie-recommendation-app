package com.example.masterwork.movie;

import com.example.masterwork.movie.models.Movie;
import com.example.masterwork.movie.models.MovieDTO;
import com.example.masterwork.recommendation.models.Recommendation;
import org.springframework.stereotype.Service;

@Service
public class MovieServiceImpl implements MovieService {

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
}
