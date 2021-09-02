package com.personal.medical.repository;

import org.springframework.data.repository.CrudRepository;

import com.personal.medical.model.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, Long>{

}
