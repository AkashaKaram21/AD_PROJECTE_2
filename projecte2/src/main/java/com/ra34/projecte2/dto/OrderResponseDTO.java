package com.ra34.projecte2.dto;

import com.ra34.projecte2.model.OrderStatus;
import java.time.LocalDateTime;
import java.util.List;

public class OrderResponseDTO {
    private Long id;
    private Long customerId;
    private OrderStatus orderStatus;
    private Double totalAmounts;
    private LocalDateTime dataCreated;
    private List<OrderItemResponseDTO> orderItems;

    // Constructor
    public OrderResponseDTO() {}

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Double getTotalAmounts() {
        return totalAmounts;
    }

    public void setTotalAmounts(Double totalAmounts) {
        this.totalAmounts = totalAmounts;
    }

    public LocalDateTime getDataCreated() {
        return dataCreated;
    }

    public void setDataCreated(LocalDateTime dataCreated) {
        this.dataCreated = dataCreated;
    }

    public List<OrderItemResponseDTO> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemResponseDTO> orderItems) {
        this.orderItems = orderItems;
    }
}