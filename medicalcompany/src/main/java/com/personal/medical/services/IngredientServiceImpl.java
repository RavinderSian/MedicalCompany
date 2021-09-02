package com.personal.medical.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.personal.medical.model.Ingredient;
import com.personal.medical.repository.IngredientRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class IngredientServiceImpl implements IngredientService {

	private final IngredientRepository ingredientRepository;

	@Override
	public Ingredient save(Ingredient ingredient) {
		return ingredientRepository.save(ingredient);
	}

	@Override
	public void delete(Ingredient ingredient) {
		ingredientRepository.delete(ingredient);
		
	}

	@Override
	public List<Ingredient> findAll() {
		return (List<Ingredient>) ingredientRepository.findAll();
	}

	@Override
	public Optional<Ingredient> findById(Long id) {
		return ingredientRepository.findById(id).isEmpty()
		? Optional.empty()
		: ingredientRepository.findById(id);
	}
	
	public Ingredient updateIngredientName(Ingredient ingredient, String name) {
		ingredient.setName(name);
		return ingredientRepository.save(ingredient);
	}
	
}
