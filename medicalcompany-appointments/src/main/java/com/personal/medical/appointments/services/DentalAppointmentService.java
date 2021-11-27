package com.personal.medical.appointments.services;

import com.personal.medical.appointments.model.DentalAppointment;

public interface DentalAppointmentService extends CrudService<DentalAppointment, Long> {

	void deleteAppointmentsForGivenDentistId(Long dentistId);
	void deleteAppointmentsForGivenPatientId(Long patientId);
	boolean checkIfPatientOrDentistExists(DentalAppointment dentalAppointment);
	
}
