package com.example.masterwork.recommendation;

import com.example.masterwork.TestNoSecurityConfig;
import com.example.masterwork.exception.model.ErrorDTO;
import com.example.masterwork.recommendation.models.RecommendationDTO;
import com.example.masterwork.recommendation.models.RecommendationModDTO;
import com.example.masterwork.viewer.model.Viewer;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;

import static com.example.masterwork.TestUtils.testMovieBuilder;
import static com.example.masterwork.TestUtils.testRecommendationBuilder;
import static com.example.masterwork.TestUtils.testViewerBuilder;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestNoSecurityConfig.class)
@Transactional
@Sql("classpath:data.sql")
public class RecommendationControllerIntegrationTest {

  @Autowired
  MockMvc mockMvc;
  Gson gson = new Gson();

  @Test
  public void test_postRecommendationNoRating_should_respondBadRequestStatusAndErrorMessage() throws Exception {
    RecommendationDTO request = new RecommendationDTO();
    request.setMovieId(1);
    Viewer viewer = testViewerBuilder().recommendations(new ArrayList<>()).build();
    Authentication auth = new UsernamePasswordAuthenticationToken(viewer, null, null);
    ErrorDTO expected = new ErrorDTO("Rating is required");

    mockMvc.perform(MockMvcRequestBuilders.post("/recommendation")
            .contentType(MediaType.APPLICATION_JSON)
            .content(gson.toJson(request))
            .principal(auth))
        .andExpect(status().isBadRequest())
        .andExpect(content().json(gson.toJson(expected)));
  }

  @Test
  public void test_postRecommendationNoMovieId_should_respondBadRequestStatusAndErrorMessage() throws Exception {
    RecommendationDTO request = new RecommendationDTO();
    request.setRating(5);
    Viewer viewer = testViewerBuilder().recommendations(Collections.emptyList()).build();
    Authentication auth = new UsernamePasswordAuthenticationToken(viewer, null, null);
    ErrorDTO expected = new ErrorDTO("Movie id is required");

    mockMvc.perform(MockMvcRequestBuilders.post("/recommendation")
            .contentType(MediaType.APPLICATION_JSON)
            .content(gson.toJson(request))
            .principal(auth))
        .andExpect(status().isBadRequest())
        .andExpect(content().json(gson.toJson(expected)));
  }

  @Test
  public void test_postRecommendationRatingBelow1_should_respondBadRequestStatusAndErrorMessage() throws Exception {
    RecommendationDTO request = new RecommendationDTO();
    request.setRating(0);
    request.setMovieId(113);
    Viewer viewer = testViewerBuilder().recommendations(Collections.emptyList()).build();
    Authentication auth = new UsernamePasswordAuthenticationToken(viewer, null, null);
    ErrorDTO expected = new ErrorDTO("Lowest rating is 1");

    mockMvc.perform(MockMvcRequestBuilders.post("/recommendation")
            .contentType(MediaType.APPLICATION_JSON)
            .content(gson.toJson(request))
            .principal(auth))
        .andExpect(status().isBadRequest())
        .andExpect(content().json(gson.toJson(expected)));
  }

  @Test
  public void test_postRecommendationRatingAbove10_should_respondBadRequestStatusAndErrorMessage() throws Exception {
    RecommendationDTO request = new RecommendationDTO();
    request.setRating(11);
    request.setMovieId(113);
    Viewer viewer = testViewerBuilder().recommendations(Collections.emptyList()).build();
    Authentication auth = new UsernamePasswordAuthenticationToken(viewer, null, null);
    ErrorDTO expected = new ErrorDTO("Highest rating is 10");

    mockMvc.perform(MockMvcRequestBuilders.post("/recommendation")
            .contentType(MediaType.APPLICATION_JSON)
            .content(gson.toJson(request))
            .principal(auth))
        .andExpect(status().isBadRequest())
        .andExpect(content().json(gson.toJson(expected)));
  }

  @Test
  public void test_postRecommendationInvalidMovieId_should_respondNotFoundStatusAndErrorMessage() throws Exception {
    RecommendationDTO request = new RecommendationDTO();
    request.setRating(5);
    request.setMovieId(0);
    Viewer viewer = testViewerBuilder().recommendations(new ArrayList<>()).build();
    Authentication auth = new UsernamePasswordAuthenticationToken(viewer, null, null);
    ErrorDTO expected = new ErrorDTO("Movie is not in the database.");

    mockMvc.perform(MockMvcRequestBuilders.post("/recommendation")
            .contentType(MediaType.APPLICATION_JSON)
            .content(gson.toJson(request))
            .principal(auth))
        .andExpect(status().isNotFound())
        .andExpect(content().json(gson.toJson(expected)));
  }

  @Test
  public void test_postExistingRecommendation_should_respondConflictStatusAndErrorMessage() throws Exception {
    RecommendationDTO request = new RecommendationDTO();
    request.setRating(5);
    request.setMovieId(111);
    Viewer viewer = testViewerBuilder()
        .recommendations(Collections.singletonList(testRecommendationBuilder()
            .movie(testMovieBuilder().id(111).build())
            .build()))
        .build();
    Authentication auth = new UsernamePasswordAuthenticationToken(viewer, null, null);
    ErrorDTO expected = new ErrorDTO("Recommendation already exists");

    mockMvc.perform(MockMvcRequestBuilders.post("/recommendation")
            .contentType(MediaType.APPLICATION_JSON)
            .content(gson.toJson(request))
            .principal(auth))
        .andExpect(status().isConflict())
        .andExpect(content().json(gson.toJson(expected)));
  }

