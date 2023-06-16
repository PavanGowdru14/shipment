package org.jsp.backend.service;

import java.util.List;
import java.util.Optional;

import org.jsp.backend.model.Shipment;
import org.jsp.backend.repository.ShipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShipmentService {

	private final ShipmentRepository shipmentRepository;

	@Autowired
	public ShipmentService(ShipmentRepository shipmentRepository) {
		this.shipmentRepository = shipmentRepository;
	}

	public Shipment saveShipment(Shipment shipment) {
		return shipmentRepository.save(shipment);
	}

	public List<Shipment> getAllShipments() {
		return shipmentRepository.findAll();
	}

	public Shipment updateShipment(Long id, Shipment updatedShipment) {
		Optional<Shipment> optionalShipment = shipmentRepository.findById(id);
		if (optionalShipment.isPresent()) {
			Shipment existingShipment = optionalShipment.get();
			existingShipment.setCustomerName(updatedShipment.getCustomerName());
			existingShipment.setCustomerAddress(updatedShipment.getCustomerAddress());
			existingShipment.setWeight(updatedShipment.getWeight());
			existingShipment.setDimensions(updatedShipment.getDimensions());
			existingShipment.setDeliveryAddress(updatedShipment.getDeliveryAddress());
			return shipmentRepository.save(existingShipment);
		}
		return null;
	}

	public void deleteShipment(Long id) {
		shipmentRepository.deleteById(id);
	}
}