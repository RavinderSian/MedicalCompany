package com.personal.medical.services;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.personal.medical.model.Doctor;
import com.personal.medical.repository.DoctorRepository;

@Service
public class DoctorServiceImpl implements DoctorService {
	
	private final DoctorRepository repository;
	private final RestTemplate restTemplate;
	
	public DoctorServiceImpl(DoctorRepository repository, RestTemplate restTemplate) {
		this.repository = repository;
		this.restTemplate = restTemplate;
	}

	@Override
	public Doctor save(Doctor doctor) {
		
		return repository.save(doctor);
	}

	@Override
	public void delete(Doctor doctor) {
		
		restTemplate.exchange("http://localhost:8081/doctorappointments/delete/doctor/" + doctor.getId(), HttpMethod.DELETE, 
				new HttpEntity<>(null, null), String.class);
		
		repository.delete(doctor);
	}

	@Override
	public List<Doctor> findAll() {
		return (List<Doctor>) repository.findAll();
	}

	@Override
	public Optional<Doctor> findById(Long id) {
		return repository.findById(id).isPresent()
		? repository.findById(id)
		: Optional.empty();
	}

}
