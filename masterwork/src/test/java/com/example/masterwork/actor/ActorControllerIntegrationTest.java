package com.example.masterwork.actor;

import com.example.masterwork.TestNoSecurityConfig;
import com.example.masterwork.actor.models.ActorDTO;
import com.example.masterwork.exception.model.ErrorDTO;
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

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestNoSecurityConfig.class)
@Transactional
@Sql("classpath:data.sql")
public class ActorControllerIntegrationTest {

  @Autowired
  MockMvc mockMvc;
  Gson gson = new Gson();

  @Test
  public void test_getMoviesByActorId_should_respondOkStatusAndProperJson() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/actor/111/movies"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.movies").isArray())
        .andExpect(jsonPath("$.movies[0].id", is(111)))
        .andExpect(jsonPath("$.movies[0].title", is("testmovie")))
        .andExpect(jsonPath("$.movies[0].averageRating", is(5.0)));
  }

  @Test
  public void test_getMoviesByActorInvalidId_should_respondNotFoundStatusANdProperErrorMessage() throws Exception {
    ErrorDTO expected = new ErrorDTO("Actor/actress is not in the database");

    mockMvc.perform(MockMvcRequestBuilders.get("/actor/0/movies"))
        .andExpect(status().isNotFound())
        .andExpect(content().json(gson.toJson(expected)));
  }

  @Test
  public void test_getAllActors_should_respondOkStatusAndProperJson() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/actor/all"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.actors").isArray())
        .andExpect(jsonPath("$.actors[0].id", is(111)))
        .andExpect(jsonPath("$.actors[0].name", is("testactor")));
  }

  @Test
  public void test_postNewActor_should_respondCreatedStatusAndProperJson() throws Exception {
    ActorDTO request = new ActorDTO();
    request.setName("newActor");

    mockMvc.perform(MockMvcRequestBuilders.post("/actor")
            .contentType(MediaType.APPLICATION_JSON)
            .content(gson.toJson(request)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").isNumber())
        .andExpect(jsonPath("$.name", is(request.getName())));
  }

  @Test
  public void test_postActorWithNoName_should_respondBadRequestStatusAndProperErrorMessage() throws Exception {
    ErrorDTO expected = new ErrorDTO("Name is required");

    mockMvc.perform(MockMvcRequestBuilders.post("/actor")
            .contentType(MediaType.APPLICATION_JSON)
            .content(gson.toJson(new ActorDTO())))
        .andExpect(status().isBadRequest())
        .andExpect(content().json(gson.toJson(expected)));
  }

  @Test
  public void test_postExistingActor_should_respondConflictAndProperErrorMessage() throws Exception {
    ActorDTO request = new ActorDTO();
    request.setName("testactor");
    ErrorDTO expected = new ErrorDTO("Actor/actress is already in the database");

    mockMvc.perform(MockMvcRequestBuilders.post("/actor")
            .contentType(MediaType.APPLICATION_JSON)
            .content(gson.toJson(request)))
        .andExpect(status().isConflict())
        .andExpect(content().json(gson.toJson(expected)));
  }

}
