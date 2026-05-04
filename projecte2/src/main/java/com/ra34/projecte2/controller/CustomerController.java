package com.ra34.projecte2.controller;

import com.ra34.projecte2.dto.AddressRequestDTO;
import com.ra34.projecte2.dto.CustomerResponseDTO;
import com.ra34.projecte2.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> getCustomerById(@PathVariable Long id) {
        try {
            Optional<CustomerResponseDTO> result = customerService.getCustomerById(id);
            
            if (result.isPresent()) {
                return ResponseEntity.ok(result.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponseDTO>> getAllCustomers() {
        try {
            List<CustomerResponseDTO> customers = customerService.getAllCustomers();
            return ResponseEntity.ok(customers);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/{id}/addresses")
    public ResponseEntity<CustomerResponseDTO> addAddresses(@PathVariable Long id, 
                                                             @RequestBody List<AddressRequestDTO> dtos) {
        try {
            Optional<CustomerResponseDTO> result = customerService.addAddresses(id, dtos);
            
            if (result.isPresent()) {
                return ResponseEntity.status(HttpStatus.CREATED).body(result.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}/addresses")
    public ResponseEntity<CustomerResponseDTO> deleteAddresses(@PathVariable Long id) {
        try {
            Optional<CustomerResponseDTO> result = customerService.deleteAddresses(id);
            
            if (result.isPresent()) {
                return ResponseEntity.ok(result.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}