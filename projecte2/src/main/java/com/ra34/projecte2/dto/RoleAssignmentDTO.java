package com.ra34.projecte2.dto;

import java.util.List;

public class RoleAssignmentDTO {
    private List<Long> roleIds;

    // Constructor
    public RoleAssignmentDTO() {}

    // Getters y Setters
    public List<Long> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<Long> roleIds) {
        this.roleIds = roleIds;
    }
}