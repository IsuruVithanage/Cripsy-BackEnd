package org.cripsy.orderservice.controller;

import org.cripsy.orderservice.dto.BestSellingProductDTO;
import org.cripsy.orderservice.dto.MonthlyTotalPriceDTO;
import org.cripsy.orderservice.dto.OrderDTO;
import org.cripsy.orderservice.dto.OrderDetailDTO;
import org.cripsy.orderservice.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/orders")
@CrossOrigin("http://localhost:3000")
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/getAllOrders")
    public List<OrderDetailDTO> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    public OrderDetailDTO getOrderById(@PathVariable Integer id) {
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



    //Get Total Price Summary
    @GetMapping("/getMonthlySumTotal")
    public List<Map<String, Object>> getMonthlyTotalSumOfTotalPrice() {
        return orderService.getMonthlyTotalSumOfTotalPrice();
    }

    //Get Total Qty Summary
    @GetMapping("/getMonthlySumQty")
    public List<Map<String, Object>> getMonthlySumQty() {
        return orderService.getMonthlySumQty();
    }

    //Get Order Summary
    @GetMapping("/best-orderSummery")
    public List<Map<String, Object>> getOrderStats() {
        return orderService.getOrderStats();
    }

    //Get Best Selling Details
    @GetMapping("/best-selling")
    public List<BestSellingProductDTO> getBestSellingProducts() {
        return orderService.getBestSellingProducts();
    }

    //Get Monthly Selling Details(Chart Data)
    @GetMapping("/monthly-selling")
    public List<MonthlyTotalPriceDTO> findMonthlyTotalPrices() {
        return orderService.findMonthlyTotalPrices();
    }

    

    //Order States
    @GetMapping("/status/{status}")
    public List<OrderDetailDTO> getOrdersByStatus(@PathVariable String status) {
        return orderService.getOrdersByStatus(status);
    }
    @GetMapping("/getAllByCustomer/{customerID}")
    public List<OrderDetailDTO> getAllByCustomerId(@PathVariable Integer customerID) {
        return orderService.getAllByCustomerId(customerID);
    }

    @GetMapping("/getAllByDeliveryPersonId/{deliveryPersonId}")
    public List<OrderDetailDTO> getAllByDeliveryPersonId(@PathVariable Integer deliveryPersonId) {
        return orderService.getAllByDeliveryId(deliveryPersonId);
    }

    @PutMapping("/updateStatus/{orderId}/{newStatus}")
    public ResponseEntity<String> updateOrderStatus(
            @PathVariable Integer orderId,
            @PathVariable String newStatus) {
        try {
            boolean isUpdated = orderService.updateOrderStatus(orderId, newStatus);
            if (isUpdated) {
                return ResponseEntity.ok("Order status updated successfully!");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found!");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating order status!");
        }
    }

}
