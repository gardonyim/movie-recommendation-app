package com.example.masterwork.recommendation;

import com.example.masterwork.exception.exceptions.RecommendationOwnedByOtherViewerException;
import com.example.masterwork.exception.exceptions.RequestCauseConflictException;
import com.example.masterwork.movie.MovieService;
import com.example.masterwork.movie.models.Movie;
import com.example.masterwork.recommendation.models.Recommendation;
import com.example.masterwork.recommendation.models.RecommendationDTO;
import com.example.masterwork.recommendation.models.RecommendationModDTO;
import com.example.masterwork.utilities.DeleteDTO;
import com.example.masterwork.viewer.model.Viewer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

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
    if (viewer.getRecommendations().stream()
        .anyMatch(r -> r.getMovie().getId() == recommendationDTO.getMovieId())) {
      throw new RequestCauseConflictException("Recommendation already exists");
    }
    Movie movie = movieService.getMovieById(recommendationDTO.getMovieId());
    movieService.updateRating(movie, recommendationDTO.getRating());
    return new RecommendationDTO(save(null, viewer, movie, recommendationDTO.getRating(),
        recommendationDTO.getRecommendationText()));
  }

  @Override
  public Recommendation save(Integer id, Viewer viewer, Movie movie, int rating, String recommendationText) {
    return recommendationRepository.save(Recommendation.builder()
        .id(id)
        .rating(rating)
        .recommendationText(recommendationText)
        .movie(movie)
        .viewer(viewer)
        .build());
  }

  @Override
  public RecommendationDTO modifyRecommendation(Viewer viewer, int id, RecommendationModDTO modDTO) {
    Recommendation recommendation = getRecommendationOwnedByViewer(viewer, id);
    Movie movie = movieService.getMovieById(recommendation.getId());
    movie.setRecommendations(movie.getRecommendations().stream()
        .filter(r -> r.getId() != id)
        .collect(Collectors.toList()));
    movieService.updateRating(movie, modDTO.getRating());
    return new RecommendationDTO(save(id, viewer, movie, modDTO.getRating(), modDTO.getRecommendationText()));
  }

  @Override
  public DeleteDTO removeRecommendation(Viewer viewer, int id) {
    Recommendation recommendation = getRecommendationOwnedByViewer(viewer, id);
    Movie movie = recommendation.getMovie();
    movie.setRecommendations(movie.getRecommendations().stream()
        .filter(r -> !r.equals(recommendation))
        .collect(Collectors.toList()));
    viewer.setRecommendations(viewer.getRecommendations().stream()
        .filter(r -> r.getId() != id)
        .collect(Collectors.toList()));
    movie.setAverageRating(movie.getRecommendations().stream()
        .mapToInt(r -> r.getRating())
        .average()
        .orElseGet(null));
    movieService.save(movie);
    recommendationRepository.deleteRecommendation(id);
    return new DeleteDTO(id);
  }

  private Recommendation getRecommendationOwnedByViewer(Viewer viewer, int id) {
    return viewer.getRecommendations().stream()
        .filter(r -> r.getId() == id)
        .findFirst()
        .orElseThrow(RecommendationOwnedByOtherViewerException::new);
  }

}
