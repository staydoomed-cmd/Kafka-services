package com.example.Payment_srvice.service;

import com.example.Payment_srvice.model.dto.OrderCreatedEvent;
import com.example.Payment_srvice.model.dto.OrderPaidEvent;
import com.example.Payment_srvice.model.entity.Payment;
import com.example.Payment_srvice.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

    private final PaymentKafkaProducer paymentKafkaProducer;
    private final PaymentRepository paymentRepository;

    @KafkaListener(
            topics = "new_orders",
            groupId = "payment_group",
            containerFactory = "paymentKafkaListenerFactory",
            concurrency = "${kafka.payment.concurrency:3}"
    )
    public void consumeOrder(OrderCreatedEvent event) {
        log.info("Thread  {} processing order {}",
                Thread.currentThread().getName(), event.getOrderId());

        Payment payment = new Payment();
        payment.setOrderId(event.getOrderId());
        payment.setProduct(event.getProduct());
        payment.setQuantity(event.getQuantity());
        payment.setStatus("PAID");

        Payment savedPayment = paymentRepository.save(payment);

        OrderPaidEvent paidEvent = new OrderPaidEvent();
        paidEvent.setOrderId(savedPayment.getOrderId());
        paidEvent.setProduct(savedPayment.getProduct());
        paidEvent.setQuantity(savedPayment.getQuantity());
        paidEvent.setStatus(savedPayment.getStatus());

        paymentKafkaProducer.sendPaymentToKafka(paidEvent);
    }
}