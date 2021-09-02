package com.personal.medical.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity(name = "employees")
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long employeeId;
	
	@NotEmpty(message = "Please enter a valid first name")
	@Column(name = "first_name")
	private String firstName;
	
	@NotEmpty(message = "Please enter a valid last name")
	@Column(name = "last_name")
	private String lastName;
	
	@JsonIgnore
	@OneToMany(mappedBy = "employee")
	private List<Prescription> prescriptions = new ArrayList<>();
	
	@CreationTimestamp
	private Timestamp createdTime;
	
	@UpdateTimestamp
	private Timestamp updatedTime;
	
	public void addPrescription(Prescription prescription) {
		prescription.setEmployee(this);
		this.prescriptions.add(prescription);
	}
	
	public void removePrescription(Prescription prescription) {
		prescription.setEmployee(null);
		this.prescriptions.remove(prescription);
	}
	
	
	
	
}