package com.ra34.projecte2.dto;

public class RoleResponseDTO {
    private Long id;
    private String name;

    // Constructor
    public RoleResponseDTO() {}

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
}