package com.personal.medical.services;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
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
	public void test_Save_CallsRepositorySave_WhenCalled() {
		//Act
		prescriptionService.save(prescriptionMock);
		//Assert
		verify(prescriptionRepository, times(1)).save(prescriptionMock);
	}
	
	@Test
	public void test_Save_ReturnsCorrectPrescription_WhenGivenPrescriptionMock() {
		//Arrange
		when(prescriptionRepository.save(prescriptionMock)).thenReturn(prescriptionMock);
		//Assert
		Assertions.assertEquals(prescriptionMock, prescriptionService.save(prescriptionMock));
	}
	
	@Test
	public void test_Delete_CallsRepositoryDelete_WhenCalled() {
		//Act
		prescriptionService.delete(prescriptionMock);
		//Assert
		verify(prescriptionRepository, times(1)).delete(prescriptionMock);
	}
	
	@Test
	public void test_FindAll_CallsRepositoryFindAll_WhenCalled() {
		//Act
		prescriptionService.findAll();
		//Assert
		verify(prescriptionRepository, times(1)).findAll();
	}
	
	@Test
	public void test_FindAll_ReturnsListOfSize0_WhenGivenMockPrescription() {
		//Arrange
		when(prescriptionRepository.findAll()).thenReturn(new ArrayList<>());
		//Assert
		Assertions.assertEquals(0, prescriptionService.findAll().size());
	}
	
	@Test
	public void test_FindAll_ReturnsListOfSize1_WhenGivenMockPrescription() {
		//Arrange
		when(prescriptionRepository.findAll()).thenReturn(new ArrayList<>(Arrays.asList(prescriptionMock)));
		//Assert
		Assertions.assertEquals(1, prescriptionService.findAll().size());
	}
	
	@Test
	public void test_FindAll_ReturnsListOfSize2_WhenGivenMockPrescription() {
		//Arrange
		when(prescriptionRepository.findAll()).thenReturn(new ArrayList<>(Arrays.asList(prescriptionMock, prescriptionMock)));
		//Assert
		Assertions.assertEquals(2, prescriptionService.findAll().size());
	}
	
	@Test
	public void test_FindById_ReturnsPrescriptionMock_WhenCalledWithId1() {
		//Arrange
		when(prescriptionRepository.findById(1L)).thenReturn(Optional.of(prescriptionMock));
		//Assert
		Assertions.assertEquals(prescriptionService.findById(1L).get(), prescriptionMock);
	}
	
	@Test
	public void test_FindById_ReturnsEmptyOptional_WhenCalledWithId5() {
		//Assert
		Assertions.assertTrue(prescriptionService.findById(5L).isEmpty());
	}
	
}
