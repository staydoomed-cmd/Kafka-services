package com.example.Order_srvice.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequestDTO {
    private String product;
    private Integer quantity;
}
