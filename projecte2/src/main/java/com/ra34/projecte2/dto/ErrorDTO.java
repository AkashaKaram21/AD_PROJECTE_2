package com.ra34.projecte2.dto;

/**
 * DTO per gestionar els errors no controlats que retorna el controller.
 */
public class ErrorDTO {
    
    private int status;
    private String description;
    
    // Constructor
    public ErrorDTO(int status, String description) {
        this.status = status;
        this.description = description;
    }
    
    // Getters y Setters
    public int getStatus() {
        return status;
    }
    
    public void setStatus(int status) {
        this.status = status;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
}