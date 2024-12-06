package org.cripsy.orderservice.service;

import org.cripsy.orderservice.dto.OrderDTO;
import org.cripsy.orderservice.model.Item;
import org.cripsy.orderservice.model.Order;
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

}
