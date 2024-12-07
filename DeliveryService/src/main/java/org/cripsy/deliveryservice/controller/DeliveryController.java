package org.cripsy.deliveryservice.controller;

import lombok.AllArgsConstructor;
import org.cripsy.deliveryservice.dto.AuthDTO;
import org.cripsy.deliveryservice.dto.DeliveryDTO;
import org.cripsy.deliveryservice.service.DeliveryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/delivery")
public class DeliveryController {

    private final DeliveryService deliveryService;


    @PostMapping("login")
    public DeliveryDTO findDeliveryByUsername(@RequestBody AuthDTO authDTO) {
        return deliveryService.findDeliveryByUsername(authDTO.getUsername());

    }

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
}
