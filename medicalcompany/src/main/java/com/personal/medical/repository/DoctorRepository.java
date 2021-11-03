package com.personal.medical.repository;

import org.springframework.data.repository.CrudRepository;

import com.personal.medical.model.Doctor;

public interface DoctorRepository extends CrudRepository<Doctor, Long>{

}
