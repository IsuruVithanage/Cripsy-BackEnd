package org.cripsy.OrderService.controller;

import org.cripsy.OrderService.dto.OrderDTO;
import org.cripsy.OrderService.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import org.cripsy.OrderService.dto.MailBody;
import org.cripsy.OrderService.service.EmailService;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final EmailService emailService; // Inject the EmailService to send emails

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

    /**
     * Endpoint to update order status and send a confirmation email.
     */
    // @PutMapping("/confirmOrder/{id}")
    // public ResponseEntity<String> confirmOrder(@PathVariable Integer id) {
    // // Fetch and update the order's status
    // OrderDTO updatedOrder = orderService.updateOrderStatus(id, "confirmed");

    // // Send a confirmation email to the user
    // MailBody mailBody = MailBody.builder()
    // .to(updatedOrder.getCustomerEmail()) // Assuming OrderDTO contains
    // customerEmail
    // .subject("Order Confirmation")
    // .body("Dear " + updatedOrder.getCustomerName() + ",\n\n" +
    // "Your order with ID " + updatedOrder.getOrderId() + " has been successfully
    // confirmed.\n\n" +
    // "Thank you for shopping with us!\n\n" +
    // "Best regards,\n" +
    // "Team Crispy")
    // .build();

    // emailService.sendSimpleMail(mailBody);

    // return ResponseEntity.ok("Order confirmed and confirmation email sent to " +
    // updatedOrder.getCustomerEmail());
    // }

}
