package com.example.masterwork.recommendation;

import com.example.masterwork.exception.exceptions.RecommendationOwnedByOtherViewerException;
import com.example.masterwork.exception.exceptions.RequestCauseConflictException;
import com.example.masterwork.exception.exceptions.RequestForbiddenResourceException;
import com.example.masterwork.movie.MovieServiceImpl;
import com.example.masterwork.movie.models.Movie;
import com.example.masterwork.recommendation.models.Recommendation;
import com.example.masterwork.recommendation.models.RecommendationDTO;
import com.example.masterwork.recommendation.models.RecommendationModDTO;
import com.example.masterwork.utilities.DeleteDTO;
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
    int id = 1;
    Viewer viewer = testViewerBuilder()
        .recommendations(Collections.singletonList(testRecommendationBuilder().id(id).movie(movie).build()))
        .build();
    RecommendationModDTO modDTO = new RecommendationModDTO(5, "new recommendation");
    RecommendationDTO expected = new RecommendationDTO(testRecommendationBuilder()
        .id(id)
        .rating(modDTO.getRating())
        .recommendationText(modDTO.getRecommendationText())
        .movie(movie)
        .build());

    RecommendationDTO actual = recommendationService.modifyRecommendation(viewer, id, modDTO);

    assertEquals(expected, actual);
  }

  @Test
  public void when_modifyRecommendationInvalidRequest_should_throwException() {
    Movie movie = testMovieBuilder()
        .id(1)
        .recommendations(Collections.singletonList(defaultRecommendation()))
        .build();
    int id = 0;
    Viewer viewer = testViewerBuilder()
        .recommendations(Collections.singletonList(testRecommendationBuilder().id(1).movie(movie).build()))
        .build();
    RecommendationModDTO modDTO = new RecommendationModDTO(5, "new recommendation");

    Throwable exception = assertThrows(RequestForbiddenResourceException.class, () -> {
      recommendationService.modifyRecommendation(viewer, id, modDTO);
    });

    assertEquals("Forbidden operation: recommendation was made by other viewer", exception.getMessage());
  }

  @Test
  public void when_deleteOwnRecommendation_should_returnProperDTO() {
    int id = 113;
    Movie movie = testMovieBuilder()
        .recommendations(Collections.singletonList(testRecommendationBuilder().id(id).build()))
        .build();
    Recommendation recommendation = testRecommendationBuilder().id(id).movie(movie).build();
    Viewer viewer = testViewerBuilder().id(111).recommendations(Collections.singletonList(recommendation)).build();

    DeleteDTO actual = recommendationService.removeRecommendation(viewer, id);

    assertEquals(id, actual.getId());
  }

  @Test
  public void when_deleteOtherRecommendation_should_throwException() {
    int id = 113;
    Viewer viewer = testViewerBuilder().id(112)
        .recommendations(Collections.singletonList(defaultRecommendation()))
        .build();

    Throwable exception = assertThrows(RecommendationOwnedByOtherViewerException.class, () -> {
      recommendationService.removeRecommendation(viewer, id);
    });

    assertEquals("Forbidden operation: recommendation was made by other viewer", exception.getMessage());
  }

}
