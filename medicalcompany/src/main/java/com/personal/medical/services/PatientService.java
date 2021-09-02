package com.personal.medical.services;

import com.personal.medical.model.Patient;

public interface PatientService extends CrudService<Patient, Long> {

	Patient updateFirstName(Patient patient, String firstName);
}
