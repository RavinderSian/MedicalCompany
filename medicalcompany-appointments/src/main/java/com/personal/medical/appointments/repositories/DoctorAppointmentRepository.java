package com.personal.medical.appointments.repositories;

import org.springframework.data.repository.CrudRepository;

import com.personal.medical.appointments.model.DoctorAppointment;

public interface DoctorAppointmentRepository extends CrudRepository<DoctorAppointment, Long>{

}
