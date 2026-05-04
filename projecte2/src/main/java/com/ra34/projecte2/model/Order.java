package com.ra34.projecte2.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders") // "order" és paraula reservada SQL → obligatori canviar el nom
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus = OrderStatus.PENDING;

    private Double totalAmounts = 0.0;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime dataCreated;

    // cascade ALL + orphanRemoval: els items no existeixen sense la comanda
    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    // Constructor
    public Order() {}

    public Order(Customer customer) {
        this.customer = customer;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
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

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}