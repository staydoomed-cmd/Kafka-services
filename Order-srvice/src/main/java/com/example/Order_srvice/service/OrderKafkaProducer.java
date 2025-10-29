package com.example.Order_srvice.service;

import com.example.Order_srvice.model.dto.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderKafkaProducer {
    private final KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate;

    public void sendOrderToKafka(OrderCreatedEvent orderCreatedEvent){
        kafkaTemplate.send("new_orders", orderCreatedEvent.getOrderId().toString(),orderCreatedEvent);
        log.info("Order with id {} sent to new_orders",orderCreatedEvent.getOrderId());
    }
}
