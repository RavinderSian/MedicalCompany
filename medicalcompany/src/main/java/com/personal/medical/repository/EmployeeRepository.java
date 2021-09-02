package com.personal.medical.repository;

import org.springframework.data.repository.CrudRepository;

import com.personal.medical.model.Employee;

public interface EmployeeRepository extends CrudRepository<Employee, Long>{

}
