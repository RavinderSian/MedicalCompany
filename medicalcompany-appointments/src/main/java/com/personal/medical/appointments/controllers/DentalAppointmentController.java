package com.personal.medical.appointments.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.personal.medical.appointments.model.DentalAppointment;
import com.personal.medical.appointments.services.DentalAppointmentService;

@RestController
@RequestMapping("dentalappointments/")
public class DentalAppointmentController implements CrudController<DentalAppointment, Long>{

	private final DentalAppointmentService service;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Bean
	public RestTemplate restTemplate() {
	    return new RestTemplate();
	}
	
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
		
		if (bindingResult.hasFieldErrors()) {
			Map<String, String> errorMap = new HashMap<>();
			bindingResult.getFieldErrors().forEach(error -> errorMap.put(error.getField(), error.getDefaultMessage()));
			return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
		}
		
		try {
			restTemplate.exchange("http://localhost:8080/patient/" + dentalAppointment.getPatientId(), HttpMethod.GET, new HttpEntity<>(null, null), String.class);
		} catch(HttpClientErrorException.NotFound httpClientErrorException) {
			return new ResponseEntity<>("Patient id not found", httpClientErrorException.getStatusCode());
		}
		
		try {
			restTemplate.exchange("http://localhost:8080/dentist/" + dentalAppointment.getDentistId(), HttpMethod.GET, new HttpEntity<>(null, null), String.class);
		} catch(HttpClientErrorException.NotFound httpClientErrorException) {
			return new ResponseEntity<>("Dentist id not found", httpClientErrorException.getStatusCode());
		}
		
		service.save(dentalAppointment);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
