package com.personal.medical.repository;

import org.springframework.data.repository.CrudRepository;

import com.personal.medical.model.Prescription;

public interface PrescriptionRepository extends CrudRepository<Prescription, Long> {

}
