package com.personal.medical.appointments.services;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
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

import com.personal.medical.appointments.model.DentalAppointment;
import com.personal.medical.appointments.repositories.DentalAppointmentRepository;

@SpringBootTest
class DentalAppointmentServiceImplTest {

	private DentalAppointmentService service;
	
	@Autowired
	private RestTemplate restTemplate;
	
	private MockRestServiceServer mockServer;
	
	@Mock
	private DentalAppointmentRepository repository;
	
	@Mock
	private DentalAppointment dentalAppointmentMock;
	
	@BeforeEach
	void setUp() throws Exception {
		service = new DentalAppointmentServiceImpl(repository, restTemplate);
		mockServer = MockRestServiceServer.createServer(restTemplate);
	}

	@Test
	void test_ServiceNotNull() {
		assertThat(service, not(equalTo(null)));
	}
	
	@Test
	void test_FindById_ReturnsMock_WhenCalledWithId1() {
		//Arrange
		when(repository.findById(1L)).thenReturn(Optional.of(dentalAppointmentMock));
		//Assert
		Assertions.assertEquals(dentalAppointmentMock, service.findById(1L).get());
	}
	
	@Test
	void test_FindById_ReturnsEmptyOptional_WhenCalledWithId5() {
		//Assert
		Assertions.assertTrue(service.findById(5L).isEmpty());
	}
	
	@Test
	void test_checkIfPatientOrDentistExists_ReturnsFalse_WhenNoPatientExists() {
		
		dentalAppointmentMock.setPatientId(5L);
		
		mockServer.expect(ExpectedCount.once(), 
		          requestTo("http://localhost:8080/dentalpatient/" + dentalAppointmentMock.getPatientId()))
		          .andExpect(method(HttpMethod.GET))
		          .andRespond(withStatus(HttpStatus.NOT_FOUND)
		        );
		
		boolean result = service.checkIfPatientOrDentistExists(dentalAppointmentMock);
		
		mockServer.verify();
		
		assertThat(result, is(equalTo(false)));
	}
	
	@Test
	void test_checkIfPatientOrDentistExists_ReturnsFalse_WhenNoDentistExists() {
		
		dentalAppointmentMock.setPatientId(1L);
		dentalAppointmentMock.setDentistId(1L);

		
		mockServer.expect(ExpectedCount.once(), 
		          requestTo("http://localhost:8080/dentalpatient/" + dentalAppointmentMock.getPatientId()))
		          .andExpect(method(HttpMethod.GET))
		          .andRespond(withStatus(HttpStatus.OK)
		        );
		
		mockServer.expect(ExpectedCount.once(), 
		          requestTo("http://localhost:8080/dentist/" + dentalAppointmentMock.getDentistId()))
		          .andExpect(method(HttpMethod.GET))
		          .andRespond(withStatus(HttpStatus.NOT_FOUND)
		        );
		
		boolean result = service.checkIfPatientOrDentistExists(dentalAppointmentMock);
		mockServer.verify();
		
		assertThat(result, is(equalTo(false)));
	}
	
	@Test
	void test_checkIfPatientOrDentistExists_ReturnsTrue_WhenPatientAndDentistExists() {
		
		dentalAppointmentMock.setPatientId(1L);
		dentalAppointmentMock.setDentistId(1L);
		
		mockServer.expect(ExpectedCount.once(), 
		          requestTo("http://localhost:8080/dentalpatient/" + dentalAppointmentMock.getPatientId()))
		          .andExpect(method(HttpMethod.GET))
		          .andRespond(withStatus(HttpStatus.OK)
		        );
		
		mockServer.expect(ExpectedCount.once(), 
		          requestTo("http://localhost:8080/dentist/" + dentalAppointmentMock.getDentistId()))
		          .andExpect(method(HttpMethod.GET))
		          .andRespond(withStatus(HttpStatus.OK)
		        );
		
		boolean result = service.checkIfPatientOrDentistExists(dentalAppointmentMock);
		mockServer.verify();
		
		assertThat(result, is(equalTo(true)));
	}

}
