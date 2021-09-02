package com.personal.medical.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

@Data
@Entity
public class Ingredient {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ingredientId;
	
	@NotEmpty(message = "Please enter a valid name")
	@Column(name = "name")
	private String name;
	
	@CreationTimestamp
	private Timestamp createdTime;
	
	@UpdateTimestamp
	private Timestamp updatedTime;
	
	@ManyToOne
	private Medicine medicine;
}
