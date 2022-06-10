package com.example.masterwork.recommendation;

import com.example.masterwork.movie.models.Movie;
import com.example.masterwork.recommendation.models.Recommendation;
import com.example.masterwork.recommendation.models.RecommendationDTO;
import com.example.masterwork.viewer.model.Viewer;

public interface RecommendationService {

  RecommendationDTO addRecommendation(Viewer viewer, RecommendationDTO recommendationDTO);

  Recommendation save(Viewer viewer, Movie movie, RecommendationDTO recommendationDTO);

}
