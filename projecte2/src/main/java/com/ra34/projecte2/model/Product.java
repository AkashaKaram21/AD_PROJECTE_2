package com.ra34.projecte2.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entitat JPA que representa un producte de la botiga
 */
@Entity
@Table(name = "products")
public class Product {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 20)
    private String name;
    
    @Column(length = 100)
    private String description;
    
    @Column(nullable = false)
    private Integer stock;
    
    @Column(nullable = false)
    private Double price;
    
    @Column
    private Double rating;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Condition condition;
    
    @Column(nullable = false)
    private Boolean status = true;
    
    @Column(name = "data_created")
    private LocalDateTime dataCreated;
    
    @Column(name = "data_updated")
    private LocalDateTime dataUpdated;
    
    // Constructor protegido (requerido por JPA)
    public Product() {}
    
    // Constructor completo
    public Product(String name, String description, Integer stock, Double price, 
                   Double rating, Condition condition) {
        this.name = name;
        this.description = description;
        this.stock = stock;
        this.price = price;
        this.rating = rating;
        this.condition = condition;
        this.status = true;
        this.dataCreated = LocalDateTime.now();
        this.dataUpdated = LocalDateTime.now();
    }
    
    // Getters y Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public Integer getStock() {
        return stock;
    }
    
    public void setStock(Integer stock) {
        this.stock = stock;
    }
    
    public Double getPrice() {
        return price;
    }
    
    public void setPrice(Double price) {
        this.price = price;
    }
    
    public Double getRating() {
        return rating;
    }
    
    public void setRating(Double rating) {
        this.rating = rating;
    }
    
    public Condition getCondition() {
        return condition;
    }
    
    public void setCondition(Condition condition) {
        this.condition = condition;
    }
    
    public Boolean getStatus() {
        return status;
    }
    
    public void setStatus(Boolean status) {
        this.status = status;
    }
    
    public LocalDateTime getDataCreated() {
        return dataCreated;
    }
    
    public void setDataCreated(LocalDateTime dataCreated) {
        this.dataCreated = dataCreated;
    }
    
    public LocalDateTime getDataUpdated() {
        return dataUpdated;
    }
    
    public void setDataUpdated(LocalDateTime dataUpdated) {
        this.dataUpdated = dataUpdated;
    }
}