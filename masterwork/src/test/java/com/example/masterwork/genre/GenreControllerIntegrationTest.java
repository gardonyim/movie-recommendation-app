package com.example.masterwork.genre;

import com.example.masterwork.TestNoSecurityConfig;
import com.example.masterwork.exception.model.ErrorDTO;
import com.example.masterwork.genre.models.GenreDTO;
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
public class GenreControllerIntegrationTest {

  @Autowired
  MockMvc mockMvc;
  Gson gson = new Gson();

  @Test
  public void test_postNewGenre_should_respondCreatedStatusAndProperJson() throws Exception {
    GenreDTO genreDTO = new GenreDTO();
    genreDTO.setType("new genre");

    mockMvc.perform(MockMvcRequestBuilders.post("/genre")
            .contentType(MediaType.APPLICATION_JSON)
            .content(gson.toJson(genreDTO)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").isNumber())
        .andExpect(jsonPath("$.type", is(genreDTO.getType())));
  }

  @Test
  public void test_postGenreNoGenreType_should_respondBadRequestStatusAndErrorMessage() throws Exception {
    ErrorDTO expected = new ErrorDTO("Genre type is required");

    mockMvc.perform(MockMvcRequestBuilders.post("/genre")
            .contentType(MediaType.APPLICATION_JSON)
            .content(gson.toJson(new GenreDTO())))
        .andExpect(status().isBadRequest())
        .andExpect(content().json(gson.toJson(expected)));
  }

  @Test
  public void test_postGenreExistingGenreType_should_respondConflictStatusAndErrorMessage() throws Exception {
    GenreDTO genreDTO = new GenreDTO();
    genreDTO.setType("testgenre");
    ErrorDTO expected = new ErrorDTO("Genre is already present in the database");

    mockMvc.perform(MockMvcRequestBuilders.post("/genre")
            .contentType(MediaType.APPLICATION_JSON)
            .content(gson.toJson(genreDTO)))
        .andExpect(status().isConflict())
        .andExpect(content().json(gson.toJson(expected)));
  }

  @Test
  public void test_getGenres_should_respondOkStatusAndProperJson() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/genre/all"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.genres").isArray())
        .andExpect(jsonPath("$.genres[0].id", is(111)))
        .andExpect(jsonPath("$.genres[0].type", is("testgenre")));
  }

}
