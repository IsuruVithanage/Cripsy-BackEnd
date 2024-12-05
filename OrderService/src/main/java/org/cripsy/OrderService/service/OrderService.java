package org.cripsy.OrderService.service;

import org.cripsy.OrderService.dto.ItemDTO;
import org.cripsy.OrderService.dto.OrderDTO;
import org.cripsy.OrderService.model.Item;
import org.cripsy.OrderService.model.Order;
import org.cripsy.OrderService.repository.ItemRepository;
import org.cripsy.OrderService.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
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

        // Map and save items
        List<Item> items = orderDTO.getItems().stream()
                .map(itemDTO -> {
                    Item item = modelMapper.map(itemDTO, Item.class);
                    item.setOrder(order);
                    return item;
                }).collect(Collectors.toList());

        order.setItems(items);

        Order savedOrder = orderRepository.save(order);
        return modelMapper.map(savedOrder, OrderDTO.class);
    }

    public OrderDTO updateOrder(Integer id, OrderDTO orderDTO) {
        if (orderRepository.existsById(id)) {
            Order order = modelMapper.map(orderDTO, Order.class);

            // Map and update items
            List<Item> items = orderDTO.getItems().stream()
                    .map(itemDTO -> {
                        Item item = modelMapper.map(itemDTO, Item.class);
                        item.setOrder(order);
                        return item;
                    }).collect(Collectors.toList());

            order.setItems(items);
            order.setOrderId(id);
            Order updatedOrder = orderRepository.save(order);
            return modelMapper.map(updatedOrder, OrderDTO.class);
        }
        return null;
    }

    public void deleteOrder(Integer id) {
        orderRepository.deleteById(id);
    }
}
