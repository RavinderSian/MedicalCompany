package com.personal.medical.appointments.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.personal.medical.appointments.model.DoctorAppointment;
import com.personal.medical.appointments.services.DoctorAppointmentService;

@RestController
@RequestMapping("doctorappointments/")
public class DoctorAppointmentController implements CrudController<DoctorAppointment, Long>{

	private final DoctorAppointmentService service;
	
	public DoctorAppointmentController(DoctorAppointmentService service) {
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
		} 
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@Override
	public ResponseEntity<?> add(@Valid DoctorAppointment doctorAppointment, BindingResult bindingResult) {
		
		if (bindingResult.hasFieldErrors()) {
			Map<String, String> errorMap = new HashMap<>();
			bindingResult.getFieldErrors().forEach(error -> errorMap.put(error.getField(), error.getDefaultMessage()));
			return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
		} 
		service.save(doctorAppointment);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
