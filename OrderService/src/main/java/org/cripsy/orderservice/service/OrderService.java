package org.cripsy.orderservice.service;

import org.cripsy.orderservice.dto.DeliveryDTO;

import org.cripsy.orderservice.dto.EmailRequest;
import org.cripsy.orderservice.dto.BestSellingProductDTO;
import org.cripsy.orderservice.dto.MonthlyTotalPriceDTO;
import org.cripsy.orderservice.dto.OrderDTO;
import org.cripsy.orderservice.model.Order;
import org.cripsy.orderservice.repository.ItemRepository;
import org.cripsy.orderservice.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final ModelMapper modelMapper;

    private final RestTemplate restTemplate;

    private static final String EMAIL_SERVICE_URL = "http://localhost:8088/api/email";
    private static final String CUSTOMER_SERVICE_URL = "http://localhost:8081/api/customers/";
    private static final String DELIVERY_SERVICE_URL = "http://localhost:8087/api/delivery";

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

        // orderRepository.save(order);
        // // After order is saved, send an email
        // sendOrderConfirmationEmail(order.getCustomerID(), order.getOrderId());
        // return orderDTO;
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

    // Get Fully Total Price
    public double getTotalSumOfTotalPrice() {
        return orderRepository.getTotalSumOfTotalPrice();
    }

    // Get Monthly Total of Total Price
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

    private void sendOrderConfirmationEmail(Integer customerId, Integer orderId) {
        // Retrieve the customer's email from customerService
        String customerEmail = getCustomerEmail(customerId);

        if (customerEmail == null || customerEmail.isEmpty()) {
            throw new RuntimeException("Customer email not found for customer ID: " + customerId);
        }

        EmailRequest emailRequest = new EmailRequest(
                customerEmail,
                "Order Confirmation",
                "Your order #" + orderId + " has been successfully placed.");

        restTemplate.postForObject(EMAIL_SERVICE_URL, emailRequest, String.class);
    }

    private String getCustomerEmail(Integer customerId) {
        // Call the customerService API to fetch the email
        try {
            String customerServiceEndpoint = CUSTOMER_SERVICE_URL + customerId + "/email";
            return restTemplate.getForObject(customerServiceEndpoint, String.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch email from customerService for customer ID: " + customerId, e);
        }
    }

    public OrderDTO updateOrderStatus(Integer orderId, String orderStatus) {
        // Find the order by ID
        Optional<Order> optionalOrder = orderRepository.findById(orderId);

        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();

            // Update the status
            order.setOrderStatus(orderStatus);

            // Save the updated order
            Order updatedOrder = orderRepository.save(order);

            // Convert to DTO and return
            return modelMapper.map(updatedOrder, OrderDTO.class);
        } else {
            throw new RuntimeException("Order not found for ID: " + orderId);
        }
    }

    /**
     * Assign a delivery person to an order if the order is ready to deliver.
     *
     * @param orderId The ID of the order.
     * @return The updated OrderDTO with the assigned delivery person.
     */
    public OrderDTO assignDeliveryPersonToOrder(Integer orderId) {
        // Find the order by ID
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found for ID: " + orderId));

        // Check if the order status is "ready to deliver"
        if (!"ready to deliver".equalsIgnoreCase(order.getOrderStatus())) {
            throw new RuntimeException("Order is not ready to deliver. Current status: " + order.getOrderStatus());
        }

        // Call DeliveryService to fetch an available delivery person
        DeliveryDTO deliveryPerson = restTemplate.getForObject(
                DELIVERY_SERVICE_URL + "/available",
                DeliveryDTO.class);

        if (deliveryPerson == null || deliveryPerson.getPersonId() == null) {
            throw new RuntimeException("No available delivery person at the moment.");
        }

        // Assign the delivery person to the order
        order.setDeliveryPersonId(deliveryPerson.getPersonId());

        // Update the delivery person's availability by calling DeliveryService API
        restTemplate.put(
                DELIVERY_SERVICE_URL + "/" + deliveryPerson.getPersonId() + "/availability",
                false);

        // Save the updated order
        Order updatedOrder = orderRepository.save(order);

        return modelMapper.map(updatedOrder, OrderDTO.class);
    }

    // Get Monthly Items Qty
    public List<Map<String, Object>> getMonthlyItemQuantityTotal() {
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

    // Get Mothly Total Price
    public List<MonthlyTotalPriceDTO> getMonthlyTotalPrices() {
        return orderRepository.findMonthlyTotalPrices();
    }

    public List<BestSellingProductDTO> getBestSellingProducts() {
        // Call the repository method to fetch data
        return itemRepository.findBestSellingProducts();
    }

}
