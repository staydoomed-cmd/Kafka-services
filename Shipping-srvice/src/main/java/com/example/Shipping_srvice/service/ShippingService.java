package com.example.Shipping_srvice.service;

import com.example.Shipping_srvice.model.dto.OrderPaidEvent;
import com.example.Shipping_srvice.model.dto.OrderShippedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShippingService {

    private final KafkaTemplate<String, OrderShippedEvent> kafkaTemplate;

    @KafkaListener(topics = "payed_orders", groupId = "shipping_group", containerFactory = "kafkaListenerContainerFactory")
    public void consumePaymentEvent(OrderPaidEvent event) {
        log.info("Payed order with  id {} consumed ", event.getOrderId());

        OrderShippedEvent shippedEvent = new OrderShippedEvent();
        shippedEvent.setOrderId(event.getOrderId());
        shippedEvent.setProduct(event.getProduct());
        shippedEvent.setQuantity(event.getQuantity());
        shippedEvent.setStatus("SENT");

        kafkaTemplate.send("sent_orders", shippedEvent);
        log.info("Shipped event  with  id {} sent ", shippedEvent.getOrderId());

    }
}