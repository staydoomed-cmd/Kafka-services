package com.example.Order_srvice.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderResponseDTO {
    private Long id;
    private String product;
    private Integer quantity;
    private String status;
}
