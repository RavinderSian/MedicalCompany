package com.personal.medical.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.personal.medical.model.Prescription;
import com.personal.medical.services.PrescriptionService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("prescription/")
public class PrescriptionController implements CrudController<Prescription, Long> {

	private final PrescriptionService prescriptionService;
	
	public PrescriptionController(PrescriptionService prescriptionService) {
		this.prescriptionService = prescriptionService;
	}

	@Override
	@GetMapping("{id}")
	public ResponseEntity<?> getById(Long id){
		return prescriptionService.findById(id).isEmpty()
		? new ResponseEntity<>(HttpStatus.NOT_FOUND)
		: new ResponseEntity<>(prescriptionService.findById(id).get(), HttpStatus.OK);
	}
	
	@Override
	@PostMapping("save")
	public ResponseEntity<?> add (Prescription prescription, BindingResult bindingResult){
		if (bindingResult.hasFieldErrors()) {
			List<String> errorStrings = new ArrayList<>();
			bindingResult.getFieldErrors().forEach(objectError -> {
				errorStrings.add(objectError.getDefaultMessage());
			});
			return new ResponseEntity<>(errorStrings.toString(), HttpStatus.BAD_REQUEST);
		}
		Prescription savedPrescription = prescriptionService.save(prescription);
		return new ResponseEntity<>(savedPrescription, HttpStatus.OK);
	}
	
	@Override
	@DeleteMapping("delete/{id}")
	public ResponseEntity<?> deleteById(Long id){
		Optional<Prescription> prescriptionOptional = prescriptionService.findById(id);
		if (prescriptionOptional.isEmpty()) {
			log.info("Id not present in database");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		prescriptionService.delete(prescriptionOptional.get());
		return new ResponseEntity<>("Prescription deleted", HttpStatus.OK);
	}
	
}
