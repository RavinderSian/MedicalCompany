package com.personal.medical.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.personal.medical.model.DentalHygienist;
import com.personal.medical.repository.DentalHygienistRepository;

@Service
public class DentalHygienistServiceImpl implements DentalHygienistService {
	
	private DentalHygienistRepository repository;
	
	public DentalHygienistServiceImpl(DentalHygienistRepository repository) {
		this.repository = repository;
	}

	@Override
	public DentalHygienist save(DentalHygienist dentalHygienist) {
		return repository.save(dentalHygienist);
	}

	@Override
	public void delete(DentalHygienist dentalHygienist) {
		repository.delete(dentalHygienist);
	}

	@Override
	public List<DentalHygienist> findAll() {
		return (List<DentalHygienist>) repository.findAll();
	}

	@Override
	public Optional<DentalHygienist> findById(Long id) {
		return repository.findById(1L).isPresent()
		? repository.findById(id)
		: Optional.empty();
	}

}
