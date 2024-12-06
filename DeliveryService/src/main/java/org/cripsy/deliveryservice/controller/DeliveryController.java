package org.cripsy.deliveryservice.controller;

import lombok.AllArgsConstructor;
import org.cripsy.deliveryservice.dto.DeliveryDTO;
import org.cripsy.deliveryservice.service.DeliveryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/delivery")
public class DeliveryController {

    private final DeliveryService deliveryService;

    @GetMapping("/getAll")
    public List<DeliveryDTO> getAll() {
        return deliveryService.getAllDeliveryPersons();
    }

    @GetMapping("/{id}")
    public DeliveryDTO getDeliveryPersonById(@PathVariable Integer id) {
        return deliveryService.getDeliveryPersonById(id);
    }

    @PostMapping
    public DeliveryDTO createDeliveryPerson(@RequestBody DeliveryDTO deliveryDTO) {
        return deliveryService.createDeliveryPerson(deliveryDTO);
    }

    @PutMapping("/{id}")
    public DeliveryDTO updateDeliveryPerson(@PathVariable Integer id, @RequestBody DeliveryDTO deliveryDTO) {
        return deliveryService.updateDeliveryPerson(id, deliveryDTO);
    }

    @DeleteMapping("/{id}")
    public boolean deleteDeliveryPerson(@PathVariable Integer id) {
        return deliveryService.deleteDeliveryPerson(id);
    }

    /**
     * Get an available delivery person.
     *
     * @return The available DeliveryDTO or 404 if none available.
     */
    @GetMapping("/available")
    public ResponseEntity<DeliveryDTO> getAvailableDeliveryPerson() {
        DeliveryDTO availablePerson = deliveryService.getAvailableDeliveryPerson();
        if (availablePerson == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(availablePerson);
    }

    /**
     * Update the availability of a delivery person.
     *
     * @param id           The ID of the delivery person.
     * @param availability The new availability status.
     * @return Success or error response.
     */
    @PutMapping("/{id}/availability")
    public ResponseEntity<String> updateAvailability(
            @PathVariable Integer id,
            @RequestBody boolean availability) {
        boolean updated = deliveryService.updateAvailability(id, availability);
        if (updated) {
            return ResponseEntity.ok("Availability updated successfully");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Delivery person not found");
    }
}
