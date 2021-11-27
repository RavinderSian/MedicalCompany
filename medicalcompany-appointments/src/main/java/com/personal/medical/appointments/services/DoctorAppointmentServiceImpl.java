package com.personal.medical.appointments.services;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.personal.medical.appointments.model.DoctorAppointment;
import com.personal.medical.appointments.repositories.DoctorAppointmentRepository;

@Service
public class DoctorAppointmentServiceImpl implements DoctorAppointmentService {
	
	private final DoctorAppointmentRepository repository;
	
	private final RestTemplate restTemplate;
	
	public DoctorAppointmentServiceImpl(DoctorAppointmentRepository repository, RestTemplate restTemplate) {
		this.repository = repository;
		this.restTemplate = restTemplate;
	}

	@Override
	public DoctorAppointment save(DoctorAppointment doctorAppointment) {
		
		return repository.save(doctorAppointment);
	}

	@Override
	public void delete(DoctorAppointment doctorAppointment) {
		repository.delete(doctorAppointment);
	}

	@Override
	public List<DoctorAppointment> findAll() {
		return (List<DoctorAppointment>) repository.findAll();
	}

	@Override
	public Optional<DoctorAppointment> findById(Long id) {
		return repository.findById(id).isPresent()
		? repository.findById(id)
		: Optional.empty();
	}

	@Override
	public void deleteAppointmentsForGivenDoctorId(Long doctorId) {
		repository.findByDoctorId(doctorId).forEach(appointment -> delete(appointment));
		
	}

	@Override
	public void deleteAppointmentsForGivenPatientId(Long patientId) {
		repository.findByPatientId(patientId).forEach(appointment -> delete(appointment));
	}

	@Override
	public boolean checkIfPatientOrDoctorExists(DoctorAppointment doctorAppointment) {
		
		try {
			restTemplate.exchange("http://localhost:8080/patient/" + doctorAppointment.getPatientId(), HttpMethod.GET, new HttpEntity<>(null, null), String.class);
		} catch(HttpClientErrorException.NotFound httpClientErrorException) {
			return false;
		}
		
		try {
			restTemplate.exchange("http://localhost:8080/doctor/" + doctorAppointment.getDoctorId(), HttpMethod.GET, new HttpEntity<>(null, null), String.class);
		} catch(HttpClientErrorException.NotFound httpClientErrorException) {
			return false;
		}
		
		return true;
	}
	
}
