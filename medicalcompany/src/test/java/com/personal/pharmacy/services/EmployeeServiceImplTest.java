package com.personal.pharmacy.services;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.personal.medical.model.Employee;
import com.personal.medical.repository.EmployeeRepository;
import com.personal.medical.services.EmployeeService;
import com.personal.medical.services.EmployeeServiceImpl;

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
	public void test_Save_CallsRepositorySave_WhenCalled() {
		//Act
		employeeService.save(employeeMock);
		//Assert
		verify(employeeRepository, times(1)).save(employeeMock);
	}
	
	@Test
	public void test_Save_ReturnsCorrectEmployee_WhenGivenEmployeeMock() {
		//Assert
		when(employeeRepository.save(employeeMock)).thenReturn(employeeMock);
		//Assert
		Assertions.assertEquals(employeeMock, employeeService.save(employeeMock));
	}
	
	@Test
	public void test_Delete_Calls_RepositoryDelete_WhenCalled() {
		//Act
		employeeService.delete(employeeMock);
		//Assert
		verify(employeeRepository, times(1)).delete(employeeMock);
	}
	
	@Test
	public void test_FindAll_Calls_RepositoryFindAll_WhenCalled() {
		//Act
		employeeService.findAll();
		//Assert
		verify(employeeRepository, times(1)).findAll();
	}
	
	@Test
	public void test_FindAll_ReturnsEmptyList_WhenGivenNothing() {
		//Arrange
		when(employeeRepository.findAll()).thenReturn(new ArrayList<>());
		//Assert
		Assertions.assertEquals(0, employeeService.findAll().size());
	}
	
	
	@Test
	public void test_FindAll_ReturnsListOfSize1_WhenGivenEmployeeMock() {
		//Arrange
		when(employeeRepository.findAll()).thenReturn(new ArrayList<Employee>(Arrays.asList(employeeMock)));
		//Assert
		Assertions.assertEquals(1, employeeService.findAll().size());
	}
	
	@Test
	public void test_FindAll_ReturnsListOfSize2_WhenGivenDuplicateEmployeeMocks() {
		//Arrange
		when(employeeRepository.findAll()).thenReturn(new ArrayList<Employee>(Arrays.asList(employeeMock, employeeMock)));
		//Assert
		Assertions.assertEquals(2, employeeService.findAll().size());
	}
	
	@Test
	public void test_FindById_ReturnsEmployeeMock_WhenCalledWithId1() {
		//Arrange
		when(employeeRepository.findById(1L)).thenReturn(Optional.of(employeeMock));
		//Assert
		Assertions.assertEquals(employeeMock, employeeService.findById(1L).get());
	}
	
	@Test
	public void test_FindById_ReturnsEmptyOptional_WhenCalledWithId5() {
		//Assert
		Assertions.assertTrue(employeeService.findById(5L).isEmpty());
	}
	
	@Test
	public void test_UpdateFirstName_ReturnsEmployeeWithCorrectName_WhenGivenStringNewName() {
		//Arrange
		Employee employee = new Employee();
		employee.setFirstName("test");
		//Act
		employeeService.updateFirstName(employee, "new name");
		//Assert
		Assertions.assertEquals("new name", employee.getFirstName());
	}
	
}
