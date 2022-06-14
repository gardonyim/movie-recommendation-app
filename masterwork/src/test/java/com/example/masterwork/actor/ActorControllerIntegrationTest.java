package com.example.masterwork.actor;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Sql("classpath:data.sql")
public class ActorControllerIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void test_getMoviesByActorId_should_respondOkStatusAndProperJson() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/actor/1/movies"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.movies").isArray());
  }
}
