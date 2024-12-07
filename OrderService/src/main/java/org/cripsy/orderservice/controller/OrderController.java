package org.cripsy.orderservice.controller;

import org.cripsy.orderservice.dto.BestSellingProductDTO;
import org.cripsy.orderservice.dto.MonthlyTotalPriceDTO;
import org.cripsy.orderservice.dto.OrderDTO;
import org.cripsy.orderservice.service.OrderService;
import lombok.AllArgsConstructor;
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

//    @GetMapping("/getSumTotal")
//    public Double getTotalSumOfTotalPrice() {
//        return orderService.getTotalSumOfTotalPrice();
//    }

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
    public List<OrderDTO> getOrdersByStatus(@PathVariable String status) {
        return orderService.getOrdersByStatus(status);
    }



}
