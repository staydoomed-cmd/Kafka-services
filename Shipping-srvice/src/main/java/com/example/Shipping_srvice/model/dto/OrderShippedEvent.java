package com.example.Shipping_srvice.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderShippedEvent {
    private Long orderId;
    private String product;
    private Integer quantity;
    private String status;
}
