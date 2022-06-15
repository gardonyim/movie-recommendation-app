package com.example.masterwork.viewer;

import com.example.masterwork.TestNoSecurityConfig;
import com.example.masterwork.exception.model.ErrorDTO;
import com.example.masterwork.viewer.model.RegistrationReqDTO;
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

import static com.example.masterwork.TestUtils.defaultRegistration;
import static com.example.masterwork.TestUtils.testRegistrationBuilder;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestNoSecurityConfig.class)
@Transactional
@Sql("classpath:data.sql")
public class ViewerControllerIntegrationTest {

  @Autowired
  MockMvc mockMvc;
  Gson gson = new Gson();

  @Disabled
  @Test
  public void test_postRegisterValidRequest_should_respondOkStatusAndProperJson() throws Exception {
    RegistrationReqDTO request = defaultRegistration();

    mockMvc.perform(MockMvcRequestBuilders.post("/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(gson.toJson(request)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").isNumber())
        .andExpect(jsonPath("$.username", is(request.getUsername())));
  }

  @Test
  public void test_postRegisterNoUsername_should_respondBadRequestStatusAndErrorMessage() throws Exception {
    RegistrationReqDTO request = testRegistrationBuilder().username(null).build();
    ErrorDTO expected = new ErrorDTO("Username is required");

    mockMvc.perform(MockMvcRequestBuilders.post("/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(gson.toJson(request)))
        .andExpect(status().isBadRequest())
        .andExpect(content().json(gson.toJson(expected)));
  }

  @Test
  public void test_postRegisterNoPassword_should_respondBadRequestStatusAndErrorMessage() throws Exception {
    RegistrationReqDTO request = testRegistrationBuilder().password(null).build();
    ErrorDTO expected = new ErrorDTO("Password is required");

    mockMvc.perform(MockMvcRequestBuilders.post("/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(gson.toJson(request)))
        .andExpect(status().isBadRequest())
        .andExpect(content().json(gson.toJson(expected)));
  }

  @Test
  public void test_postRegisterNoEmailAddress_should_respondBadRequestStatusAndErrorMessage() throws Exception {
    RegistrationReqDTO request = testRegistrationBuilder().email(null).build();
    ErrorDTO expected = new ErrorDTO("Email address is required");

    mockMvc.perform(MockMvcRequestBuilders.post("/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(gson.toJson(request)))
        .andExpect(status().isBadRequest())
        .andExpect(content().json(gson.toJson(expected)));
  }

  @Test
  public void test_postRegisterShortPassword_should_respondBadRequestStatusAndErrorMessage() throws Exception {
    RegistrationReqDTO request = testRegistrationBuilder().password("passwor").build();
    ErrorDTO expected = new ErrorDTO("Password should be al least 8 characters long");

    mockMvc.perform(MockMvcRequestBuilders.post("/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(gson.toJson(request)))
        .andExpect(status().isBadRequest())
        .andExpect(content().json(gson.toJson(expected)));
  }

  @Test
  public void test_postRegisterInvalidEmailAddress_should_respondBadRequestStatusAndErrorMessage() throws Exception {
    RegistrationReqDTO request = testRegistrationBuilder().email("test").build();
    ErrorDTO expected = new ErrorDTO("Valid e-mail address is required");

    mockMvc.perform(MockMvcRequestBuilders.post("/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(gson.toJson(request)))
        .andExpect(status().isBadRequest())
        .andExpect(content().json(gson.toJson(expected)));
  }

  @Test
  public void test_postRegisterExistingUsername_should_respondBadRequestStatusAndErrorMessage() throws Exception {
    RegistrationReqDTO request = testRegistrationBuilder().username("testviewer").build();
    ErrorDTO expected = new ErrorDTO("Username is already taken");

    mockMvc.perform(MockMvcRequestBuilders.post("/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(gson.toJson(request)))
        .andExpect(status().isConflict())
        .andExpect(content().json(gson.toJson(expected)));
  }

}
