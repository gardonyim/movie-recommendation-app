package com.example.masterwork.recommendation;

import com.example.masterwork.exception.exceptions.RequestCauseConflictException;
import com.example.masterwork.movie.MovieServiceImpl;
import com.example.masterwork.movie.models.Movie;
import com.example.masterwork.recommendation.models.Recommendation;
import com.example.masterwork.recommendation.models.RecommendationDTO;
import com.example.masterwork.recommendation.models.RecommendationModDTO;
import com.example.masterwork.viewer.model.Viewer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static com.example.masterwork.TestUtils.defaultMovie;
import static com.example.masterwork.TestUtils.defaultRecommendation;
import static com.example.masterwork.TestUtils.testMovieBuilder;
import static com.example.masterwork.TestUtils.testRecommendationBuilder;
import static com.example.masterwork.TestUtils.testViewerBuilder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RecommendationServiceTest {

  @Mock
  RecommendationRepository recommendationRepository;

  @Mock
  MovieServiceImpl movieService;

  @InjectMocks
  RecommendationServiceImpl recommendationService;

  @Test
  public void when_addNewRecommendation_should_returnProperDTO() {
    Viewer viewer = testViewerBuilder().recommendations(Collections.emptyList()).build();
    Movie movie = defaultMovie();
    when(movieService.getMovieById(anyInt())).thenReturn(movie);
    Recommendation recommendation = testRecommendationBuilder().movie(defaultMovie()).build();
    when(recommendationRepository.save(any(Recommendation.class))).thenReturn(recommendation);
    RecommendationDTO expected = new RecommendationDTO(recommendation);

    RecommendationDTO actual = recommendationService.addRecommendation(viewer, new RecommendationDTO(recommendation));

    assertEquals(expected, actual);
  }

  @Test
  public void when_addExistingRecommendation_should_throwException() {
    Recommendation recommendation = testRecommendationBuilder().movie(defaultMovie()).build();
    Viewer viewer = testViewerBuilder().recommendations(
        Collections.singletonList(recommendation)).build();
    RecommendationDTO recommendationDTO = new RecommendationDTO(recommendation);

    Throwable exception = assertThrows(RequestCauseConflictException.class, () -> {
      recommendationService.addRecommendation(viewer, recommendationDTO);
    });

    assertEquals("Recommendation already exists", exception.getMessage());
  }

  @Test
  public void when_modifyRecommendationValidRequest_should_returnProperDTO() {
    Movie movie = testMovieBuilder()
        .id(1)
        .recommendations(Collections.singletonList(defaultRecommendation()))
        .build();
    when(movieService.getMovieById(anyInt())).thenReturn(movie);
    when(recommendationRepository.save(any(Recommendation.class))).then(returnsFirstArg());
    Viewer viewer = testViewerBuilder()
        .recommendations(Collections.singletonList(testRecommendationBuilder().id(1).movie(movie).build()))
        .build();
    int id = 1;
    RecommendationModDTO modDTO = new RecommendationModDTO(5, "new recommendation");
    RecommendationDTO expected = new RecommendationDTO(testRecommendationBuilder()
        .id(1)
        .rating(modDTO.getRating())
        .recommendationText(modDTO.getRecommendationText())
        .movie(movie)
        .build());

    RecommendationDTO actual = recommendationService.modifyRecommendation(viewer, id, modDTO);

    assertEquals(expected, actual);
  }

}
