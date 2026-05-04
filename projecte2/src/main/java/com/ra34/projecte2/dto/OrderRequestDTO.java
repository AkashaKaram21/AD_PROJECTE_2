package com.ra34.projecte2.dto;

import java.util.List;

public class OrderRequestDTO {
    private Long customerId;
    private List<OrderItemRequestDTO> items;

    // Constructor
    public OrderRequestDTO() {}

    // Getters y Setters
    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public List<OrderItemRequestDTO> getItems() {
        return items;
    }

    public void setItems(List<OrderItemRequestDTO> items) {
        this.items = items;
    }
}