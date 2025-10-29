package com.example.Order_srvice.service;

import com.example.Order_srvice.model.dto.OrderCreatedEvent;
import com.example.Order_srvice.model.dto.OrderRequestDTO;
import com.example.Order_srvice.model.dto.OrderResponseDTO;
import com.example.Order_srvice.model.entity.Order;
import com.example.Order_srvice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderKafkaProducer orderKafkaProducer;




    public OrderResponseDTO createOrder(OrderRequestDTO dto) {
        Order order = new Order();
        order.setProduct(dto.getProduct());
        order.setQuantity(dto.getQuantity());
        order.setStatus("NEW");

        Order saved = orderRepository.save(order);
        log.info("New order saved with product {}, quantity{}", order.getProduct(),order.getQuantity());
        OrderCreatedEvent event = mapToEvent(saved);
        orderKafkaProducer.sendOrderToKafka(event);
        return mapToDTO(saved);
    }


    public Optional<OrderResponseDTO> updateStatus(Long id, String status) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        return optionalOrder.map(order -> {
            order.setStatus(status);
            Order updated = orderRepository.save(order);
            return mapToDTO(updated);
        });
    }

    public Optional<OrderResponseDTO> getOrder(Long id) {
        return orderRepository.findById(id).map(this::mapToDTO);
    }


    private OrderResponseDTO mapToDTO(Order order) {
        OrderResponseDTO dto = new OrderResponseDTO();
        dto.setId(order.getId());
        dto.setProduct(order.getProduct());
        dto.setQuantity(order.getQuantity());
        dto.setStatus(order.getStatus());
        return dto;
    }
    private OrderCreatedEvent mapToEvent(Order order) {
        OrderCreatedEvent event = new OrderCreatedEvent();
        event.setOrderId(order.getId());
        event.setProduct(order.getProduct());
        event.setQuantity(order.getQuantity());
        return event;
    }
}