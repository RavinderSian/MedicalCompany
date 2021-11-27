package com.personal.medical.appointments.services;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.personal.medical.appointments.model.DentalAppointment;
import com.personal.medical.appointments.repositories.DentalAppointmentRepository;

@Service
public class DentalAppointmentServiceImpl implements DentalAppointmentService {
	
	private final DentalAppointmentRepository repository;
	
	private final RestTemplate restTemplate;
	
	public DentalAppointmentServiceImpl(DentalAppointmentRepository repository, RestTemplate restTemplate) {
		this.repository = repository;
		this.restTemplate = restTemplate;
	}

	@Override
	public DentalAppointment save(DentalAppointment dentalAppointment) {
		return repository.save(dentalAppointment);
	}

	@Override
	public void delete(DentalAppointment dentalAppointment) {
		repository.delete(dentalAppointment);
	}
	
	@Override
	public void deleteAppointmentsForGivenDentistId(Long dentistId) {
		repository.findByDentistId(dentistId).forEach(appointment -> delete(appointment));
	}
	
	@Override
	public void deleteAppointmentsForGivenPatientId(Long patientId) {
		repository.findByPatientId(patientId).forEach(appointment -> delete(appointment));
	}

	@Override
	public List<DentalAppointment> findAll() {
		return (List<DentalAppointment>) repository.findAll();
	}

	@Override
	public Optional<DentalAppointment> findById(Long id) {
		return repository.findById(id).isPresent()
		? repository.findById(id)
		: Optional.empty();
	}
	
	@Override
	public boolean checkIfPatientOrDentistExists(DentalAppointment dentalAppointment) {
		
		try {
			restTemplate.exchange("http://localhost:8080/patient/" + dentalAppointment.getPatientId(), HttpMethod.GET, new HttpEntity<>(null, null), String.class);
		} catch(HttpClientErrorException.NotFound httpClientErrorException) {
			return false;
		}
		
		try {
			restTemplate.exchange("http://localhost:8080/dentist/" + dentalAppointment.getDentistId(), HttpMethod.GET, new HttpEntity<>(null, null), String.class);
		} catch(HttpClientErrorException.NotFound httpClientErrorException) {
			return false;
		}
		
		return true;
	}
	
}
