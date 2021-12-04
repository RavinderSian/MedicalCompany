package com.personal.medical.services;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.personal.medical.model.DentalPatient;
import com.personal.medical.repository.DentalPatientRepository;

@Service
public class DentalPatientServiceImpl implements DentalPatientService {
	
	private final DentalPatientRepository repository;
	private final RestTemplate restTemplate;
	
	public DentalPatientServiceImpl(DentalPatientRepository repository, RestTemplate restTemplate) {
		this.repository = repository;
		this.restTemplate = restTemplate;
	}

	@Override
	public DentalPatient save(DentalPatient dentalPatient) {
		return repository.save(dentalPatient);
	}

	@Override
	public void delete(DentalPatient dentalPatient) {
		
		restTemplate.exchange("http://localhost:8081/dentalappointments/delete/patient/" + dentalPatient.getId(), HttpMethod.DELETE, 
				new HttpEntity<>(null, null), String.class);
		
		repository.delete(dentalPatient);
	}

	@Override
	public List<DentalPatient> findAll() {
		return (List<DentalPatient>) repository.findAll();
	}

	@Override
	public Optional<DentalPatient> findById(Long id) {
		return repository.findById(id).isPresent()
		? repository.findById(id)
		: Optional.empty();
	}

}
