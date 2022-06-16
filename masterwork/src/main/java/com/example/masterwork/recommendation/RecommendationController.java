package com.example.masterwork.recommendation;

import com.example.masterwork.recommendation.models.RecommendationDTO;
import com.example.masterwork.recommendation.models.RecommendationModDTO;
import com.example.masterwork.viewer.model.Viewer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class RecommendationController {

  private RecommendationService recommendationService;

  @Autowired
  public RecommendationController(RecommendationService recommendationService) {
    this.recommendationService = recommendationService;
  }

  @PostMapping("/recommendation")
  public ResponseEntity<RecommendationDTO> addRecommendation(@Valid @RequestBody RecommendationDTO recommendationDTO,
                                                             Authentication auth) {
    Viewer viewer = (Viewer) auth.getPrincipal();
    return ResponseEntity.status(201).body(recommendationService.addRecommendation(viewer, recommendationDTO));
  }

  @PutMapping("/recommendation/{id}")
  public ResponseEntity<RecommendationDTO> modifyRecommendation(@Valid @RequestBody RecommendationModDTO modDTO,
                                                                @PathVariable int id, Authentication auth) {
    Viewer viewer = (Viewer) auth.getPrincipal();
    return ResponseEntity.ok(recommendationService.modifyRecommendation(viewer, id, modDTO));
  }

}
