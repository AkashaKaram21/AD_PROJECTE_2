package com.ra34.projecte2.dto;

import java.util.List;

public class UserRoleResponseDTO {
    private Long id;
    private String email;
    private List<RoleResponseDTO> roles;

    // Constructor
    public UserRoleResponseDTO() {}

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<RoleResponseDTO> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleResponseDTO> roles) {
        this.roles = roles;
    }
}