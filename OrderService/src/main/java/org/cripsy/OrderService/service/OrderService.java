package org.cripsy.OrderService.service;

import org.cripsy.OrderService.dto.OrderDTO;
import org.cripsy.OrderService.dto.UserDTO;
import org.cripsy.OrderService.model.Order;
import org.cripsy.OrderService.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import org.cripsy.OrderService.dto.MailBody;
import org.cripsy.OrderService.service.EmailService;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;
    private final EmailService emailService; // Inject EmailService to send emails
    private final RestTemplate restTemplate;

    private final String USER_SERVICE_URL = "http://localhost:8081/api/customers"; // Replace with actual User Service
                                                                                   // URL

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

    // /**
    // * Update the status of an order and send a confirmation email
    // */
    // public OrderDTO updateOrderStatus(Integer id, String orderStatus) {
    // Optional<Order> optionalOrder = orderRepository.findById(id);

    // if (optionalOrder.isPresent()) {
    // Order order = optionalOrder.get();
    // order.setStatus(orderStatus); // Update the status

    // // Save the updated order
    // Order updatedOrder = orderRepository.save(order);
    // OrderDTO orderDTO = modelMapper.map(updatedOrder, OrderDTO.class);

    // // Send confirmation email only if the status is "confirmed"
    // if ("confirmed".equalsIgnoreCase(orderStatus)) {
    // sendConfirmationEmail(orderDTO);
    // }

    // return orderDTO;
    // }

    // throw new RuntimeException("Order not found with ID: " + id);
    // }

    // /**
    // * Sends a confirmation email to the customer
    // */
    // private void sendConfirmationEmail(OrderDTO orderDTO) {
    // // Prepare the email body
    // MailBody mailBody = MailBody.builder()
    // .to(orderDTO.getCustomerEmail()) // Assuming OrderDTO contains customerEmail
    // .subject("Order Confirmation")
    // .body("Dear " + orderDTO.getCustomerName() + ",\n\n" +
    // "Your order with ID " + orderDTO.getOrderId() + " has been successfully
    // confirmed.\n\n" +
    // "Thank you for shopping with us!\n\n" +
    // "Best regards,\n" +
    // "Team Crispy")
    // .build();

    // // Send the email using the EmailService
    // emailService.sendSimpleMail(mailBody);
    // }
    public OrderDTO updateOrderStatus(Integer id, String status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with ID " + id));

        order.setStatus(status);

        if ("confirmed".equalsIgnoreCase(status)) {
            // Send confirmation email
            MailBody mailBody = MailBody.builder()
                    .to(order.getCustomerEmail())
                    .subject("Order Confirmation")
                    .body("Dear " + order.getCustomerName() + ",\n\nYour order #" + order.getOrderId()
                            + " has been confirmed!")
                    .build();

            emailService.sendSimpleMail(mailBody);
        }

        Order updatedOrder = orderRepository.save(order);
        return modelMapper.map(updatedOrder, OrderDTO.class);
    }

    // Helper Method to Call User Service
    private UserDTO getUserDetails(Integer customerId) {
        String url = USER_SERVICE_URL + "/" + customerId; // Assuming User Service has an endpoint: GET /api/users/{id}
        return restTemplate.getForObject(url, UserDTO.class);
    }

}
