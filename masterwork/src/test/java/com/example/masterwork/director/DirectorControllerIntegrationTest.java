package com.example.masterwork.director;

import com.example.masterwork.TestNoSecurityConfig;
import com.example.masterwork.actor.models.ActorDTO;
import com.example.masterwork.director.models.DirectorDTO;
import com.example.masterwork.exception.model.ErrorDTO;
import com.google.gson.Gson;
import org.junit.jupiter.api.Disabled;
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

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestNoSecurityConfig.class)
@Transactional
@Sql("classpath:data.sql")
public class DirectorControllerIntegrationTest {

  @Autowired
  MockMvc mockMvc;
  Gson gson = new Gson();

  @Test
  public void test_getMoviesByDirectorId_should_respondOkStatusAndProperJson() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/director/1/movies"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.movies").isArray())
        .andExpect(jsonPath("$.movies[0].id", is(1)))
        .andExpect(jsonPath("$.movies[0].title", is("testmovie")))
        .andExpect(jsonPath("$.movies[0].averageRating", is(5.0)));
  }

  @Test
  public void test_getMoviesByDirectorInvalidId_should_respondNotFoundStatusANdProperErrorMessage() throws Exception {
    ErrorDTO expected = new ErrorDTO("Director is not in the database");

    mockMvc.perform(MockMvcRequestBuilders.get("/director/0/movies"))
        .andExpect(status().isNotFound())
        .andExpect(content().json(gson.toJson(expected)));
  }

  @Test
  public void test_getAllDirector_should_respondOkStatusAndProperJson() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/director/all"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.directors").isArray())
        .andExpect(jsonPath("$.directors[0].id", is(1)))
        .andExpect(jsonPath("$.directors[0].name", is("testdirector")));
  }

  @Disabled
  @Test
  public void test_postNewDirector_should_respondCreatedStatusAndProperJson() throws Exception {
    DirectorDTO request = new DirectorDTO();
    request.setName("newDirector");

    mockMvc.perform(MockMvcRequestBuilders.post("/director")
            .contentType(MediaType.APPLICATION_JSON)
            .content(gson.toJson(request)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").isNumber())
        .andExpect(jsonPath("$.name", is(request.getName())));
  }

  @Test
  public void test_postDirectorWithNoName_should_respondBadRequestStatusAndProperErrorMessage() throws Exception {
    ErrorDTO expected = new ErrorDTO("Name is required");

    mockMvc.perform(MockMvcRequestBuilders.post("/director")
            .contentType(MediaType.APPLICATION_JSON)
            .content(gson.toJson(new ActorDTO())))
        .andExpect(status().isBadRequest())
        .andExpect(content().json(gson.toJson(expected)));
  }

  @Test
  public void test_postExistingDirector_should_respondConflictAndProperErrorMessage() throws Exception {
    DirectorDTO request = new DirectorDTO();
    request.setName("testdirector");
    ErrorDTO expected = new ErrorDTO("Director is already in the database");

    mockMvc.perform(MockMvcRequestBuilders.post("/director")
            .contentType(MediaType.APPLICATION_JSON)
            .content(gson.toJson(request)))
        .andExpect(status().isConflict())
        .andExpect(content().json(gson.toJson(expected)));
  }

}
