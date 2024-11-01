package com.gersimuca.cm.feature.user;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserControllerITest {

  @Autowired private MockMvc mvc;

  @Autowired private ObjectMapper mapper;

  @Test
  @Order(1)
  void createUserEndpointReturnsCreatedUser() throws Exception {

    mvc.perform(
            post("/create")
                .contentType(APPLICATION_JSON)
                .content(mapper.writeValueAsString(UserTestData.USER_CREATE_REQUEST)))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON));
  }

  @Test
  @Order(2)
  void loginUserEndpointReturnsUser() throws Exception {
    mvc.perform(
            post("/login")
                .contentType(APPLICATION_JSON)
                .content(mapper.writeValueAsString(UserTestData.USER_LOGIN_REQUEST)))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON));
  }
}
