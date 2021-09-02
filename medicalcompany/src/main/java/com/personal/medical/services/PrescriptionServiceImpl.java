package com.personal.medical.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.personal.medical.model.Prescription;
import com.personal.medical.repository.PrescriptionRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PrescriptionServiceImpl implements PrescriptionService {

	private final PrescriptionRepository prescriptionRepository;
	
	@Override
	public Prescription save(Prescription prescription) {
		return prescriptionRepository.save(prescription);
	}

	@Override
	public void delete(Prescription prescription) {
		prescription.setEmployee(null);
		prescriptionRepository.delete(prescription);
	}

	@Override
	public List<Prescription> findAll() {
		return (List<Prescription>) prescriptionRepository.findAll();
	}

	@Override
	public Optional<Prescription> findById(Long id) {
		
		return prescriptionRepository.findById(id).isEmpty()
		? Optional.empty()
		: prescriptionRepository.findById(id);
	}

}
