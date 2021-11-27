package com.personal.medical.appointments.services;

import com.personal.medical.appointments.model.DoctorAppointment;

public interface DoctorAppointmentService extends CrudService<DoctorAppointment, Long> {
	
	void deleteAppointmentsForGivenDoctorId(Long doctorId);
	void deleteAppointmentsForGivenPatientId(Long patientId);
	boolean checkIfPatientOrDoctorExists(DoctorAppointment doctorAppointment);
}
