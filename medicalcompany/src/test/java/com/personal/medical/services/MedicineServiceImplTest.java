package com.personal.medical.services;

import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.personal.medical.model.Medicine;
import com.personal.medical.repository.MedicineRepository;

@SpringBootTest
class MedicineServiceImplTest {

	MedicineService medicineService;
	
	@Mock
	MedicineRepository medicineRepository;
	
	@Mock
	Medicine medicineMock;
	
	@BeforeEach
	void setUp() throws Exception {
		medicineService = new MedicineServiceImpl(medicineRepository);
	}

	@Test
	void test_FindById_ReturnsMedicineMock_WhenCalledWithId1() {
		//Arrange
		when(medicineRepository.findById(1L)).thenReturn(Optional.of(medicineMock));
		//Assert
		Assertions.assertEquals(medicineService.findById(1L).get(), medicineMock);
	}
	
	@Test
	void test_FindById_ReturnsEmptyOptional_WhenCalledWithId5() {
		//Assert
		Assertions.assertTrue(medicineService.findById(5L).isEmpty());
	}
	
	@Test
	void test_UpdateName_ReturnsMedicineWithCorrectName_WhenGivenStringName() {
		//Arrange
		Medicine medicine = new Medicine();
		medicine.setName("mock");
		//Act
		medicineService.updateName(medicine, "name");
		//Assert
		Assertions.assertEquals("name", medicine.getName());
	}
}
