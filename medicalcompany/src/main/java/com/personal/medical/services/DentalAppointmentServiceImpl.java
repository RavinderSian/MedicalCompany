package com.personal.medical.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.personal.medical.model.Appointment;
import com.personal.medical.repository.DentalAppointmentRepository;

@Service
public class DentalAppointmentServiceImpl implements DentalAppointmentService {
	
	private final DentalAppointmentRepository repository;
	
	public DentalAppointmentServiceImpl(DentalAppointmentRepository repository) {
		this.repository = repository;
	}

	@Override
	public Appointment save(Appointment dentalAppointment) {
		return repository.save(dentalAppointment);
	}

	@Override
	public void delete(Appointment dentalAppointment) {
		repository.delete(dentalAppointment);
	}

	@Override
	public List<Appointment> findAll() {
		return (List<Appointment>) repository.findAll();
	}

	@Override
	public Optional<Appointment> findById(Long id) {
		return repository.findById(id).isPresent()
		? repository.findById(id)
		: Optional.empty();
	}

	
	
}
