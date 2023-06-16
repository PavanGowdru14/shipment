package org.jsp.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Shipment {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String customerName;
	private String customerAddress;
	private Double weight;
	private String dimensions;
	private String deliveryAddress;

	// getters and setters
}