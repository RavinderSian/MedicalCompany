package com.personal.medical.services;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.personal.medical.model.Patient;
import com.personal.medical.repository.PatientRepository;

@SpringBootTest
class PatientServiceImplTest {
	
	PatientService patientService;
	
	@Mock
	Patient patientMock;
	
	@Mock
	PatientRepository patientRepository;
	
	@BeforeEach
	void setUp() throws Exception {
		patientService = new PatientServiceImpl(patientRepository);
	}

	@Test
	void test_FindById_ReturnsEmptyOptional_WhenCalledWithId5() {
		//Assert
		Optional<Patient> patientOptional = patientService.findById(5L);
		Assertions.assertTrue(patientOptional.isEmpty());
	}
	
	@Test
	void test_UpdateFirstName_UpdatesFirstNameCorrectly_WhenGivenStringNewName() {
		//Arrange
		Patient patient = new Patient();
		patient.setFirstName("test");
		//Act
		patientService.updateFirstName(patient, "new name");
		//Assert
		Assertions.assertEquals(patient.getFirstName(), "new name");
	}
}
