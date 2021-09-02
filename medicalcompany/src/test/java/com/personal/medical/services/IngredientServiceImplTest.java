package com.personal.medical.services;

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

import com.personal.medical.model.Ingredient;
import com.personal.medical.repository.IngredientRepository;

@SpringBootTest
class IngredientServiceImplTest {

	//@Mock never mock the service being tested
	IngredientService ingredientService;
	
	@Mock
	IngredientRepository ingredientRepository;
	
	@Mock
	Ingredient ingredientMock;
	
	@BeforeEach
	public void setUp() throws Exception {
		ingredientService = new IngredientServiceImpl(ingredientRepository);
	}
	
	@Test
	public void test_Save_CallsRepositorySave_WhenCalled() {
		//Act
		ingredientService.save(ingredientMock);
		//Assert
		verify(ingredientRepository, times(1)).save(ingredientMock);
	}
	
	@Test
	public void test_Save_ReturnsCorrectIngredient_WhenGivenIngredientMock() {
		//Assert
		when(ingredientRepository.save(ingredientMock)).thenReturn(ingredientMock);
		//Assert
		Assertions.assertEquals(ingredientMock, ingredientService.save(ingredientMock));
	}
	
	@Test
	public void test_Delete_Calls_RepositoryDelete_WhenCalled() {
		//Act
		ingredientService.delete(ingredientMock);
		//Assert
		verify(ingredientRepository, times(1)).delete(ingredientMock);
	}
	
	@Test
	public void test_FindAll_Calls_RepositoryFindAll_WhenCalled() {
		//Act
		ingredientService.findAll();
		//Assert
		verify(ingredientRepository, times(1)).findAll();
	}
	
	@Test
	public void test_FindAll_ReturnsEmptyList_WhenGivenNothing() {
		//Arrange
		when(ingredientRepository.findAll()).thenReturn(new ArrayList<>());
		//Assert
		Assertions.assertEquals(0, ingredientService.findAll().size());
	}
	
	
	@Test
	public void test_FindAll_ReturnsListOfSize1_WhenGivenIngredientMock() {
		//Arrange
		when(ingredientRepository.findAll()).thenReturn(new ArrayList<Ingredient>(Arrays.asList(ingredientMock)));
		//Assert
		Assertions.assertEquals(1, ingredientService.findAll().size());
	}
	
	@Test
	public void test_FindAll_ReturnsListOfSize2_WhenGivenDuplicateIngredientMocks() {
		//Arrange
		when(ingredientRepository.findAll()).thenReturn(new ArrayList<Ingredient>(Arrays.asList(ingredientMock, ingredientMock)));
		//Assert
		Assertions.assertEquals(2, ingredientService.findAll().size());
	}
	
	@Test
	public void test_FindById_ReturnsIngredientMock_WhenCalledWithId1() {
		//Arrange
		when(ingredientRepository.findById(1L)).thenReturn(Optional.of(ingredientMock));
		//Assert
		Assertions.assertEquals(ingredientMock, ingredientService.findById(1L).get());
	}
	
	@Test
	public void test_FindById_ReturnsEmptyOptional_WhenCalledWithId5() {
		//Assert
		Assertions.assertTrue(ingredientService.findById(5L).isEmpty());
	}
	
	@Test
	public void test_UpdateIngredientName_ReturnsIngredientWithCorrectName_WhenGivenStringNewName() {
		//Arrange
		Ingredient ingredient = new Ingredient();
		ingredient.setName("test");
		//Act
		ingredientService.updateIngredientName(ingredient, "new name");
		//Assert
		Assertions.assertEquals("new name", ingredient.getName());
	}

}
