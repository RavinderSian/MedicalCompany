package com.personal.medical.appointments.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.personal.medical.appointments.model.DentalAppointment;

public interface DentalAppointmentRepository extends CrudRepository<DentalAppointment, Long>{
	
	List<DentalAppointment> findByDentistId(Long dentistId);
	List<DentalAppointment> findByPatientId(Long patientId);

}
