package com.personal.medical.appointments.controllers;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface CrudController<T, ID> {

	@GetMapping("{id}")
	ResponseEntity<?> getById(@PathVariable ID id);
	
	@DeleteMapping("delete/{id}")
	ResponseEntity<?> deleteById(@PathVariable ID id);
	
	@PutMapping("save")
	ResponseEntity<?> add(@RequestBody @Valid T t, BindingResult bindingResult); //@Valid is needed to process error messages
}
