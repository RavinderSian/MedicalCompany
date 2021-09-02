package com.personal.medical.model;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity(name = "medicines")
@EqualsAndHashCode(exclude = "ingredients")
public class Medicine {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long medicineId;
	
	@NotEmpty(message = "Please enter a valid name")
	@Column(name = "name")
	private String name;
	
	@Column(name = "dosage")
	private Integer dosage;
	
	@Column(name = "duration")
	private String duration;
	
	@JsonIgnore
	@OneToMany(mappedBy = "medicine", cascade = CascadeType.ALL)
	private Set<Ingredient> ingredients = new HashSet<>();
	
	@JsonIgnore
	@ManyToOne
	private Prescription prescription;
	
	public void addIngredient(Ingredient ingredient) {
		this.ingredients.add(ingredient);
		ingredient.setMedicine(this);
	}
	
	public void removeIngredient(Ingredient ingredient) {
		this.ingredients.remove(ingredient);
		ingredient.setMedicine(null);
	}
    
	@CreationTimestamp
	private Timestamp createdTime;
	
	@UpdateTimestamp
	private Timestamp updatedTime;
}
