package com.personal.medical.services;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.personal.medical.model.Dentist;
import com.personal.medical.repository.DentistRepository;

@Service
public class DentistServiceImpl implements DentistService {
	
	private final DentistRepository repository;
	private final RestTemplate restTemplate;
	
	public DentistServiceImpl(DentistRepository repository, RestTemplate restTemplate) {
		this.repository = repository;
		this.restTemplate = restTemplate;
	}

	@Override
	public Dentist save(Dentist dentist) {
		
		return repository.save(dentist);
	}

	@Override
	public void delete(Dentist dentist) {
		
		restTemplate.exchange("localhost:8081/delete/dentist/" + dentist.getId(), HttpMethod.DELETE, 
				new HttpEntity<>(null, null), String.class);
		
		repository.delete(dentist);
	}

	@Override
	public List<Dentist> findAll() {
		return (List<Dentist>) repository.findAll();
	}

	@Override
	public Optional<Dentist> findById(Long id) {
		return repository.findById(id).isPresent()
		? repository.findById(id)
		: Optional.empty();
	}

}
