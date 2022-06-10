package com.example.masterwork.recommendation;

import com.example.masterwork.recommendation.models.Recommendation;
import org.springframework.data.repository.CrudRepository;

public interface RecommendationRepository extends CrudRepository<Recommendation, Integer> {

}
