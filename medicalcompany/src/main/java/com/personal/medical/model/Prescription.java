package com.personal.medical.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity(name = "prescriptions")
public class Prescription {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long prescriptionId;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "prescription", cascade = CascadeType.ALL)
	private List<Medicine> medicines = new ArrayList<>();
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "fk_patient_id")
	private Patient patient;
	
	@ManyToOne(cascade = CascadeType.ALL)
	private Employee employee;
	
	@CreationTimestamp
	private Timestamp createdTime;
	
	@UpdateTimestamp
	private Timestamp updatedTime;
	
	public void addMedicine(Medicine medicine) {
		medicine.setPrescription(this);
		this.medicines.add(medicine);
	}
	
	public void removeMedicine(Medicine medicine) {
		this.medicines.remove(medicine);
		medicine.setPrescription(null);
	}
}
