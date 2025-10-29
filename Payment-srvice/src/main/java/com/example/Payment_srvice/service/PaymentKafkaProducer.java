package com.example.Payment_srvice.service;

import com.example.Payment_srvice.model.dto.OrderPaidEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentKafkaProducer {

    private final KafkaTemplate<String, OrderPaidEvent> kafkaTemplate;

    public void sendPaymentToKafka(OrderPaidEvent event) {
        kafkaTemplate.send("payed_orders", event);
        log.info("Payment event sent to Kafka for order{}", event.getOrderId());

    }
}
