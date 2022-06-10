package com.example.masterwork.recommendation;

import com.example.masterwork.recommendation.models.RecommendationDTO;
import com.example.masterwork.viewer.model.Viewer;

public interface RecommendationService {

  RecommendationDTO addRecommendation(Viewer viewer, RecommendationDTO recommendationDTO);

}
