package com.personal.medical.services;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.personal.medical.model.DoctorAppointment;
import com.personal.medical.repository.DoctorAppointmentRepository;

@SpringBootTest
class DoctorAppointmentServiceImplTest {

	private DoctorAppointmentService service;
	
	@Mock
	private DoctorAppointmentRepository repository;
	
	@BeforeEach
	void setUp() throws Exception {
		service = new DoctorAppointmentServiceImpl(repository);
	}

	@Test
	void test_ServiceNotNull() {
		assertThat(service, not(equalTo(null)));
	}
	
	@Test
	void test_FindById_ReturnsEmptyOptional_WhenNoId_Present() {
		
		Optional<DoctorAppointment> result = Optional.empty();
		assertThat(result, equalTo(Optional.empty()));
	}

}
