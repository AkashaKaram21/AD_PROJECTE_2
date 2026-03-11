package com.ra34.projecte2.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import org.antlr.v4.runtime.misc.NotNull;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    /** Nom del producte: 
     * obligatori, màxim 20 caràcters */
    @Column(nullable = false, length = 20)
    private String name;

    /** Descripció del producte:
     *  màxim 100 caràcters */
    @Column(length = 100)
    @Size(max = 100, message = "La descripció no pot superar els 100 caràcters")
    private String description;

    /** Estoc disponible: 
     * obligatori */
    @Column(nullable = false)
    @NotNull(message = "L'estoc és obligatori")
    @Min(value = 0, message = "L'estoc no pot ser negatiu")
    private Integer stock;

    /** Preu del producte: 
     * obligatori */
    @Column(nullable = false)
    @NotNull(message = "El preu és obligatori")
    @DecimalMin(value = "0.0", inclusive = false, message = "El preu ha de ser positiu")
    private Double price;

    /** Valoració del producte: 
     * de 0 a 5 */
    @Column
    @DecimalMin(value = "0.0", message = "La valoració mínima és 0")
    @DecimalMax(value = "5.0", message = "La valoració màxima és 5")
    private Double rating;


    /**
     * Condició física del producte és guarda dins d'enum
     */
    @Enumerated(EnumType.STRING)
    @Column
    private ProductCondition condition;


    /**
     * Camp de borrat lògic.
     * true = producte actiu / false = producte inactiu.
     */
    @Column(nullable = false)
    private Boolean status = true;


    /** Data i hora de creació del registre */
    @Column(name = "data_created", updatable = false)
    private LocalDateTime dataCreated;

    /** Data i hora de l'última modificació del registre */
    @Column(name = "data_updated")
    private LocalDateTime dataUpdated;

    //Constructor
    public Product() {
    }

    public Product(Long id, String name, String description, Integer stock, Double price, Double rating,
            ProductCondition condition, Boolean status, LocalDateTime dataCreated, LocalDateTime dataUpdated) {
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

    //Getter i Setters
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

    @Override
    public String toString() {
        return "Product{id=" + id + ", name='" + name + "', price=" + price +
            ", stock=" + stock + ", condition=" + condition + ", status=" + status + "}";
    }

}
