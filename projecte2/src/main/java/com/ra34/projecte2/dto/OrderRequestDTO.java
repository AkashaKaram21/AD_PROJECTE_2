package com.ra34.projecte2.dto;

import java.util.List;

public class OrderRequestDTO {
    private Long customerId;
    private List<Long> productIds;

    public OrderRequestDTO() {}

    // Getters y Setters
    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }
    public List<Long> getProductIds() { return productIds; }
    public void setProductIds(List<Long> productIds) { this.productIds = productIds; }
}