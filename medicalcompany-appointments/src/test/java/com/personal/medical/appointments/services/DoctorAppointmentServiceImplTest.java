package com.personal.medical.appointments.services;

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

import com.personal.medical.appointments.model.DoctorAppointment;
import com.personal.medical.appointments.repositories.DoctorAppointmentRepository;

@SpringBootTest
class DoctorAppointmentServiceImplTest {

	private DoctorAppointmentService service;
	
	@Mock
	private DoctorAppointmentRepository repository;
	
	@Mock
	private DoctorAppointment doctorAppointmentMock;
	
	@BeforeEach
	void setUp() throws Exception {
		service = new DoctorAppointmentServiceImpl(repository);
	}

	@Test
	void test_ServiceNotNull() {
		assertThat(service, not(equalTo(null)));
	}
	
	@Test
	void test_FindById_ReturnsMock_WhenCalledWithId1() {
		//Arrange
		when(repository.findById(1L)).thenReturn(Optional.of(doctorAppointmentMock));
		//Assert
		Assertions.assertEquals(doctorAppointmentMock, service.findById(1L).get());
	}
	
	@Test
	void test_FindById_ReturnsEmptyOptional_WhenCalledWithId5() {
		//Assert
		Assertions.assertTrue(service.findById(5L).isEmpty());
	}

}
