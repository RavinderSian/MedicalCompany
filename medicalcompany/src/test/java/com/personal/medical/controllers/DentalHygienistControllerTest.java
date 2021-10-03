package com.personal.medical.controllers;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.personal.medical.model.DentalHygienist;
import com.personal.medical.services.DentalHygienistService;

@AutoConfigureMockMvc
@WebMvcTest(DentalHygienistController.class)
class DentalHygienistControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	private DentalHygienistController controller;
	
	@MockBean
	private DentalHygienistService service;
	
	@BeforeEach
	void setUp() throws Exception {
		this.controller = new DentalHygienistController(service);
	}

	@Test
	void test_ControllerNotNull() {
		assertThat(controller, notNullValue());
	}
	
	@Test
	void test_FindById_ReturnsCorrectResponseWhenEntityForIdPresent() throws Exception {
		
		DentalHygienist hygienist = new DentalHygienist();
		hygienist.setId(1L);
		hygienist.setFirstName("test");
		hygienist.setLastName("testing");
		when(service.findById(1L)).thenReturn(Optional.of(hygienist));
		
		mockMvc.perform(get("/hygienist/1"))
		.andExpect(status().isOk())
		.andExpect(content().json("{'id': 1, 'firstName': 'test', 'lastName' : 'testing'}"));
	}

	@Test
	void test_FindById_ReturnsCorrectResponseWithNoEntityForIdPresent() throws Exception {
		mockMvc.perform(get("/hygienist/10"))
		.andExpect(status().isNotFound());
	}
	
	@Test
	void test_Delete_ReturnsCorrectResponseWhenEntityForIdPresent() throws Exception {
		DentalHygienist hygienist = new DentalHygienist();
		hygienist.setId(1L);
		
		when(service.findById(1L)).thenReturn(Optional.of(hygienist));
		mockMvc.perform(delete("/hygienist/delete/1"))
		.andExpect(status().isOk());
	}
	
	@Test
	void test_Delete_ReturnsCorrectResponseWithNoEntityForIdPresent() throws Exception {
		mockMvc.perform(delete("/hygienist/delete/10"))
		.andExpect(status().isNotFound());
	}
	
	@Test
	void test_Save_ReturnsCorrectResponse_WhenGivenValidEntity() throws Exception {
		
		DentalHygienist hygienist = new DentalHygienist();
		hygienist.setId(1L);
		hygienist.setFirstName("test");
		hygienist.setLastName("testing");
		when(service.save(hygienist)).thenReturn(hygienist);
		
	    ObjectMapper mapper = new ObjectMapper();
	    
	    this.mockMvc.perform(put("/hygienist/save").contentType(MediaType.APPLICATION_JSON_VALUE).content(mapper.writer().writeValueAsString(hygienist)))
	    	.andExpect(status().isOk());
	}
	
	@Test
	void test_Save_ReturnsCorrectResponse_WhenGivenEmptyFirstName() throws Exception {
		
		DentalHygienist hygienist = new DentalHygienist();
		hygienist.setId(1L);
		hygienist.setFirstName("");
		hygienist.setLastName("testing");
		when(service.save(hygienist)).thenReturn(hygienist);
		
	    ObjectMapper mapper = new ObjectMapper();
	    
	    this.mockMvc.perform(put("/hygienist/save").contentType(MediaType.APPLICATION_JSON_VALUE).content(mapper.writer().writeValueAsString(hygienist)))
	    	.andExpect(status().isBadRequest())
	    	.andExpect(content().json("{'firstName' : 'Please enter a valid first name'}"));
	}
	
	@Test
	void test_Save_ReturnsCorrectResponse_WhenGivenNoFirstName() throws Exception {
		
		DentalHygienist hygienist = new DentalHygienist();
		hygienist.setId(1L);
		hygienist.setLastName("testing");
		when(service.save(hygienist)).thenReturn(hygienist);
		
	    ObjectMapper mapper = new ObjectMapper();
	    
	    this.mockMvc.perform(put("/hygienist/save").contentType(MediaType.APPLICATION_JSON_VALUE).content(mapper.writer().writeValueAsString(hygienist)))
	    	.andExpect(status().isBadRequest())
	    	.andExpect(content().json("{'firstName' : 'Please enter a valid first name'}"));
	}
	
	@Test
	void test_Save_ReturnsCorrectResponse_WhenGivenEmptyLastName() throws Exception {
		
		DentalHygienist hygienist = new DentalHygienist();
		hygienist.setId(1L);
		hygienist.setFirstName("test");
		hygienist.setLastName("");
		when(service.save(hygienist)).thenReturn(hygienist);
		
	    ObjectMapper mapper = new ObjectMapper();
	    
	    this.mockMvc.perform(put("/hygienist/save").contentType(MediaType.APPLICATION_JSON_VALUE).content(mapper.writer().writeValueAsString(hygienist)))
	    	.andExpect(status().isBadRequest())
	    	.andExpect(content().json("{'lastName' : 'Please enter a valid last name'}"));
	}
	
	@Test
	void test_Save_ReturnsCorrectResponse_WhenGivenNoLastName() throws Exception {
		
		DentalHygienist hygienist = new DentalHygienist();
		hygienist.setId(1L);
		hygienist.setFirstName("test");
		when(service.save(hygienist)).thenReturn(hygienist);
		
	    ObjectMapper mapper = new ObjectMapper();
	    
	    this.mockMvc.perform(put("/hygienist/save").contentType(MediaType.APPLICATION_JSON_VALUE).content(mapper.writer().writeValueAsString(hygienist)))
	    	.andExpect(status().isBadRequest())
	    	.andExpect(content().json("{'lastName' : 'Please enter a valid last name'}"));
	}

}
