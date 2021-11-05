package com.personal.medical.appointments.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.personal.medical.appointments.model.DoctorAppointment;
import com.personal.medical.appointments.repositories.DoctorAppointmentRepository;

@Service
public class DoctorAppointmentServiceImpl implements DoctorAppointmentService {
	
	private final DoctorAppointmentRepository repository;
	
	public DoctorAppointmentServiceImpl(DoctorAppointmentRepository repository) {
		this.repository = repository;
	}

	@Override
	public DoctorAppointment save(DoctorAppointment doctorAppointment) {
		return repository.save(doctorAppointment);
	}

	@Override
	public void delete(DoctorAppointment doctorAppointment) {
		repository.delete(doctorAppointment);
	}

	@Override
	public List<DoctorAppointment> findAll() {
		return (List<DoctorAppointment>) repository.findAll();
	}

	@Override
	public Optional<DoctorAppointment> findById(Long id) {
		return repository.findById(id).isPresent()
		? repository.findById(id)
		: Optional.empty();
	}

	
	
}
