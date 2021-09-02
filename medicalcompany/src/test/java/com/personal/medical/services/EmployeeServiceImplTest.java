package com.personal.medical.services;

import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.personal.medical.model.Employee;
import com.personal.medical.repository.EmployeeRepository;

@SpringBootTest
class EmployeeServiceImplTest {

	//@Mock never mock the service being tested
	EmployeeService employeeService;
	
	@Mock
	EmployeeRepository employeeRepository;
	
	@Mock
	Employee employeeMock;
	
	@BeforeEach
	public void setUp() throws Exception {
		employeeService = new EmployeeServiceImpl(employeeRepository);
	}
	
	@Test
	void test_FindById_ReturnsEmployeeMock_WhenCalledWithId1() {
		//Arrange
		when(employeeRepository.findById(1L)).thenReturn(Optional.of(employeeMock));
		//Assert
		Assertions.assertEquals(employeeMock, employeeService.findById(1L).get());
	}
	
	@Test
	void test_FindById_ReturnsEmptyOptional_WhenCalledWithId5() {
		//Assert
		Assertions.assertTrue(employeeService.findById(5L).isEmpty());
	}
	
	@Test
	void test_UpdateFirstName_ReturnsEmployeeWithCorrectName_WhenGivenStringNewName() {
		//Arrange
		Employee employee = new Employee();
		employee.setFirstName("test");
		//Act
		employeeService.updateFirstName(employee, "new name");
		//Assert
		Assertions.assertEquals("new name", employee.getFirstName());
	}
	
}
