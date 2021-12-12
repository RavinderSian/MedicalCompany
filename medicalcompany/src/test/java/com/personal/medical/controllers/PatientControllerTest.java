package com.personal.medical.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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
import org.springframework.web.client.ResourceAccessException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.personal.medical.model.Patient;
import com.personal.medical.services.PatientService;

@WebMvcTest(PatientController.class)
class PatientControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	private PatientController controller;

	@MockBean
	private PatientService patientService;
	
	@BeforeEach
	void setUp() {
		controller = new PatientController(patientService);
	}
	
	@Test
	void contextLoads() throws Exception {
		assertThat(controller).isNotNull();
	}
	
	@Test
	void test_GetById_ReturnsCorrectStatusAndPatient_WhenGivenId1() throws Exception {
		
		Patient patient = new Patient();
		patient.setPatientId(1L);
		patient.setFirstName("rav");
		
		when(patientService.findById(1L)).thenReturn(Optional.of(patient));
		
		this.mockMvc.perform(get("/patient/1")).andDo(print())
		.andExpect(status().isOk())
		.andExpect(content().json("{'patientId': 1, 'firstName': 'rav'}")); 
	}
	
	@Test
	void test_Delete_ReturnsCorrectResponseWhenEntityForIdPresent() throws Exception {
		Patient patient = new Patient();
		patient.setPatientId(1L);
		
		when(patientService.findById(1L)).thenReturn(Optional.of(patient));
		mockMvc.perform(delete("/patient/delete/1"))
		.andExpect(status().isOk());
	}
	
	@Test
	void test_Delete_ReturnsCorrectResponseWithNoEntityForIdPresent() throws Exception {
		mockMvc.perform(delete("/patient/delete/10"))
		.andExpect(status().isNotFound());
	}
	
	@Test
	void test_Delete_ReturnsCorrectResponse_WhenAppointmentsServiceIsDown() throws Exception {
		Patient patient = new Patient();
		patient.setPatientId(1L);
		when(patientService.findById(1L)).thenReturn(Optional.of(patient));
		doThrow(ResourceAccessException.class).when(patientService).delete(patient);
		
		mockMvc.perform(delete("/patient/delete/1"))
		.andExpect(status().isServiceUnavailable());
	}
	
	@Test
	void test_GetById_ReturnsStringNoDataFoundForId5_WhenGivenIdWithNoData() throws Exception {
		
		this.mockMvc.perform(get("/patient/5"))
		.andExpect(status().isNotFound());
	}
	
	@Test
	void test_Save_ReturnsCorrectStatusAndPatient_WhenGivenValidPatient() throws Exception {
		
		Patient patient = new Patient();
		patient.setPatientId(1L);
		patient.setFirstName("rav");
		patient.setLastName("sian");
		
		when(patientService.save(patient)).thenReturn(patient);
		
	    ObjectMapper mapper = new ObjectMapper();
	    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
	    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
	    String requestJson = ow.writeValueAsString(patient);
		
		this.mockMvc.perform(put("/patient/save").contentType(MediaType.APPLICATION_JSON_VALUE).content(requestJson))
		.andExpect(status().isOk())
		.andExpect(content().json("{'patientId': 1, 'firstName': 'rav', 'lastName':'sian'}"));
	}
	
	@Test
	void test_Save_ReturnsCorrectStatusAndPatient_WhenGivenInValidPatient() throws Exception {
		
		Patient patient = new Patient();
		patient.setPatientId(1L);
		patient.setLastName("testing");
		
		when(patientService.save(patient)).thenReturn(patient);
		
	    ObjectMapper mapper = new ObjectMapper();
	    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
	    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
	    String requestJson = ow.writeValueAsString(patient);
		
		this.mockMvc.perform(put("/patient/save").contentType(MediaType.APPLICATION_JSON_VALUE).content(requestJson))
		.andExpect(status().isBadRequest())
		.andExpect(content().string("{\"firstName\":\"Please enter a valid first name\"}"));

	}
	
	@Test
	void test_UpdateFirstName_CorrectlyUpdatesFirstName_WhenGivenFirstNameJohnAndId1() throws Exception {
		
		Patient patient = new Patient();
		patient.setPatientId(1L);
		patient.setFirstName("rav");
		
		when(patientService.findById(1L)).thenReturn(Optional.of(patient));
		patient.setFirstName("John");
		when(patientService.updateFirstName(patient, "John")).thenReturn(patient);
		
		this.mockMvc.perform(patch("/patient/1/updatefirstname").contentType(MediaType.APPLICATION_JSON_VALUE).content("John"))
		.andExpect(status().isOk())
		.andExpect(content().json("{'patientId': 1, 'firstName': 'John'}"));
	}
	
	@Test
	void test_UpdateFirstName_ReturnsNoDataForId5_WhenGivenFirstNameJohnAndId5() throws Exception {
		this.mockMvc.perform(patch("/patient/5/updatefirstname").contentType(MediaType.APPLICATION_JSON_VALUE).content("John"))
		.andExpect(status().isNotFound());
	}
	
}
