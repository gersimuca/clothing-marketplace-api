package com.gersimuca.cm.feature.garment;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gersimuca.cm.feature.grament.GarmentDto;
import com.gersimuca.cm.feature.user.UserEntity;
import com.gersimuca.cm.feature.user.UserTestData;
import java.util.Collections;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GarmentControllerITest {
  @Autowired private MockMvc mvc;

  @Autowired private ObjectMapper mapper;

  @Test
  @WithMockUser(
      username = "johnsmith",
      roles = {"CLIENT"})
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
  @WithMockUser(
      username = "johnsmith",
      roles = {"CLIENT"})
  @Order(2)
  void getAllGarmentsEndpointReturnsGarments() throws Exception {
    mvc.perform(
            get("/clothes")
                .param("type", "SHIRT")
                .param("size", "M")
                .param("minPrice", "10.00")
                .param("maxPrice", "50.00"))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON));
  }

  @Test
  @WithMockUser(
      username = "johnsmith",
      roles = {"CLIENT"})
  @Order(3)
  void publishGarmentEndpointCreatesGarment() throws Exception {
    UserEntity mockPublisher = UserTestData.JOHN_SMITH_ENTITY;
    mockPublisher.setUsername("johnsmith");

    GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_CLIENT");
    Authentication authentication =
        new UsernamePasswordAuthenticationToken(
            mockPublisher, null, Collections.singleton(authority));
    SecurityContextHolder.getContext().setAuthentication(authentication);

    GarmentDto garmentDto = GarmentTestData.createGarmentDto();

    mvc.perform(
            post("/clothes")
                .contentType(APPLICATION_JSON)
                .content(mapper.writeValueAsString(garmentDto)))
        .andExpect(status().isCreated())
        .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
        .andExpect(jsonPath("$.type").value(garmentDto.getType().toString()))
        .andExpect(jsonPath("$.size").value(garmentDto.getSize().toString()))
        .andExpect(jsonPath("$.price").value(garmentDto.getPrice().doubleValue()));

    SecurityContextHolder.clearContext();
  }

  @Test
  @WithMockUser(
      username = "johnsmith",
      roles = {"CLIENT"})
  @Order(4)
  void updateGarmentEndpointUpdatesGarment() throws Exception {
    UserEntity mockPublisher = UserTestData.JOHN_SMITH_ENTITY;
    mockPublisher.setUsername("johnsmith");

    GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_CLIENT");
    Authentication authentication =
        new UsernamePasswordAuthenticationToken(
            mockPublisher, null, Collections.singleton(authority));
    SecurityContextHolder.getContext().setAuthentication(authentication);

    GarmentDto updatedGarmentDto = GarmentTestData.updatedGarmentDto();

    mvc.perform(
            put("/clothes/{id}", 1L)
                .contentType(APPLICATION_JSON)
                .content(mapper.writeValueAsString(updatedGarmentDto)))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
        .andExpect(jsonPath("$.description").value(updatedGarmentDto.getDescription()))
        .andExpect(jsonPath("$.price").value(updatedGarmentDto.getPrice().doubleValue()));
    SecurityContextHolder.clearContext();
  }

  @Test
  @WithMockUser(
      username = "johnsmith",
      roles = {"CLIENT"})
  @Order(5)
  void deleteGarmentEndpointDeletesGarment() throws Exception {
    UserEntity mockPublisher = UserTestData.JOHN_SMITH_ENTITY;
    mockPublisher.setUsername("johnsmith");

    GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_CLIENT");
    Authentication authentication =
        new UsernamePasswordAuthenticationToken(
            mockPublisher, null, Collections.singleton(authority));
    SecurityContextHolder.getContext().setAuthentication(authentication);

    mvc.perform(delete("/clothes/{id}", 1L)).andExpect(status().isNoContent());
    SecurityContextHolder.clearContext();
  }
}
