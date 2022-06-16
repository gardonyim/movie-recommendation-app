package com.example.masterwork.recommendation;

import com.example.masterwork.recommendation.models.Recommendation;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface RecommendationRepository extends CrudRepository<Recommendation, Integer> {

  @Modifying
  @Query(value = "DELETE FROM recommendations WHERE id = :id", nativeQuery = true)
  void deleteRecommendation(@Param("id") Integer id);

}
