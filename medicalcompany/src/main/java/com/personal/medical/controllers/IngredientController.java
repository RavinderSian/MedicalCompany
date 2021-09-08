package com.personal.medical.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.personal.medical.model.Ingredient;
import com.personal.medical.services.IngredientService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("ingredient/")
public class IngredientController implements CrudController<Ingredient, Long> {

	private final IngredientService ingredientServices;

	public IngredientController(IngredientService ingredientServices) {
		this.ingredientServices = ingredientServices;
	}

	@Override
	public ResponseEntity<?> getById(Long id) {
		return ingredientServices.findById(id).isEmpty()
		? new ResponseEntity<>(HttpStatus.NOT_FOUND)
		: new ResponseEntity<>(ingredientServices.findById(id).get(), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> deleteById(Long id) {
		Optional<Ingredient> ingredientOptional = ingredientServices.findById(id);
		if (ingredientOptional.isEmpty()) {
			log.info("Id not present in database");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		ingredientServices.delete(ingredientOptional.get());
		return new ResponseEntity<>("Deleted ingredient", HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> add(Ingredient ingredient, BindingResult bindingResult) {
		if (bindingResult.hasFieldErrors()) {
			Map<String, String> errors = new HashMap<>();
			bindingResult.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
			return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
		}
		Ingredient savedIngredient = ingredientServices.save(ingredient);
		return new ResponseEntity<>(savedIngredient, HttpStatus.OK);
	}
	
	@PatchMapping("{id}/updatename")
	public ResponseEntity<?> updateName(@PathVariable Long id, @RequestBody String name){
		Optional<Ingredient> ingredientOptional = ingredientServices.findById(id);
		if (ingredientOptional.isEmpty()) {
			log.info("Id not present in database");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		Ingredient ingredient = ingredientOptional.get();
		ingredientServices.updateIngredientName(ingredient, name);
		return new ResponseEntity<>(ingredient, HttpStatus.OK);
	}
	
}
