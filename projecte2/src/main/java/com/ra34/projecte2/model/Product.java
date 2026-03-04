package com.ra34.projecte2.model;

import java.time.LocalDateTime;

import jakarta.annotation.Generated;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Product {

    //Classe per fer lo de enum
    public enum Condition {
        nou, bon_estat, acceptable, mal_estat
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private String description;
    private int stock;
    private double price;
    //En java el tipus double no et permet null
    //per això tenim el Double que et permet fer ho
    private Double rating;          
    private Condition condition;
    //per defecte es false 
    private boolean status = true; 
    private LocalDateTime dataCreated;
    private LocalDateTime dataUpdated;

    //Contructor vació
    public Product() {
    }

    

    public Product(long id, String name, String description, int stock, double price, Double rating,
            Condition condition, boolean status, LocalDateTime dataCreated, LocalDateTime dataUpdated) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.stock = stock;
        this.price = price;
        this.rating = rating;
        this.condition = condition;
        this.status = status;
        this.dataCreated = dataCreated;
        this.dataUpdated = dataUpdated;
    }

    //Getter y Setters 
    public long getId() {
        return id;
    }
    public void setId(long id) {
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
    public int getStock() {
        return stock;
    }
    public void setStock(int stock) {
        this.stock = stock;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
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
    public boolean isStatus() {
        return status;
    }
    public void setStatus(boolean status) {
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
