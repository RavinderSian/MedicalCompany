package com.personal.medical.repository;

import org.springframework.data.repository.CrudRepository;

import com.personal.medical.model.DentalAppointment;

public interface DentalAppointmentRepository extends CrudRepository<DentalAppointment, Long> {

}
