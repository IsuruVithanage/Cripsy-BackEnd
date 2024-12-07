package org.cripsy.orderservice.service;

import org.cripsy.orderservice.dto.BestSellingProductDTO;
import org.cripsy.orderservice.dto.MonthlyTotalPriceDTO;
import org.cripsy.orderservice.dto.OrderDTO;
import org.cripsy.orderservice.model.Order;
import org.cripsy.orderservice.repository.ItemRepository;
import org.cripsy.orderservice.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private  final ItemRepository  itemRepository;
    private final ModelMapper modelMapper;

    public List<OrderDTO> getAllOrders() {
        List<Order> orderList = orderRepository.findAll();
        return orderList.stream()
                .map(order -> modelMapper.map(order, OrderDTO.class))
                .toList();
    }

    public OrderDTO getOrderById(Integer id) {
        Optional<Order> order = orderRepository.findById(id);
        return order.map(value -> modelMapper.map(value, OrderDTO.class)).orElse(null);
    }

    public OrderDTO createOrder(OrderDTO orderDTO) {

        Order order = modelMapper.map(orderDTO, Order.class);

        Order savedOrder = orderRepository.save(order);
        return modelMapper.map(savedOrder, OrderDTO.class);
    }

    public List<OrderDTO> getOrdersByStatus(String status) {
        List<Order> orders = orderRepository.findByOrderStatus(status);
        return orders.stream()
                .map(order -> modelMapper.map(order, OrderDTO.class))
                .collect(Collectors.toList());

    }

    public OrderDTO updateOrder(Integer id, OrderDTO orderDTO) {
        if (orderRepository.existsById(id)) {
            Order order = modelMapper.map(orderDTO, Order.class);
            order.setOrderId(id);
            Order updatedOrder = orderRepository.save(order);
            return modelMapper.map(updatedOrder, OrderDTO.class);
        }
        return null;
    }




    public void deleteOrder(Integer id) {
        orderRepository.deleteById(id);
    }


    //Get Total Price Summary
    public List<Map<String, Object>> getMonthlyTotalSumOfTotalPrice() {
        Double thisMonthTotalPrice = orderRepository.getTotalPriceForCurrentMonth();
        Double lastMonthTotalPrice = orderRepository.getTotalPriceForLastMonth();

        // Default to 0.0 if null
        thisMonthTotalPrice = thisMonthTotalPrice != null ? thisMonthTotalPrice : 0.0;
        lastMonthTotalPrice = lastMonthTotalPrice != null ? lastMonthTotalPrice : 0.0;

        // Calculate the percentage difference
        double percentageDifference = 0.0;
        if (lastMonthTotalPrice > 0) {
            percentageDifference = ((thisMonthTotalPrice - lastMonthTotalPrice) / lastMonthTotalPrice) * 100;
        } else if (thisMonthTotalPrice > 0) {
            percentageDifference = 100.0; // If last month was 0 and this month has price, difference is 100%
        }

        // Prepare response map
        Map<String, Object> statsMap = new HashMap<>();
        statsMap.put("thisMonthTotalPrice", thisMonthTotalPrice);
        statsMap.put("lastMonthTotalPrice", lastMonthTotalPrice);
        statsMap.put("percentageDifference", percentageDifference);

        // Wrap in a list and return
        List<Map<String, Object>> stats = new ArrayList<>();
        stats.add(statsMap);

        return stats;
    }


    //Get Total Qty Summary
    public List<Map<String, Object>> getMonthlySumQty() {
        Long thisMonthQuantity = orderRepository.getTotalItemQuantityForCurrentMonth();
        Long lastMonthQuantity = orderRepository.getTotalItemQuantityForLastMonth();

        // Default to 0 if null
        thisMonthQuantity = thisMonthQuantity != null ? thisMonthQuantity : 0L;
        lastMonthQuantity = lastMonthQuantity != null ? lastMonthQuantity : 0L;

        // Calculate the percentage difference
        double percentageDifference = 0.0;
        if (lastMonthQuantity > 0) {
            percentageDifference = ((double) (thisMonthQuantity - lastMonthQuantity) / lastMonthQuantity) * 100;
        } else if (thisMonthQuantity > 0) {
            percentageDifference = 100.0; // If last month was 0 and this month has quantity, difference is 100%
        }

        // Prepare response map
        Map<String, Object> statsMap = new HashMap<>();
        statsMap.put("thisMonthQuantity", thisMonthQuantity);
        statsMap.put("lastMonthQuantity", lastMonthQuantity);
        statsMap.put("percentageDifference", percentageDifference);

        // Wrap in a list and return
        List<Map<String, Object>> stats = new ArrayList<>();
        stats.add(statsMap);

        return stats;
    }


    //Get Order Summary
    public List<Map<String, Object>> getOrderStats() {
        long thisMonthOrders = orderRepository.getTotalOrdersForCurrentMonth();
        long lastMonthOrders = orderRepository.getTotalOrdersForLastMonth();

        // Calculate the difference percentage
        double percentageDifference = 0;
        if (lastMonthOrders > 0) {
            percentageDifference = ((double) (thisMonthOrders - lastMonthOrders) / lastMonthOrders) * 100;
        }

        // Prepare response
        Map<String, Object> statsMap = new HashMap<>();
        statsMap.put("thisMonthOrders", thisMonthOrders);
        statsMap.put("lastMonthOrders", lastMonthOrders);
        statsMap.put("percentageDifference", percentageDifference);

        // Create a list and add the map
        List<Map<String, Object>> stats = new ArrayList<>();
        stats.add(statsMap);

        return stats;
    }

    //Get Monthly Total Selling(Chart Data)
    public List<MonthlyTotalPriceDTO> findMonthlyTotalPrices() {
        return orderRepository.findMonthlyTotalPrices();
    }



    //Get Best Selling Details
    public List<BestSellingProductDTO> getBestSellingProducts() {
        // Call the repository method to fetch data
        return itemRepository.findBestSellingProducts();
    }




}
