package com.personal.medical.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity(name = "appointments")
public class DentalAppointment {

	@Column(name = "description")
	private String description;
	
	@Column(name = "booking_time_date")
	private LocalDateTime bookingDateTime;
	
}
