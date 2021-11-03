package com.personal.medical.repository;

import org.springframework.data.repository.CrudRepository;

import com.personal.medical.model.DoctorAppointment;

public interface DoctorAppointmentRepository extends CrudRepository<DoctorAppointment, Long> {

}
