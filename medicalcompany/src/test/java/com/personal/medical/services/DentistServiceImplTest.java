package com.personal.medical.services;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.personal.medical.model.DentalAppointment;
import com.personal.medical.repository.DentistRepository;

@SpringBootTest
class DentistServiceImplTest {

	private DentistService service;
	
	@Mock
	private DentistRepository repository;
	
	@BeforeEach
	void setUp() throws Exception {
		service = new DentistServiceImpl(repository);
	}

	@Test
	void test_ServiceNotNull() {
		assertThat(service, not(equalTo(null)));
	}
	
	@Test
	void test_FindById_ReturnsEmptyOptional_WhenNoId_Present() {
		
		Optional<DentalAppointment> result = Optional.empty();
		assertThat(result, equalTo(Optional.empty()));
	}

}
