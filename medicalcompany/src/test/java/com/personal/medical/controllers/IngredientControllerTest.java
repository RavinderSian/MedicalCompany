   package com.personal.medical.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.personal.medical.model.Ingredient;
import com.personal.medical.services.IngredientService;

@WebMvcTest(IngredientController.class)
class IngredientControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	private IngredientController controller;

	@MockBean
	private IngredientService ingredientService;

	@BeforeEach
	void setUp() {
		controller = new IngredientController(ingredientService);
	}
	
	@Test
	void contextLoads() throws Exception {
		assertThat(controller).isNotNull();
	}
	
	@Test
	void test_GetById_ReturnsCorrectStatusAndIngredient_WhenGivenId1() throws Exception {
		
		Ingredient ingredient = new Ingredient();
		ingredient.setName("paracetamol");
		ingredient.setIngredientId(1L);
		
		when(ingredientService.findById(1L)).thenReturn(Optional.of(ingredient));
		
		this.mockMvc.perform(get("/ingredient/1")).andDo(print())
		.andExpect(status().isOk())
		.andExpect(content().json("{'ingredientId': 1, 'name': 'paracetamol'}")); 
	}
	
	@Test
	void test_GetById_ReturnsNoDataFoundForId5_WhenGivenId5() throws Exception {
		
		this.mockMvc.perform(get("/ingredient/5")).andDo(print())
		.andExpect(status().isNotFound());
	}
	
	@Test
	void test_Add_ReturnsCorrectStatusAndIngredient_WhenGivenIngredient() throws Exception {
		
		Ingredient ingredient = new Ingredient();
		ingredient.setName("paracetamol");
		ingredient.setIngredientId(1L);
		
		when(ingredientService.save(ingredient)).thenReturn(ingredient);
		
	    ObjectMapper mapper = new ObjectMapper();
	    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
	    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
	    String requestJson = ow.writeValueAsString(ingredient);
		
		this.mockMvc.perform(put("/ingredient/save").contentType(MediaType.APPLICATION_JSON_VALUE).content(requestJson))
		.andExpect(status().isOk())
		.andExpect(content().json("{'ingredientId': 1, 'name': 'paracetamol'}"));
	}
	
	@Test
	void test_Add_ReturnsCorrectStatusAndIngredient_WhenGivenInValidIngredient() throws Exception {
		
		Ingredient ingredient = new Ingredient();
		ingredient.setIngredientId(1L);
		
		when(ingredientService.save(ingredient)).thenReturn(ingredient);
		
	    ObjectMapper mapper = new ObjectMapper();
	    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
	    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
	    String requestJson = ow.writeValueAsString(ingredient);
		
		this.mockMvc.perform(put("/ingredient/save").contentType(MediaType.APPLICATION_JSON_VALUE).content(requestJson))
		.andExpect(status().isBadRequest())
		.andExpect(content().string("[Please enter a valid name]"));
	}
	
	@Test
	void test_UpdateName_CorrectlyUpdatesName_WhenGivenNameSalicylicAcidAndId1() throws Exception {
		
		Ingredient ingredient = new Ingredient();
		ingredient.setName("paracetamol");
		ingredient.setIngredientId(1L);
		
		when(ingredientService.findById(1L)).thenReturn(Optional.of(ingredient));
		ingredient.setName("new");
		when(ingredientService.updateIngredientName(ingredient, "new")).thenReturn(ingredient);
		this.mockMvc.perform(patch("/ingredient/1/updatename").contentType(MediaType.APPLICATION_JSON_VALUE).content("new"))
		.andExpect(status().isOk())
		.andExpect(content().json("{'ingredientId': 1, 'name': 'new'}"));
	}
	
	@Test
	void test_UpdateName_ReturnsNoDataForId5_WhenGivenNameNewAndId5() throws Exception {
		
		this.mockMvc.perform(patch("/ingredient/5/updatename").contentType(MediaType.APPLICATION_JSON_VALUE).content("new"))
		.andExpect(status().isNotFound());
	}
	
}
