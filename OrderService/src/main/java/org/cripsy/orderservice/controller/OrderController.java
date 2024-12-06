package org.cripsy.orderservice.controller;

import org.cripsy.orderservice.dto.OrderDTO;
import org.cripsy.orderservice.service.OrderService;
import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/getAllOrders")
    public List<OrderDTO> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    public OrderDTO getOrderById(@PathVariable Integer id) {
        return orderService.getOrderById(id);
    }

    @PostMapping("/createOrder")
    public OrderDTO createOrder(@RequestBody OrderDTO orderDTO) {
        return orderService.createOrder(orderDTO);
    }

    @PutMapping("/updateOrder/{id}")
    public OrderDTO updateOrder(@PathVariable Integer id, @RequestBody OrderDTO orderDTO) {
        return orderService.updateOrder(id, orderDTO);
    }

    @DeleteMapping("/deleteOrder/{id}")
    public void deleteOrder(@PathVariable Integer id) {
        orderService.deleteOrder(id);
    }

    @GetMapping("/getSumTotal")
    public Double getTotalSumOfTotalPrice() {
        return orderService.getTotalSumOfTotalPrice();
    }

    @GetMapping("/getMonthlySumTotal")
    public List<Map<String, Object>> getMonthlyTotalSumOfTotalPrice() {
        return orderService.getMonthlyTotalSumOfTotalPrice();
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<OrderDTO> updateOrderStatus(@PathVariable Integer id, @RequestParam String orderStatus) {
        OrderDTO updatedOrder = orderService.updateOrderStatus(id, orderStatus);
        return ResponseEntity.ok(updatedOrder);
    }
}
