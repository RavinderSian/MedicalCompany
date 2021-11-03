package com.personal.medical.services;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.personal.medical.model.Dentist;
import com.personal.medical.repository.DentistRepository;

@SpringBootTest
class DentistServiceImplTest {

	private DentistService service;
	
	@Mock
	private DentistRepository repository;
	
	@Mock
	private Dentist dentistMock;
	
	@BeforeEach
	void setUp() throws Exception {
		service = new DentistServiceImpl(repository);
	}

	@Test
	void test_ServiceNotNull() {
		assertThat(service, not(equalTo(null)));
	}
	
	@Test
	void test_FindById_ReturnsMock_WhenCalledWithId1() {
		//Arrange
		when(repository.findById(1L)).thenReturn(Optional.of(dentistMock));
		//Assert
		Assertions.assertEquals(dentistMock, service.findById(1L).get());
	}
	
	@Test
	void test_FindById_ReturnsEmptyOptional_WhenCalledWithId5() {
		//Assert
		Assertions.assertTrue(service.findById(5L).isEmpty());
	}

}
