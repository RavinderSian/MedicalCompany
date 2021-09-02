package com.personal.medical.repository;

import org.springframework.data.repository.CrudRepository;

import com.personal.medical.model.Medicine;

public interface MedicineRepository extends CrudRepository<Medicine, Long>{

	Medicine findByName(String name);
}

