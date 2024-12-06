package org.cripsy.orderservice.service;

import org.cripsy.orderservice.dto.DeliveryDTO;

import org.cripsy.orderservice.dto.EmailRequest;
import org.cripsy.orderservice.dto.OrderDTO;
import org.cripsy.orderservice.model.Item;
import org.cripsy.orderservice.model.Order;
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
        Order order = new Order();
        order.setCustomerID(orderDTO.getCustomerID());

        List<Item> items = orderDTO.getItems().stream().map(itemDTO -> {
            Item item = new Item();
            item.setProductId(itemDTO.getProductId());
            item.setQuantity(itemDTO.getQuantity());
            item.setPrice(itemDTO.getPrice());
            item.setDiscount(itemDTO.getDiscount());
            item.setOrder(order);
            return item;
        }).collect(Collectors.toList());

        order.setItems(items);

        orderRepository.save(order);
        // After order is saved, send an email
        sendOrderConfirmationEmail(order.getCustomerID(), order.getOrderId());
        return orderDTO;
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

    public double getTotalSumOfTotalPrice() {
        return orderRepository.getTotalSumOfTotalPrice();
    }

    public List<Map<String, Object>> getMonthlyTotalSumOfTotalPrice() {
        List<Object[]> results = orderRepository.getMonthlyTotalSumOfTotalPrice();
        List<Map<String, Object>> monthlySums = new ArrayList<>();

        for (Object[] result : results) {
            Map<String, Object> monthData = new HashMap<>();
            monthData.put("year", result[0]); // Year
            monthData.put("month", result[1]); // Month
            monthData.put("total", result[2]); // Total sum for that month
            monthlySums.add(monthData);
        }

        return monthlySums;
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

}
