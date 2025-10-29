package com.example.Payment_srvice.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderPaidEvent {
    private Long orderId;
    private String status;
    private Integer quantity;
    private String product;

}