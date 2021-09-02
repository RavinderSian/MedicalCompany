package com.personal.medical.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.personal.medical.model.Employee;
import com.personal.medical.repository.EmployeeRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

	private final EmployeeRepository employeeRepository;
	
	@Override
	public Employee save(Employee employee) {
		return employeeRepository.save(employee);
	}

	@Override
	public void delete(Employee employee) {
		employee.getPrescriptions().forEach(prescription -> prescription.setEmployee(null));
		employeeRepository.delete(employee);
	}

	
	@Override
	public List<Employee> findAll() {
		return (List<Employee>) employeeRepository.findAll();
	}
	
	
	@Override
	public Optional<Employee> findById(Long id) {
		
		return employeeRepository.findById(id).isEmpty()
		?  Optional.empty()
		:  employeeRepository.findById(id);
	}

	@Override
	public Employee updateFirstName(Employee employee, String firstName) {
		employee.setFirstName(firstName);
		return employeeRepository.save(employee);
	}

}
 