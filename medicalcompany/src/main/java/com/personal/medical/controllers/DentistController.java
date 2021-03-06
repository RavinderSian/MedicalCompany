package com.personal.medical.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.ResourceAccessException;

import com.personal.medical.model.Dentist;
import com.personal.medical.services.DentistService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("dentist/")
public class DentistController implements CrudController<Dentist, Long>{
	
	private final DentistService service;

	public DentistController(DentistService service) {
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
			try {
				service.delete(service.findById(id).get());
			}catch(ResourceAccessException exception) {
				log.info("Appointments application is not accessible");
				return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
			}
			return new ResponseEntity<>(HttpStatus.OK);
		} 
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
	}

	@Override
	public ResponseEntity<?> add(@Valid Dentist dentist, BindingResult bindingResult) {
		
		if (bindingResult.hasErrors()){
			Map<String, String> errors = new HashMap<>();
			bindingResult.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
			return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
		}
		
		service.save(dentist);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
}
