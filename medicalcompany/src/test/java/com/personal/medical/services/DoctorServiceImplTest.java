package com.personal.medical.services;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import com.personal.medical.model.Doctor;
import com.personal.medical.repository.DoctorRepository;

@SpringBootTest
class DoctorServiceImplTest {

	private DoctorService service;
	
	@Autowired
	private RestTemplate restTemplate;
	
	private MockRestServiceServer mockServer;
	
	@Mock
	private DoctorRepository repository;
	
	@Mock
	private Doctor doctorMock;
	
	@BeforeEach
	void setUp() throws Exception {
		service = new DoctorServiceImpl(repository, restTemplate);
		mockServer = MockRestServiceServer.createServer(restTemplate); 
	}

	@Test
	void test_ServiceNotNull() {
		assertThat(service, not(equalTo(null)));
	}
	
	@Test
	void test_RestTemplateNotNull() {
		assertThat(restTemplate, not(equalTo(null)));
	}
	
	@Test
	void test_FindById_ReturnsMock_WhenCalledWithId1() {
		//Arrange
		when(repository.findById(1L)).thenReturn(Optional.of(doctorMock));
		//Assert
		Assertions.assertEquals(doctorMock, service.findById(1L).get());
	}
	
	@Test
	void test_FindById_ReturnsEmptyOptional_WhenCalledWithId5() {
		//Assert
		Assertions.assertTrue(service.findById(5L).isEmpty());
	}
	
	@Test
	void test_Delete_CallsDoctorAppointmentsEndpointSuccessfully_WhenGivenMockDoctor() {
		
		Doctor doctor = new Doctor();
		doctor.setId(1L);
		
		mockServer.expect(ExpectedCount.once(), 
		          requestTo("http://localhost:8081/doctorappointments/delete/doctor/" + doctor.getId()))
		          .andExpect(method(HttpMethod.DELETE))
		          .andRespond(withStatus(HttpStatus.OK)
		        );
		
		service.delete(doctor);
		mockServer.verify();

	}

}
