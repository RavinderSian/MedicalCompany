package com.personal.medical.services;

import java.util.List;
import java.util.Optional;

public interface CrudService<T, ID> {

	T save(T t);
	void delete(T t);
	List<T> findAll();
	Optional<T> findById(ID id);
}
