package com.nt.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Product {

	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	private Integer id;
	
	private String Name;
	
	private String brand;
	
	private String category;
	
	private Double price;
	
	private String description;
	
	private LocalDateTime createdAt;
	
	private String imageFileName;
	
}
