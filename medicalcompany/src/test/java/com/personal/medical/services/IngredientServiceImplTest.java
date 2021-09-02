package com.personal.medical.services;

import static org.mockito.Mockito.when;

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
	void test_FindById_ReturnsIngredientMock_WhenCalledWithId1() {
		//Arrange
		when(ingredientRepository.findById(1L)).thenReturn(Optional.of(ingredientMock));
		//Assert
		Assertions.assertEquals(ingredientMock, ingredientService.findById(1L).get());
	}
	
	@Test
	void test_FindById_ReturnsEmptyOptional_WhenCalledWithId5() {
		//Assert
		Assertions.assertTrue(ingredientService.findById(5L).isEmpty());
	}
	
	@Test
	void test_UpdateIngredientName_ReturnsIngredientWithCorrectName_WhenGivenStringNewName() {
		//Arrange
		Ingredient ingredient = new Ingredient();
		ingredient.setName("test");
		//Act
		ingredientService.updateIngredientName(ingredient, "new name");
		//Assert
		Assertions.assertEquals("new name", ingredient.getName());
	}

}
