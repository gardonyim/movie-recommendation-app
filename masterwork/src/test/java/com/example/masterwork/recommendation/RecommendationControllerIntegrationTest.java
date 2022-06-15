package com.example.masterwork.recommendation;

import com.example.masterwork.TestNoSecurityConfig;
import com.example.masterwork.exception.model.ErrorDTO;
import com.example.masterwork.recommendation.models.RecommendationDTO;
import com.example.masterwork.viewer.model.Viewer;
import com.google.gson.Gson;
import org.junit.jupiter.api.Disabled;
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

  @Disabled
  @Test
  public void test_postRecommendation_should_respondOkStatusAndProperJson() throws Exception {
    RecommendationDTO request = new RecommendationDTO(testRecommendationBuilder()
        .id(null)
        .movie(testMovieBuilder().id(1).build())
        .build());
    Viewer viewer = testViewerBuilder().recommendations(new ArrayList<>()).build();
    Authentication auth = new UsernamePasswordAuthenticationToken(viewer, null, null);

    mockMvc.perform(MockMvcRequestBuilders.post("/recommendation")
            .contentType(MediaType.APPLICATION_JSON)
            .content(gson.toJson(request))
            .principal(auth))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").isNumber());
  }

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
    Viewer viewer = testViewerBuilder().recommendations(new ArrayList<>()).build();
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
  public void test_postRecommendationInvalidMovieId_should_respondBadRequestStatusAndErrorMessage() throws Exception {
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
  public void test_postExistingRecommendation_should_respondBadRequestStatusAndErrorMessage() throws Exception {
    RecommendationDTO request = new RecommendationDTO();
    request.setRating(5);
    request.setMovieId(1);
    Viewer viewer = testViewerBuilder()
        .recommendations(Collections.singletonList(testRecommendationBuilder()
            .movie(testMovieBuilder().id(1).build())
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

}
