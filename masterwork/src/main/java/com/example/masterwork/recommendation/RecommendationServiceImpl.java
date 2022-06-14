package com.example.masterwork.recommendation;

import com.example.masterwork.exception.exceptions.RequestCauseConflictException;
import com.example.masterwork.movie.MovieService;
import com.example.masterwork.movie.models.Movie;
import com.example.masterwork.recommendation.models.Recommendation;
import com.example.masterwork.recommendation.models.RecommendationDTO;
import com.example.masterwork.viewer.model.Viewer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecommendationServiceImpl implements RecommendationService {

  private RecommendationRepository recommendationRepository;
  private MovieService movieService;

  @Autowired
  public RecommendationServiceImpl(RecommendationRepository recommendationRepository, MovieService movieService) {
    this.recommendationRepository = recommendationRepository;
    this.movieService = movieService;
  }

  @Override
  public RecommendationDTO addRecommendation(Viewer viewer, RecommendationDTO recommendationDTO) {
    Movie movie = movieService.getMovieById(recommendationDTO.getMovieId());
    if (viewer.getRecommendations().stream()
        .anyMatch(r -> r.getMovie().getId() == recommendationDTO.getMovieId())) {
      throw new RequestCauseConflictException("Recommendation already exists");
    }
    movieService.updateRating(movie, recommendationDTO.getRating());
    Recommendation recommendation = save(viewer, movie, recommendationDTO);
    return new RecommendationDTO(recommendation);
  }

  @Override
  public Recommendation save(Viewer viewer, Movie movie, RecommendationDTO recommendationDTO) {
    return recommendationRepository.save(Recommendation.builder()
        .rating(recommendationDTO.getRating())
        .recommendationText(recommendationDTO.getRecommendationText())
        .movie(movie)
        .viewer(viewer)
        .build());
  }

}
