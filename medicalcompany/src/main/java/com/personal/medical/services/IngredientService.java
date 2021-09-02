package com.personal.medical.services;

import com.personal.medical.model.Ingredient;

public interface IngredientService extends CrudService<Ingredient, Long> {

	Ingredient updateIngredientName(Ingredient ingredient, String name);
}
