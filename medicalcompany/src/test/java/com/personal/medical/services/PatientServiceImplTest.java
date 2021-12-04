package com.personal.medical.services;

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

import com.personal.medical.model.Patient;
import com.personal.medical.repository.PatientRepository;

@SpringBootTest
class PatientServiceImplTest {
	
	@Autowired
	private RestTemplate restTemplate;
	
	private MockRestServiceServer mockServer;
	
	private PatientService service;
	
	@Mock
	private Patient patientMock;
	
	@Mock
	private PatientRepository patientRepository;
	
	@BeforeEach
	void setUp() throws Exception {
		service = new PatientServiceImpl(patientRepository, restTemplate);
		mockServer = MockRestServiceServer.createServer(restTemplate);
	}

	@Test
	void test_FindById_ReturnsEmptyOptional_WhenCalledWithId5() {
		//Assert
		Optional<Patient> patientOptional = service.findById(5L);
		Assertions.assertTrue(patientOptional.isEmpty());
	}
	
	@Test
	void test_UpdateFirstName_UpdatesFirstNameCorrectly_WhenGivenStringNewName() {
		//Arrange
		Patient patient = new Patient();
		patient.setFirstName("test");
		//Act
		service.updateFirstName(patient, "new name");
		//Assert
		Assertions.assertEquals(patient.getFirstName(), "new name");
	}
	
	@Test
	void test_Delete_CallsDoctorAppointmentsEndpointSuccessfully_WhenGivenMockPatient() {
		
		Patient patient = new Patient();
		patient.setPatientId(1L);
		
		mockServer.expect(ExpectedCount.once(), 
		          requestTo("http://localhost:8081/doctorappointments/delete/patient/" + patient.getPatientId()))
		          .andExpect(method(HttpMethod.DELETE))
		          .andRespond(withStatus(HttpStatus.OK)
		        );
		
		service.delete(patient);
		mockServer.verify();

	}
}
