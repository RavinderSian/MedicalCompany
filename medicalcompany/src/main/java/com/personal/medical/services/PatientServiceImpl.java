package com.personal.medical.services;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.personal.medical.model.Patient;
import com.personal.medical.repository.PatientRepository;

@Service
public class PatientServiceImpl implements PatientService {

	private final PatientRepository patientRepository;
	private final RestTemplate restTemplate;
	
	public PatientServiceImpl(PatientRepository patientRepository, RestTemplate restTemplate) {
		this.patientRepository = patientRepository;
		this.restTemplate = restTemplate;
	}
	
	@Override
	public Patient save(Patient patient) {
		return patientRepository.save(patient);
	}

	@Override
	public void delete(Patient patient) {
		
		restTemplate.exchange("http://localhost:8081/doctorappointments/delete/patient/" + patient.getPatientId(), HttpMethod.DELETE, 
				new HttpEntity<>(null, null), String.class);
		
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
