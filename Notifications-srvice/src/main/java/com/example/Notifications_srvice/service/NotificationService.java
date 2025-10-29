package com.example.Notifications_srvice.service;

import com.example.Notifications_srvice.dto.OrderShippedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {



    @KafkaListener(topics = "sent_orders", groupId = "notification_group", containerFactory = "kafkaListenerContainerFactory")
    public void consumePaymentEvent(OrderShippedEvent event) {
    log.info("Notification about order with id {} and product {} sent",event.getOrderId(),event.getProduct());


    }
}