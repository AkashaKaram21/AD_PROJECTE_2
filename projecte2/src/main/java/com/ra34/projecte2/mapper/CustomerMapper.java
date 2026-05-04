package com.ra34.projecte2.mapper;

import com.ra34.projecte2.dto.AddressResponseDTO;
import com.ra34.projecte2.dto.CustomerInfoDTO;
import com.ra34.projecte2.dto.CustomerResponseDTO;
import com.ra34.projecte2.model.Address;
import com.ra34.projecte2.model.Customer;
import java.util.ArrayList;
import java.util.List;

public class CustomerMapper {

    public static CustomerInfoDTO toInfoDTO(Customer customer) {
        if (customer == null) {
            return null;
        }
        
        CustomerInfoDTO dto = new CustomerInfoDTO();
        dto.setId(customer.getId());
        dto.setFirstName(customer.getFirstName());
        dto.setLastName(customer.getLastName());
        dto.setPhone(customer.getPhone());
        
        return dto;
    }

    public static CustomerResponseDTO toResponseDTO(Customer customer) {
        CustomerResponseDTO dto = new CustomerResponseDTO();
        dto.setId(customer.getId());
        
        if (customer.getUser() != null) {
            dto.setUserEmail(customer.getUser().getEmail());
        }
        
        dto.setFirstName(customer.getFirstName());
        dto.setLastName(customer.getLastName());
        dto.setPhone(customer.getPhone());

        // Convertir direcciones a DTOs
        List<AddressResponseDTO> addressList = new ArrayList<>();
        for (Address address : customer.getAddresses()) {
            AddressResponseDTO addressDto = new AddressResponseDTO();
            addressDto.setId(address.getId());
            addressDto.setStreet(address.getStreet());
            addressDto.setCity(address.getCity());
            addressDto.setPostalCode(address.getPostalCode());
            addressDto.setCountry(address.getCountry());
            addressList.add(addressDto);
        }
        
        dto.setAddresses(addressList);
        return dto;
    }
}