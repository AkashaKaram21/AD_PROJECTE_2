package com.ra34.projecte2.dto;

import com.ra34.projecte2.model.ProductCondition;

/**
 * DTO de resposta per protegir informació sensible
 */
public class ProductResponseDTO {

    private Long id;
    private String name;
    private String description;
    private Integer stock;
    private Double price;
    private Double rating;
    private ProductCondition condition;

    public ProductResponseDTO() {}

    public ProductResponseDTO(Long id, String name, String description, Integer stock,
                            Double price, Double rating, ProductCondition condition) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.stock = stock;
        this.price = price;
        this.rating = rating;
        this.condition = condition;
    }

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

    public ProductCondition getCondition() {
        return condition;
    }

    public void setCondition(ProductCondition condition) {
        this.condition = condition;
    }

    
}