  @Test
  public void test_putRecommendationValidRequest_should_respondOkStatusAndProperJson() throws Exception {
    RecommendationModDTO request = new RecommendationModDTO(6, "modified test2");
    Viewer viewer = testViewerBuilder()
        .id(111)
        .recommendations(Collections.singletonList(testRecommendationBuilder()
            .id(112)
            .movie(testMovieBuilder().id(112).build())
            .build()))
        .build();
    Authentication auth = new UsernamePasswordAuthenticationToken(viewer, null, null);

    mockMvc.perform(MockMvcRequestBuilders.put("/recommendation/112")
            .contentType(MediaType.APPLICATION_JSON)
            .content(gson.toJson(request))
            .principal(auth))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", is(112)))
        .andExpect(jsonPath("$.rating", is(6)))
        .andExpect(jsonPath("$.recommendationText", is("modified test2")))
        .andExpect(jsonPath("$.movieId", is(112)));
  }

  @Test
  public void test_putRecommendationNoRating_should_respondBadRequestStatusAndErrorMessage() throws Exception {
    RecommendationModDTO request = new RecommendationModDTO(null, "modified test2");
    Viewer viewer = testViewerBuilder()
        .id(111)
        .recommendations(Collections.singletonList(testRecommendationBuilder()
            .id(112)
            .movie(testMovieBuilder().id(112).build())
            .build()))
        .build();
    Authentication auth = new UsernamePasswordAuthenticationToken(viewer, null, null);
    ErrorDTO expected = new ErrorDTO("Rating is required");

    mockMvc.perform(MockMvcRequestBuilders.put("/recommendation/112")
            .contentType(MediaType.APPLICATION_JSON)
            .content(gson.toJson(request))
            .principal(auth))
        .andExpect(status().isBadRequest())
        .andExpect(content().json(gson.toJson(expected)));
  }

  @Test
  public void test_putRecommendationRatingBelow1_should_respondBadRequestStatusAndErrorMessage() throws Exception {
    RecommendationModDTO request = new RecommendationModDTO(0, "modified test2");
    Viewer viewer = testViewerBuilder()
        .id(111)
        .recommendations(Collections.singletonList(testRecommendationBuilder()
            .id(112)
            .movie(testMovieBuilder().id(112).build())
            .build()))
        .build();
    Authentication auth = new UsernamePasswordAuthenticationToken(viewer, null, null);
    ErrorDTO expected = new ErrorDTO("Lowest rating is 1");

    mockMvc.perform(MockMvcRequestBuilders.put("/recommendation/112")
            .contentType(MediaType.APPLICATION_JSON)
            .content(gson.toJson(request))
            .principal(auth))
        .andExpect(status().isBadRequest())
        .andExpect(content().json(gson.toJson(expected)));
  }

  @Test
  public void test_putRecommendationRatingAbove10_should_respondBadRequestStatusAndErrorMessage() throws Exception {
    RecommendationModDTO request = new RecommendationModDTO(11, "modified test2");
    Viewer viewer = testViewerBuilder()
        .id(111)
        .recommendations(Collections.singletonList(testRecommendationBuilder()
            .id(112)
            .movie(testMovieBuilder().id(112).build())
            .build()))
        .build();
    Authentication auth = new UsernamePasswordAuthenticationToken(viewer, null, null);
    ErrorDTO expected = new ErrorDTO("Highest rating is 10");

    mockMvc.perform(MockMvcRequestBuilders.put("/recommendation/112")
            .contentType(MediaType.APPLICATION_JSON)
            .content(gson.toJson(request))
            .principal(auth))
        .andExpect(status().isBadRequest())
        .andExpect(content().json(gson.toJson(expected)));
  }

  @Test
  public void test_putRecommendationBelongToOtherViewer_should_respondForbiddenStatusAndErrorMessage() throws Exception {
    RecommendationModDTO request = new RecommendationModDTO(10, "modified test");
    Viewer viewer = testViewerBuilder()
        .id(112)
        .recommendations(Collections.emptyList())
        .build();
    Authentication auth = new UsernamePasswordAuthenticationToken(viewer, null, null);
    ErrorDTO expected = new ErrorDTO("Forbidden operation: recommendation was made by other viewer");

    mockMvc.perform(MockMvcRequestBuilders.put("/recommendation/112")
            .contentType(MediaType.APPLICATION_JSON)
            .content(gson.toJson(request))
            .principal(auth))
        .andExpect(status().isForbidden())
        .andExpect(content().json(gson.toJson(expected)));
  }

  @Test
  public void test_deleteOthersRecommendation_should_returnForbiddenStatusAndErrorMessage() throws Exception {
    Viewer viewer = testViewerBuilder().recommendations(Collections.emptyList()).build();
    Authentication auth = new UsernamePasswordAuthenticationToken(viewer, null, null);
    ErrorDTO expected = new ErrorDTO("Forbidden operation: recommendation was made by other viewer");

    mockMvc.perform(MockMvcRequestBuilders.delete("/recommendation/113")
            .principal(auth))
        .andExpect(status().isForbidden())
        .andExpect(content().json(gson.toJson(expected)));
  }

}
