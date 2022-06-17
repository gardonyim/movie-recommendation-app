package com.example.masterwork.auth;

import com.example.masterwork.TestNoSecurityConfig;
import com.example.masterwork.authentication.models.LoginDTO;
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
public class AuthControllerIntegrationTest {

  @Autowired
  MockMvc mockMvc;
  Gson gson = new Gson();

  @Test
  public void test_loginValidCredentials_should_respondOkStatusAndProperJson() throws Exception {
    LoginDTO loginDTO = new LoginDTO("testviewer", "password");

    mockMvc.perform(MockMvcRequestBuilders.post("/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(gson.toJson(loginDTO)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status", is("ok")));
  }

  @Test
  public void test_loginNoUsername_should_respondBadRequestStatusAndErrorMessage() throws Exception {
    LoginDTO loginDTO = new LoginDTO(null, "password");
    ErrorDTO expected = new ErrorDTO("Username is required");

    mockMvc.perform(MockMvcRequestBuilders.post("/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(gson.toJson(loginDTO)))
        .andExpect(status().isBadRequest())
        .andExpect(content().json(gson.toJson(expected)));
  }

  @Test
  public void test_loginNoPassword_should_respondBadRequestStatusAndErrorMessage() throws Exception {
    LoginDTO loginDTO = new LoginDTO("testviewer", null);
    ErrorDTO expected = new ErrorDTO("Password is required");

    mockMvc.perform(MockMvcRequestBuilders.post("/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(gson.toJson(loginDTO)))
        .andExpect(status().isBadRequest())
        .andExpect(content().json(gson.toJson(expected)));
  }

  @Test
  public void test_loginNoUsernameAndPassword_should_respondBadRequestStatusAndErrorMessage() throws Exception {
    LoginDTO loginDTO = new LoginDTO(null, null);
    ErrorDTO expected = new ErrorDTO("Password is required, username is required");

    mockMvc.perform(MockMvcRequestBuilders.post("/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(gson.toJson(loginDTO)))
        .andExpect(status().isBadRequest())
        .andExpect(content().json(gson.toJson(expected)));
  }

  @Test
  public void test_loginNonexistentUser_should_respondUnauthorizedStatusAndErrorMessage() throws Exception {
    LoginDTO loginDTO = new LoginDTO("nonexistentviewer", "password");
    ErrorDTO expected = new ErrorDTO("Username or password is incorrect.");

    mockMvc.perform(MockMvcRequestBuilders.post("/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(gson.toJson(loginDTO)))
        .andExpect(status().isUnauthorized())
        .andExpect(content().json(gson.toJson(expected)));
  }

  @Test
  public void test_loginWrongPassword_should_respondUnauthorizedStatusAndErrorMessage() throws Exception {
    LoginDTO loginDTO = new LoginDTO("testviewer", "wrongpassword");
    ErrorDTO expected = new ErrorDTO("Username or password is incorrect.");

    mockMvc.perform(MockMvcRequestBuilders.post("/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(gson.toJson(loginDTO)))
        .andExpect(status().isUnauthorized())
        .andExpect(content().json(gson.toJson(expected)));
  }

}
