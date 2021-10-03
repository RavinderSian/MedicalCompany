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
import com.personal.medical.model.Dentist;
import com.personal.medical.services.DentistService;

@AutoConfigureMockMvc
@WebMvcTest(DentistController.class)
class DentistControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	private DentistController controller;
	
	@MockBean
	private DentistService service;
	
	@BeforeEach
	void setUp() throws Exception {
		this.controller = new DentistController(service);
	}

	@Test
	void test_ControllerNotNull() {
		assertThat(controller, notNullValue());
	}
	
	@Test
	void test_FindById_ReturnsCorrectResponseWhenEntityForIdPresent() throws Exception {
		
		Dentist dentist = new Dentist();
		dentist.setId(1L);
		dentist.setFirstName("test");
		dentist.setLastName("testing");
		when(service.findById(1L)).thenReturn(Optional.of(dentist));
		
		mockMvc.perform(get("/dentist/1"))
		.andExpect(status().isOk())
		.andExpect(content().json("{'id': 1, 'firstName': 'test', 'lastName' : 'testing'}"));
	}

	@Test
	void test_FindById_ReturnsCorrectResponseWithNoEntityForIdPresent() throws Exception {
		mockMvc.perform(get("/dentist/10"))
		.andExpect(status().isNotFound());
	}
	
	@Test
	void test_Delete_ReturnsCorrectResponseWhenEntityForIdPresent() throws Exception {
		Dentist dentist = new Dentist();
		dentist.setId(1L);
		
		when(service.findById(1L)).thenReturn(Optional.of(dentist));
		mockMvc.perform(delete("/dentist/delete/1"))
		.andExpect(status().isOk());
	}
	
	@Test
	void test_Delete_ReturnsCorrectResponseWithNoEntityForIdPresent() throws Exception {
		mockMvc.perform(delete("/dentist/delete/10"))
		.andExpect(status().isNotFound());
	}
	
	@Test
	void test_Save_ReturnsCorrectResponse_WhenGivenValidEntity() throws Exception {
		
		Dentist dentist = new Dentist();
		dentist.setId(1L);
		dentist.setFirstName("test");
		dentist.setLastName("testing");
		when(service.save(dentist)).thenReturn(dentist);
		
	    ObjectMapper mapper = new ObjectMapper();
	    
	    this.mockMvc.perform(put("/dentist/save").contentType(MediaType.APPLICATION_JSON_VALUE).content(mapper.writer().writeValueAsString(dentist)))
	    	.andExpect(status().isOk());
	}
	
	@Test
	void test_Save_ReturnsCorrectResponse_WhenGivenEmptyFirstName() throws Exception {
		
		Dentist dentist = new Dentist();
		dentist.setId(1L);
		dentist.setFirstName("");
		dentist.setLastName("testing");
		when(service.save(dentist)).thenReturn(dentist);
		
	    ObjectMapper mapper = new ObjectMapper();
	    
	    this.mockMvc.perform(put("/dentist/save").contentType(MediaType.APPLICATION_JSON_VALUE).content(mapper.writer().writeValueAsString(dentist)))
	    	.andExpect(status().isBadRequest())
	    	.andExpect(content().json("{'firstName' : 'Please enter a valid first name'}"));
	}
	
	@Test
	void test_Save_ReturnsCorrectResponse_WhenGivenNoFirstName() throws Exception {
		
		Dentist dentist = new Dentist();
		dentist.setId(1L);
		dentist.setLastName("testing");
		when(service.save(dentist)).thenReturn(dentist);
		
	    ObjectMapper mapper = new ObjectMapper();
	    
	    this.mockMvc.perform(put("/dentist/save").contentType(MediaType.APPLICATION_JSON_VALUE).content(mapper.writer().writeValueAsString(dentist)))
	    	.andExpect(status().isBadRequest())
	    	.andExpect(content().json("{'firstName' : 'Please enter a valid first name'}"));
	}
	
	@Test
	void test_Save_ReturnsCorrectResponse_WhenGivenEmptyLastName() throws Exception {
		
		Dentist dentist = new Dentist();
		dentist.setId(1L);
		dentist.setFirstName("test");
		dentist.setLastName("");
		when(service.save(dentist)).thenReturn(dentist);
		
	    ObjectMapper mapper = new ObjectMapper();
	    
	    this.mockMvc.perform(put("/dentist/save").contentType(MediaType.APPLICATION_JSON_VALUE).content(mapper.writer().writeValueAsString(dentist)))
	    	.andExpect(status().isBadRequest())
	    	.andExpect(content().json("{'lastName' : 'Please enter a valid last name'}"));
	}
	
	@Test
	void test_Save_ReturnsCorrectResponse_WhenGivenNoLastName() throws Exception {
		
		Dentist dentist = new Dentist();
		dentist.setId(1L);
		dentist.setFirstName("test");
		when(service.save(dentist)).thenReturn(dentist);
		
	    ObjectMapper mapper = new ObjectMapper();
	    
	    this.mockMvc.perform(put("/dentist/save").contentType(MediaType.APPLICATION_JSON_VALUE).content(mapper.writer().writeValueAsString(dentist)))
	    	.andExpect(status().isBadRequest())
	    	.andExpect(content().json("{'lastName' : 'Please enter a valid last name'}"));
	}


}
