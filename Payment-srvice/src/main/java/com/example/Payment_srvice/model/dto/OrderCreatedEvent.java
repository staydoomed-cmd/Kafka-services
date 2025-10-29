package com.example.Payment_srvice.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderCreatedEvent {
    private Long orderId;
    private String product;
    private Integer quantity;
}