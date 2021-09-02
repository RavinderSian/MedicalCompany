package com.personal.medical.services;

import com.personal.medical.model.Employee;

public interface EmployeeService extends CrudService<Employee, Long>{

	Employee updateFirstName(Employee employee, String firstName);
	
}
