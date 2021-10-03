package com.personal.medical.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.personal.medical.model.DentalPatient;
import com.personal.medical.repository.DentalPatientRepository;

@Service
public class DentalPatientServiceImpl implements DentalPatientService {
	
	private final DentalPatientRepository repository;
	
	public DentalPatientServiceImpl(DentalPatientRepository repository) {
		this.repository = repository;
	}

	@Override
	public DentalPatient save(DentalPatient dentalPatient) {
		return repository.save(dentalPatient);
	}

	@Override
	public void delete(DentalPatient dentalPatient) {
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
