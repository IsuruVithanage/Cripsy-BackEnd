package org.cripsy.productservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.cripsy.productservice.dto.ReservedStockDTO;
import org.cripsy.productservice.service.ReservedStockService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/product")
public class ReservedStockController {
    private final ReservedStockService reservedStockService;

    @PostMapping("/reserve/initiate")
    @Operation(summary = "Initialize Reservation", description = "Initialize items reservation for list of Products", tags = "Reserve")
    public Integer initializeReservation (@RequestBody Map<Integer, Integer> reservationDetails){
        return reservedStockService.initializeReservation(reservationDetails);
    }


    @PostMapping("/reserve/confirm/{transactionId}")
    @Operation(summary = "Confirm Reservation", description = "Confirm items reservation for an Initialized order", tags = "Reserve")
    public List<ReservedStockDTO> confirmReservation(@PathVariable Integer transactionId) {
        return reservedStockService.confirmReservation(transactionId);
    }

    @PostMapping("/reserve/cancel/{transactionId}")
    @Operation(summary = "Cancel Reservation", description = "Cancel items reservation for an Initialized order", tags = "Reserve")
    public String cancelReservation(@PathVariable Integer transactionId) {
        return reservedStockService.cancelReservation(transactionId);
    }

}
