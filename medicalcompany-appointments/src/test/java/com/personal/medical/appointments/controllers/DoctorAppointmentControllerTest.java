package com.personal.medical.appointments.controllers;


import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
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
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.personal.medical.appointments.model.DoctorAppointment;
import com.personal.medical.appointments.services.DoctorAppointmentService;

@AutoConfigureMockMvc
@WebMvcTest(DoctorAppointmentController.class)
class DoctorAppointmentControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	private DoctorAppointmentController controller;
	
	@Autowired
	private RestTemplate restTemplate;
	
	private MockRestServiceServer mockServer;
	
	private ObjectMapper mapper;
	
	@MockBean
	private DoctorAppointmentService service;
	
	@BeforeEach
	void setUp() throws Exception {
		this.controller = new DoctorAppointmentController(service);
		this.mockServer = MockRestServiceServer.createServer(restTemplate);
	    mapper = new ObjectMapper();

	}

	@Test
	void test_ControllerNotNull() {
		assertThat(controller, notNullValue());
	}
	
	@Test
	void test_FindById_ReturnsCorrectResponseWhenEntityForIdPresent() throws Exception {
		
		DoctorAppointment appointment = new DoctorAppointment();
		appointment.setId(1L);
		appointment.setDescription("test");
		appointment.setDate(LocalDateTime.of(2020, 1, 2, 5, 6));
		when(service.findById(1L)).thenReturn(Optional.of(appointment));
		
		mockMvc.perform(get("/doctorappointments/1"))
		.andExpect(status().isOk())
		.andExpect(content().json("{'id': 1, 'description': 'test', 'date' : '2020-01-02T05:06:00'}"));
	}

	@Test
	void test_FindById_ReturnsCorrectResponseWithNoEntityForIdPresent() throws Exception {
		mockMvc.perform(get("/doctorappointments/10"))
		.andExpect(status().isNotFound());
	}
	
	@Test
	void test_Delete_ReturnsCorrectResponseWhenEntityForIdPresent() throws Exception {
		DoctorAppointment appointment = new DoctorAppointment();
		appointment.setId(1L);
		
		when(service.findById(1L)).thenReturn(Optional.of(appointment));
		mockMvc.perform(delete("/doctorappointments/delete/1"))
		.andExpect(status().isOk());
	}
	
	@Test
	void test_Delete_ReturnsCorrectResponseWithNoEntityForIdPresent() throws Exception {
		mockMvc.perform(delete("/doctorappointments/delete/10"))
		.andExpect(status().isNotFound());
	}
	
	@Test
	void test_Save_ReturnsCorrectResponse_WhenGivenValidEntity() throws Exception {
		
		DoctorAppointment appointment = new DoctorAppointment();
		appointment.setId(1L);
		appointment.setDescription("test");
		appointment.setDate(LocalDateTime.of(2020, 1, 2, 5, 6));
		appointment.setDoctorId(1L);
		appointment.setPatientId(1L);
		when(service.save(appointment)).thenReturn(appointment);
	    ObjectMapper mapper = new ObjectMapper();
	    
	    
		mockServer.expect(ExpectedCount.once(), 
		          requestTo("http://localhost:8080/patient/" + appointment.getPatientId()))
		          .andExpect(method(HttpMethod.GET))
		          .andRespond(withStatus(HttpStatus.OK)
		        );
		
		mockServer.expect(ExpectedCount.once(), 
		          requestTo("http://localhost:8080/doctor/" + appointment.getDoctorId()))
		          .andExpect(method(HttpMethod.GET))
		          .andRespond(withStatus(HttpStatus.OK)
		        );
		
	    mockMvc.perform(put("/doctorappointments/save").contentType(MediaType.APPLICATION_JSON_VALUE).content(mapper.writer().writeValueAsString(appointment)))
	    	.andExpect(status().isOk());
		
	    mockServer.verify();
	}
	
	@Test
	void test_Save_ReturnsCorrectResponse_WhenPatientDoesNotExist() throws Exception {
		
		DoctorAppointment appointment = new DoctorAppointment();
		appointment.setId(1L);
		appointment.setDescription("test");
		appointment.setDate(LocalDateTime.of(2020, 1, 2, 5, 6));
		appointment.setDoctorId(1L);
		appointment.setPatientId(1L);
		when(service.save(appointment)).thenReturn(appointment);
		
		mockServer.expect(ExpectedCount.once(), 
		          requestTo("http://localhost:8080/patient/" + appointment.getPatientId()))
		          .andExpect(method(HttpMethod.GET))
		          .andRespond(withStatus(HttpStatus.NOT_FOUND)
		        );
	    
	    mockMvc.perform(put("/doctorappointments/save").contentType(MediaType.APPLICATION_JSON_VALUE).content(mapper.writer().writeValueAsString(appointment)))
	    	.andExpect(status().isNotFound())
	    	.andExpect(content().string("Patient id not found"));
	    mockServer.verify();

	}
	
	@Test
	void test_Save_ReturnsCorrectResponse_WhenDoctorDoesNotExist() throws Exception {
		
		DoctorAppointment appointment = new DoctorAppointment();
		appointment.setId(1L);
		appointment.setDescription("test");
		appointment.setDate(LocalDateTime.of(2020, 1, 2, 5, 6));
		appointment.setDoctorId(1L);
		appointment.setPatientId(1L);
		when(service.save(appointment)).thenReturn(appointment);
		
		mockServer.expect(ExpectedCount.once(), 
		          requestTo("http://localhost:8080/patient/" + appointment.getPatientId()))
		          .andExpect(method(HttpMethod.GET))
		          .andRespond(withStatus(HttpStatus.OK)
		        );
		
		mockServer.expect(ExpectedCount.once(), 
		          requestTo("http://localhost:8080/doctor/" + appointment.getPatientId()))
		          .andExpect(method(HttpMethod.GET))
		          .andRespond(withStatus(HttpStatus.NOT_FOUND)
		        );
	    
	    this.mockMvc.perform(put("/doctorappointments/save").contentType(MediaType.APPLICATION_JSON_VALUE).content(mapper.writer().writeValueAsString(appointment)))
	    	.andExpect(status().isNotFound())
	    	.andExpect(content().string("Doctor id not found"));
	    mockServer.verify();

	}
}
