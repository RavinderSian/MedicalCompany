package com.personal.medical.appointments.repositories;

import org.springframework.data.repository.CrudRepository;

import com.personal.medical.appointments.model.DentalAppointment;

public interface DentalAppointmentRepository extends CrudRepository<DentalAppointment, Long>{

}
