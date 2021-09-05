package com.personal.medical.repository;

import org.springframework.data.repository.CrudRepository;

import com.personal.medical.model.DentalPatient;

public interface DentalPatientRepository extends CrudRepository<DentalPatient, Long>{

}
