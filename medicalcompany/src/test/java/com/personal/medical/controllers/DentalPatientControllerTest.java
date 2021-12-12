package com.personal.medical.controllers;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.doThrow;
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
import org.springframework.web.client.ResourceAccessException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.personal.medical.model.DentalPatient;
import com.personal.medical.services.DentalPatientService;

@AutoConfigureMockMvc
@WebMvcTest(DentalPatientController.class)
class DentalPatientControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	private DentalPatientController controller;
	
	@MockBean
	private DentalPatientService service;
	
	@BeforeEach
	void setUp() throws Exception {
		this.controller = new DentalPatientController(service);
	}

	@Test
	void test_ControllerNotNull() {
		assertThat(controller, notNullValue());
	}
	
	@Test
	void test_FindById_ReturnsCorrectResponseWhenEntityForIdPresent() throws Exception {
		
		DentalPatient patient = new DentalPatient();
		patient.setId(1L);
		patient.setFirstName("test");
		patient.setLastName("testing");
		when(service.findById(1L)).thenReturn(Optional.of(patient));
		
		mockMvc.perform(get("/dentalpatient/1"))
		.andExpect(status().isOk())
		.andExpect(content().json("{'id': 1, 'firstName': 'test', 'lastName' : 'testing'}"));
	}

	@Test
	void test_FindById_ReturnsCorrectResponseWithNoEntityForIdPresent() throws Exception {
		mockMvc.perform(get("/dentalpatient/10"))
		.andExpect(status().isNotFound());
	}
	
	@Test
	void test_Delete_ReturnsCorrectResponseWhenEntityForIdPresent() throws Exception {
		DentalPatient patient = new DentalPatient();
		patient.setId(1L);
		
		when(service.findById(1L)).thenReturn(Optional.of(patient));
		mockMvc.perform(delete("/dentalpatient/delete/1"))
		.andExpect(status().isOk());
	}
	
	@Test
	void test_Delete_ReturnsCorrectResponseWithNoEntityForIdPresent() throws Exception {
		mockMvc.perform(delete("/dentalpatient/delete/10"))
		.andExpect(status().isNotFound());
	}
	
	@Test
	void test_Delete_ReturnsCorrectResponse_WhenAppointmentsServiceIsDown() throws Exception {
		DentalPatient dentalPatient = new DentalPatient();
		dentalPatient.setId(1L);
		when(service.findById(1L)).thenReturn(Optional.of(dentalPatient));
		doThrow(ResourceAccessException.class).when(service).delete(dentalPatient);
		
		mockMvc.perform(delete("/dentalpatient/delete/1"))
		.andExpect(status().isServiceUnavailable());
	}
	
	@Test
	void test_Save_ReturnsCorrectResponse_WhenGivenValidEntity() throws Exception {
		
		DentalPatient patient = new DentalPatient();
		patient.setId(1L);
		patient.setFirstName("test");
		patient.setLastName("testing");
		when(service.save(patient)).thenReturn(patient);
		
	    ObjectMapper mapper = new ObjectMapper();
	    
	    this.mockMvc.perform(put("/dentalpatient/save").contentType(MediaType.APPLICATION_JSON_VALUE).content(mapper.writer().writeValueAsString(patient)))
	    	.andExpect(status().isOk());
	}
	
	@Test
	void test_Save_ReturnsCorrectResponse_WhenGivenEmptyFirstName() throws Exception {
		
		DentalPatient patient = new DentalPatient();
		patient.setId(1L);
		patient.setFirstName("");
		patient.setLastName("testing");
		when(service.save(patient)).thenReturn(patient);
		
	    ObjectMapper mapper = new ObjectMapper();
	    
	    this.mockMvc.perform(put("/dentalpatient/save").contentType(MediaType.APPLICATION_JSON_VALUE).content(mapper.writer().writeValueAsString(patient)))
	    	.andExpect(status().isBadRequest())
	    	.andExpect(content().json("{'firstName' : 'Please enter a valid first name'}"));
	}
	
	@Test
	void test_Save_ReturnsCorrectResponse_WhenGivenNoFirstName() throws Exception {
		
		DentalPatient patient = new DentalPatient();
		patient.setId(1L);
		patient.setLastName("testing");
		when(service.save(patient)).thenReturn(patient);
		
	    ObjectMapper mapper = new ObjectMapper();
	    
	    this.mockMvc.perform(put("/dentalpatient/save").contentType(MediaType.APPLICATION_JSON_VALUE).content(mapper.writer().writeValueAsString(patient)))
	    	.andExpect(status().isBadRequest())
	    	.andExpect(content().json("{'firstName' : 'Please enter a valid first name'}"));
	}
	
	@Test
	void test_Save_ReturnsCorrectResponse_WhenGivenEmptyLastName() throws Exception {
		
		DentalPatient patient = new DentalPatient();
		patient.setId(1L);
		patient.setFirstName("test");
		patient.setLastName("");
		when(service.save(patient)).thenReturn(patient);
		
	    ObjectMapper mapper = new ObjectMapper();
	    
	    this.mockMvc.perform(put("/dentalpatient/save").contentType(MediaType.APPLICATION_JSON_VALUE).content(mapper.writer().writeValueAsString(patient)))
	    	.andExpect(status().isBadRequest())
	    	.andExpect(content().json("{'lastName' : 'Please enter a valid last name'}"));
	}
	
	@Test
	void test_Save_ReturnsCorrectResponse_WhenGivenNoLastName() throws Exception {
		
		DentalPatient patient = new DentalPatient();
		patient.setId(1L);
		patient.setFirstName("test");
		when(service.save(patient)).thenReturn(patient);
		
	    ObjectMapper mapper = new ObjectMapper();
	    
	    this.mockMvc.perform(put("/dentalpatient/save").contentType(MediaType.APPLICATION_JSON_VALUE).content(mapper.writer().writeValueAsString(patient)))
	    	.andExpect(status().isBadRequest())
	    	.andExpect(content().json("{'lastName' : 'Please enter a valid last name'}"));
	}

}
