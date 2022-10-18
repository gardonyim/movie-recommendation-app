package com.example.masterwork.movie;

import com.example.masterwork.TestNoSecurityConfig;
import com.example.masterwork.exception.model.ErrorDTO;
import com.example.masterwork.movie.models.MovieReqDTO;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.transaction.Transactional;

import static com.example.masterwork.TestUtils.defaultReqDTO;
import static com.example.masterwork.TestUtils.testReqDtoBuilder;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestNoSecurityConfig.class)
@Transactional
@Sql("classpath:data.sql")
public class MovieControllerIntegrationTest {

  @Autowired
  MockMvc mockMvc;
  Gson gson = new Gson();

  @Test
  public void test_getTopRatedMoviesNoLimit_should_respondOkStatusAndProperJsonWithDefaultLimitItems() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/movie/topRated"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.movies").isArray())
        .andExpect(jsonPath("$.movies.length()", is(2)))
        .andExpect(jsonPath("$.movies[0].id", is(112)))
        .andExpect(jsonPath("$.movies[0].title", is("testmovie2")))
        .andExpect(jsonPath("$.movies[0].averageRating", is(6.0)));
  }

  @Test
  public void test_getTopRatedMoviesWithLimit_should_respondOkStatusAndProperJsonWithDefaultLimitItems() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/movie/topRated?limit=1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.movies").isArray())
        .andExpect(jsonPath("$.movies.length()", is(1)))
        .andExpect(jsonPath("$.movies[0].id", is(112)))
        .andExpect(jsonPath("$.movies[0].title", is("testmovie2")))
        .andExpect(jsonPath("$.movies[0].averageRating", is(6.0)));
  }

  @Test
  public void test_getMovieByValidId_should_respondOkStatusAndProperJson() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/movie/111"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", is(111)))
        .andExpect(jsonPath("$.title", is("testmovie")))
        .andExpect(jsonPath("$.director", is("testdirector")))
        .andExpect(jsonPath("$.cast[0].id", is(111)))
        .andExpect(jsonPath("$.cast[0].name", is("testactor")))
        .andExpect(jsonPath("$.genre", is("testgenre")))
        .andExpect(jsonPath("$.releaseYear", is(2022)))
        .andExpect(jsonPath("$.length", is(90)))
        .andExpect(jsonPath("$.averageRating", is(5.0)))
        .andExpect(jsonPath("$.recommendations[0].id", is(111)))
        .andExpect(jsonPath("$.recommendations[0].rating", is(5)))
        .andExpect(jsonPath("$.recommendations[0].recommendationText", is("test")));
  }

  @Test
  public void test_getMovieByInvalidId_should_respondNotFoundStatusAndErrorMessage() throws Exception {
    ErrorDTO expected = new ErrorDTO("Movie is not in the database.");

    mockMvc.perform(MockMvcRequestBuilders.get("/movie/0"))
        .andExpect(status().isNotFound())
        .andExpect(content().json(gson.toJson(expected)));
  }

  @Test
  public void test_getMovieByValidTitle_should_respondOkStatusAndProperJson() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/movie?title=testmovie"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.movies").isArray())
        .andExpect(jsonPath("$.movies", hasSize(2)))
        .andExpect(jsonPath("$.movies[0].id").isNumber())
        .andExpect(jsonPath("$.movies[0].title").isString())
        .andExpect(jsonPath("$.movies[0].averageRating").isNumber());
  }

  @Test
  public void test_getMovieByInvalidTitle_should_respondNotFoundStatusAndErrorMessage() throws Exception {
    ErrorDTO expected = new ErrorDTO("Movie is not in the database.");

    mockMvc.perform(MockMvcRequestBuilders.get("/movie?title=nonexistingmovie"))
        .andExpect(status().isNotFound())
        .andExpect(content().json(gson.toJson(expected)));
  }

  @Test
  public void test_postMovieWithValidRequest_should_respondCreatedStatusAndProperJson() throws Exception {
    MovieReqDTO request = defaultReqDTO();

    mockMvc.perform(MockMvcRequestBuilders.post("/movie")
            .contentType(MediaType.APPLICATION_JSON)
            .content(gson.toJson(request)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").isNumber())
        .andExpect(jsonPath("$.title", is("test movie")))
        .andExpect(jsonPath("$.director", is("testdirector")))
        .andExpect(jsonPath("$.cast[0].id", is(111)))
        .andExpect(jsonPath("$.cast[0].name", is("testactor")))
        .andExpect(jsonPath("$.genre", is("testgenre")))
        .andExpect(jsonPath("$.releaseYear", is(2022)))
        .andExpect(jsonPath("$.length", is(90)));
  }

  @Test
  public void test_postMovieWithNoTitle_should_respondBadRequestStatusAndErrorMessage() throws Exception {
    MovieReqDTO request = testReqDtoBuilder().title(null).build();
    ErrorDTO expected = new ErrorDTO("Title is required");

    mockMvc.perform(MockMvcRequestBuilders.post("/movie")
            .contentType(MediaType.APPLICATION_JSON)
            .content(gson.toJson(request)))
        .andExpect(status().isBadRequest())
        .andExpect(content().json(gson.toJson(expected)));
  }

  @Test
  public void test_postMovieWithNoDirector_should_respondBadRequestStatusAndErrorMessage() throws Exception {
    MovieReqDTO request = testReqDtoBuilder().directorId(null).build();
    ErrorDTO expected = new ErrorDTO("Director id is required");

    mockMvc.perform(MockMvcRequestBuilders.post("/movie")
            .contentType(MediaType.APPLICATION_JSON)
            .content(gson.toJson(request)))
        .andExpect(status().isBadRequest())
        .andExpect(content().json(gson.toJson(expected)));
  }

  @Test
  public void test_postMovieWithNoCast_should_respondBadRequestStatusAndErrorMessage() throws Exception {
    MovieReqDTO request = testReqDtoBuilder().actorIdList(null).build();
    ErrorDTO expected = new ErrorDTO("List of actor ids is required");

    mockMvc.perform(MockMvcRequestBuilders.post("/movie")
            .contentType(MediaType.APPLICATION_JSON)
            .content(gson.toJson(request)))
        .andExpect(status().isBadRequest())
        .andExpect(content().json(gson.toJson(expected)));
  }

}
