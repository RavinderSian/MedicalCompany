package com.personal.medical.appointments.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.personal.medical.appointments.model.DoctorAppointment;

public interface DoctorAppointmentRepository extends CrudRepository<DoctorAppointment, Long>{
	
	List<DoctorAppointment> findByDoctorId(Long doctorId);
	List<DoctorAppointment> findByPatientId(Long patientId);
	

}
