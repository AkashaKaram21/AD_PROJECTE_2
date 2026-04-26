package com.ra34.projecte2.dto;

import java.util.Set;

public class UserResponseDTO {
    private Long id;
    private String email;
    private CustomerResponseDTO customer;
    private Set<String> roles;

    public UserResponseDTO() {}

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public CustomerResponseDTO getCustomer() { return customer; }
    public void setCustomer(CustomerResponseDTO customer) { this.customer = customer; }
    public Set<String> getRoles() { return roles; }
    public void setRoles(Set<String> roles) { this.roles = roles; }
}