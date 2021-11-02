package com.personal.medical.repository;

import org.springframework.data.repository.CrudRepository;

import com.personal.medical.model.Appointment;

public interface DentalAppointmentRepository extends CrudRepository<Appointment, Long> {

}
