package com.ra34.projecte2.mapper;

import com.ra34.projecte2.dto.RoleResponseDTO;
import com.ra34.projecte2.dto.UserResponseDTO;
import com.ra34.projecte2.dto.UserRoleResponseDTO;
import com.ra34.projecte2.model.Role;
import com.ra34.projecte2.model.User;
import java.util.ArrayList;
import java.util.List;

public class UserMapper {

    public static UserResponseDTO toResponseDTO(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        
        if (user.getCustomer() != null) {
            dto.setCustomer(CustomerMapper.toInfoDTO(user.getCustomer()));
        }
        
        return dto;
    }

    public static UserRoleResponseDTO toRoleResponseDTO(User user) {
        UserRoleResponseDTO dto = new UserRoleResponseDTO();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        
        // Convertir roles a una lista de RoleResponseDTO
        List<RoleResponseDTO> roleList = new ArrayList<>();
        for (Role role : user.getRoles()) {
            RoleResponseDTO roleDto = new RoleResponseDTO();
            roleDto.setId(role.getId());
            roleDto.setName(role.getName());
            roleList.add(roleDto);
        }
        
        dto.setRoles(roleList);
        return dto;
    }
}