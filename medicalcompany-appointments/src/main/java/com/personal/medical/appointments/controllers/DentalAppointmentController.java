package com.personal.medical.appointments.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.ResourceAccessException;

import com.personal.medical.appointments.model.DentalAppointment;
import com.personal.medical.appointments.services.DentalAppointmentService;

@RestController
@RequestMapping("dentalappointments/")
public class DentalAppointmentController implements CrudController<DentalAppointment, Long>{

	private final DentalAppointmentService service;

	public DentalAppointmentController(DentalAppointmentService service) {
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
	public ResponseEntity<?> add(DentalAppointment dentalAppointment, BindingResult bindingResult) {
		
		try {
			if(!service.checkIfPatientOrDentistExists(dentalAppointment)) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}	
		}catch (ResourceAccessException exception) {
			return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
		}
		
		if (bindingResult.hasFieldErrors()) {
			Map<String, String> errorMap = new HashMap<>();
			bindingResult.getFieldErrors().forEach(error -> errorMap.put(error.getField(), error.getDefaultMessage()));
			return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
		}
		
		service.save(dentalAppointment);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@DeleteMapping("delete/dentist/{dentistId}")
	public ResponseEntity<?> deleteAppointmentsForDentist(@PathVariable Long dentistId){
		
		service.deleteAppointmentsForGivenDentistId(dentistId);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@DeleteMapping("delete/patient/{patientId}")
	public ResponseEntity<?> deleteAppointmentsForPatient(@PathVariable Long patientId){
		
		service.deleteAppointmentsForGivenPatientId(patientId);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
