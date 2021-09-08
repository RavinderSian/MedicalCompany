package com.personal.medical.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.personal.medical.model.DentalHygienist;
import com.personal.medical.services.DentalHygienistService;

@RestController
@RequestMapping("hygienist/")
public class DentalHygienistController implements CrudController<DentalHygienist, Long> {

	private final DentalHygienistService service;

	public DentalHygienistController(DentalHygienistService service) {
		this.service = service;
	}

	@Override
	public ResponseEntity<?> getById(Long id) {
		return service.findById(id).isPresent()
		? new ResponseEntity<>(service.findById(id).get(), HttpStatus.OK)
		: new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@Override
	public ResponseEntity<?> deleteById(Long id) {
		
		if (service.findById(id).isPresent()) {
			service.delete(service.findById(id).get());
			return new ResponseEntity<>(HttpStatus.OK);
		} return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
	}

	@Override
	public ResponseEntity<?> add(@Valid DentalHygienist hygienist, BindingResult bindingResult) {
		
		if (bindingResult.hasErrors()){
			Map<String, String> errors = new HashMap<>();
			bindingResult.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
			return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
		}
		
		service.save(hygienist);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
}
