package com.personal.medical.appointments.controllers;


import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
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
import com.personal.medical.appointments.model.DentalAppointment;
import com.personal.medical.appointments.services.DentalAppointmentService;

@WebMvcTest(DentalAppointmentController.class)
class DentalAppointmentControllerTest {

	@Autowired
	private MockMvc mockMvc;

	private DentalAppointmentController controller;
	
	private ObjectMapper mapper;
	
	@MockBean
	private DentalAppointmentService service;
	
	@BeforeEach
	void setUp() throws Exception {
		controller = new DentalAppointmentController(service);
	    mapper = new ObjectMapper();
	}
	
	@Test
	void test_NotNull() {
		assertThat(controller, notNullValue());
		assertThat(mockMvc, notNullValue());	
		}
	
	@Test
	void test_FindById_ReturnsCorrectResponseWhenEntityForIdPresent() throws Exception {
		
		DentalAppointment appointment = new DentalAppointment();
		appointment.setId(1L);
		appointment.setDescription("test");
		appointment.setDate(LocalDateTime.of(2020, 1, 2, 5, 6));
		when(service.findById(1L)).thenReturn(Optional.of(appointment));
		
		mockMvc.perform(get("/dentalappointments/1"))
		.andExpect(status().isOk())
		.andExpect(content().json("{'id': 1, 'description': 'test', 'date' : '2020-01-02T05:06:00'}"));
	}

	@Test
	void test_FindById_ReturnsCorrectResponseWithNoEntityForIdPresent() throws Exception {
		mockMvc.perform(get("/dentalappointments/10"))
		.andExpect(status().isNotFound());
	}
	
	@Test
	void test_Delete_ReturnsCorrectResponseWhenEntityForIdPresent() throws Exception {
		DentalAppointment appointment = new DentalAppointment();
		appointment.setId(1L);
		
		when(service.findById(1L)).thenReturn(Optional.of(appointment));
		mockMvc.perform(delete("/dentalappointments/delete/1"))
		.andExpect(status().isOk());
	}
	
	@Test
	void test_Delete_ReturnsCorrectResponseWithNoEntityForIdPresent() throws Exception {
		mockMvc.perform(delete("/dentalappointments/delete/10"))
		.andExpect(status().isNotFound());
	}
	
	@Test
	void test_Save_ReturnsCorrectResponse_WhenGivenValidEntity() throws Exception {
		
		DentalAppointment appointment = new DentalAppointment();
		appointment.setId(1L);
		appointment.setDescription("test");
		appointment.setDate(LocalDateTime.of(2020, 1, 2, 5, 6));
		appointment.setDentistId(1L);
		appointment.setPatientId(1L);
		when(service.checkIfPatientOrDentistExists(appointment)).thenReturn(true);
		when(service.save(appointment)).thenReturn(appointment);
	    ObjectMapper mapper = new ObjectMapper();
	    
	    mockMvc.perform(put("/dentalappointments/save").contentType(MediaType.APPLICATION_JSON_VALUE).content(mapper.writer().writeValueAsString(appointment)))
	    	.andExpect(status().isOk());
	}
	
	@Test
	void test_Save_ReturnsCorrectResponse_WhenPatientOrDentistDoesNotExist() throws Exception {
		
		DentalAppointment appointment = new DentalAppointment();
		appointment.setId(1L);
		appointment.setDescription("test");
		appointment.setDate(LocalDateTime.of(2020, 1, 2, 5, 6));
		appointment.setDentistId(1L);
		appointment.setPatientId(1L);
		when(service.save(appointment)).thenReturn(appointment);
		when(service.checkIfPatientOrDentistExists(appointment)).thenReturn(false);
	    
	    mockMvc.perform(put("/dentalappointments/save").contentType(MediaType.APPLICATION_JSON_VALUE).content(mapper.writer().writeValueAsString(appointment)))
	    	.andExpect(status().isNotFound());
	}
	
	@Test
	void test_Save_ReturnsCorrectResponse_WhenMainApplicationIsDown() throws Exception {
		
		DentalAppointment appointment = new DentalAppointment();
		appointment.setId(1L);
		appointment.setDescription("test");
		appointment.setDate(LocalDateTime.of(2020, 1, 2, 5, 6));
		appointment.setDentistId(1L);
		appointment.setPatientId(1L);
		when(service.save(appointment)).thenReturn(appointment);
		when(service.checkIfPatientOrDentistExists(appointment)).thenThrow(ResourceAccessException.class);
	    
	    mockMvc.perform(put("/dentalappointments/save").contentType(MediaType.APPLICATION_JSON_VALUE).content(mapper.writer().writeValueAsString(appointment)))
	    	.andExpect(status().isServiceUnavailable());
	}
	
}
