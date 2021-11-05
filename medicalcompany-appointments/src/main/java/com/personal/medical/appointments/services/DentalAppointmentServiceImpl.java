package com.personal.medical.appointments.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.personal.medical.appointments.model.DentalAppointment;
import com.personal.medical.appointments.repositories.DentalAppointmentRepository;

@Service
public class DentalAppointmentServiceImpl implements DentalAppointmentService {
	
	private final DentalAppointmentRepository repository;
	
	public DentalAppointmentServiceImpl(DentalAppointmentRepository repository) {
		this.repository = repository;
	}

	@Override
	public DentalAppointment save(DentalAppointment dentalAppointment) {
		return repository.save(dentalAppointment);
	}

	@Override
	public void delete(DentalAppointment dentalAppointment) {
		repository.delete(dentalAppointment);
	}

	@Override
	public List<DentalAppointment> findAll() {
		return (List<DentalAppointment>) repository.findAll();
	}

	@Override
	public Optional<DentalAppointment> findById(Long id) {
		return repository.findById(id).isPresent()
		? repository.findById(id)
		: Optional.empty();
	}

	
	
}
