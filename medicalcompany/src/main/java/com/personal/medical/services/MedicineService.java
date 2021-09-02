package com.personal.medical.services;

import com.personal.medical.model.Medicine;

public interface MedicineService extends CrudService<Medicine, Long>{

	Medicine findByName(String name);
	Medicine updateName(Medicine medicine, String name);
}
