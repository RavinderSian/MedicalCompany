package com.personal.medical.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.personal.medical.model.Dentist;
import com.personal.medical.repository.DentistRepository;

@Service
public class DentistServiceImpl implements DentistService {
	
	private DentistRepository repository;
	
	public DentistServiceImpl(DentistRepository repository) {
		this.repository = repository;
	}

	@Override
	public Dentist save(Dentist dentist) {
		return repository.save(dentist);
	}

	@Override
	public void delete(Dentist dentist) {
		repository.delete(dentist);
	}

	@Override
	public List<Dentist> findAll() {
		return (List<Dentist>) repository.findAll();
	}

	@Override
	public Optional<Dentist> findById(Long id) {
		return repository.findById(1L).isPresent()
		? repository.findById(id)
		: Optional.empty();
	}

}
