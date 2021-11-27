package com.personal.medical.appointments.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@EqualsAndHashCode(callSuper=false)
public class DentalAppointment extends Appointment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull(message = "provide a dentist id")
	@Column(name = "dentist_id")
	private Long dentistId;
	
	@NotNull(message = "provide a patient id")
	@Column(name = "patient_id")
	private Long patientId;

}
