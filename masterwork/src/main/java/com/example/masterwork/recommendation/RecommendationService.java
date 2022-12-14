package com.example.masterwork.recommendation;

import com.example.masterwork.movie.models.Movie;
import com.example.masterwork.recommendation.models.Recommendation;
import com.example.masterwork.recommendation.models.RecommendationDTO;
import com.example.masterwork.recommendation.models.RecommendationModDTO;
import com.example.masterwork.utilities.DeleteDTO;
import com.example.masterwork.viewer.model.Viewer;

public interface RecommendationService {

  RecommendationDTO addRecommendation(Viewer viewer, RecommendationDTO recommendationDTO);

  Recommendation save(Integer id, Viewer viewer, Movie movie, int rating, String recommendationText);

  RecommendationDTO modifyRecommendation(Viewer viewer, int id, RecommendationModDTO modDTO);

  DeleteDTO removeRecommendation(Viewer viewer, int id);

}
