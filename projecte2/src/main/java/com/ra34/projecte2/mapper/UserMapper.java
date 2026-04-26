package com.ra34.projecte2.mapper;

import com.ra34.projecte2.dto.*;
import com.ra34.projecte2.model.*;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class UserMapper {

    public UserResponseDTO toUserDTO(User user) {
        if (user == null) {
            return null;
        }
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());

        // Mapear Customer
        if (user.getCustomer() != null) {
            dto.setCustomer(toCustomerDTO(user.getCustomer()));
        }

        // Mapear Roles (nombres)
        Set<String> roleNames = new HashSet<>();
        if (user.getRoles() != null) {
            for (Role role : user.getRoles()) {
                roleNames.add(role.getName());
            }
        }
        dto.setRoles(roleNames);
        return dto;
    }

    public CustomerResponseDTO toCustomerDTO(Customer customer) {
        if (customer == null) return null;
        CustomerResponseDTO dto = new CustomerResponseDTO();
        dto.setId(customer.getId());
        dto.setFirstName(customer.getFirstName());
        dto.setLastName(customer.getLastName());
        dto.setPhone(customer.getPhone());

        // Mapear Direcciones
        List<AddressDTO> addressDTOs = new ArrayList<>();
        if (customer.getAddresses() != null) {
            for (Address addr : customer.getAddresses()) {
                AddressDTO aDto = new AddressDTO();
                aDto.setId(addr.getId());
                aDto.setAddress(addr.getAddress());
                aDto.setCity(addr.getCity());
                aDto.setPostalCode(addr.getPostalCode());
                aDto.setCountry(addr.getCountry());
                aDto.setDefault(addr.isDefault());
                addressDTOs.add(aDto);
            }
        }
        dto.setAddresses(addressDTOs);
        return dto;
    }

    public OrderResponseDTO toOrderDTO(Order order) {
        if (order == null) return null;
        OrderResponseDTO dto = new OrderResponseDTO();
        dto.setId(order.getId());
        dto.setOrderDate(order.getOrderDate());
        dto.setTotalAmount(order.getTotalAmount());
        dto.setOrderStatus(order.getOrderStatus());

        List<OrderItemResponseDTO> items = new ArrayList<>();
        if (order.getItems() != null) {
            for (OrderItem item : order.getItems()) {
                OrderItemResponseDTO iDto = new OrderItemResponseDTO();
                iDto.setId(item.getId());
                iDto.setProductName(item.getProduct().getName());
                iDto.setQuantity(item.getQuantity());
                iDto.setUnitPrice(item.getUnitPrice());
                items.add(iDto);
            }
        }
        dto.setItems(items);
        return dto;
    }
}