package com.personal.medical.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class DentalPatient {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message = "Please enter a valid first name")
	@Column(name = "first_name")
	private String firstName;
	
	@NotEmpty(message = "Please enter a valid last name")
	@Column(name = "last_name")
	private String lastName;
	
	@Column(name = "date_joined")
	private LocalDateTime joinDate;
}
