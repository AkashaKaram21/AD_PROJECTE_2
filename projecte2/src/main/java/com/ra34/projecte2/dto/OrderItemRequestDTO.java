package com.ra34.projecte2.dto;

public class OrderItemRequestDTO {
    private Long productId;
    private Integer quantity;

    // Constructor
    public OrderItemRequestDTO() {}

    // Getters y Setters
    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}