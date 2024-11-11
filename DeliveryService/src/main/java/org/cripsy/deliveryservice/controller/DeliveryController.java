package org.cripsy.deliveryservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.cripsy.deliveryservice.dto.DeliveryDTO;
import org.cripsy.deliveryservice.service.DeliveryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/delivery")
public class DeliveryController {
    private final DeliveryService deliveryService;

    @GetMapping("/getAll")
    @Operation(summary = "Get All Delivery Persons", description = "Fetch a list of all Delivery Persons.", tags = "Admin")
    public List<DeliveryDTO> getAll() {
        return deliveryService.getAll();
    }
}