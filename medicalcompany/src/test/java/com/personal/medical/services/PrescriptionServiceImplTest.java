package com.personal.medical.services;

import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.personal.medical.model.Prescription;
import com.personal.medical.repository.PrescriptionRepository;

@SpringBootTest
class PrescriptionServiceImplTest {

	PrescriptionService prescriptionService;
	
	@Mock
	Prescription prescriptionMock;
	
	@Mock
	PrescriptionRepository prescriptionRepository;
	
	@BeforeEach
	void setUp() throws Exception {
		prescriptionService = new PrescriptionServiceImpl(prescriptionRepository);
	}

	@Test
	void test_FindById_ReturnsPrescriptionMock_WhenCalledWithId1() {
		//Arrange
		when(prescriptionRepository.findById(1L)).thenReturn(Optional.of(prescriptionMock));
		//Assert
		Assertions.assertEquals(prescriptionService.findById(1L).get(), prescriptionMock);
	}
	
	@Test
	void test_FindById_ReturnsEmptyOptional_WhenCalledWithId5() {
		//Assert
		Assertions.assertTrue(prescriptionService.findById(5L).isEmpty());
	}
	
}
