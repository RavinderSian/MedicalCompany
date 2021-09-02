package com.personal.medical.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.personal.medical.model.Patient;
import com.personal.medical.repository.PatientRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PatientServiceImpl implements PatientService {

	private final PatientRepository patientRepository;
	
	@Override
	public Patient save(Patient patient) {
		return patientRepository.save(patient);
	}

	@Override
	public void delete(Patient patient) {
		patient.getPrescriptions().forEach(prescription -> prescription.setPatient(null));
		patientRepository.delete(patient);
	}

	@Override
	public List<Patient> findAll() {
		return (List<Patient>) patientRepository.findAll();
	}

	@Override
	public Optional<Patient> findById(Long id) {
		
		return patientRepository.findById(id).isEmpty()
		? Optional.empty()
		: patientRepository.findById(id);
	}

	@Override
	public Patient updateFirstName(Patient patient, String firstName) {
		patient.setFirstName(firstName);
		return patientRepository.save(patient);
	}

}
