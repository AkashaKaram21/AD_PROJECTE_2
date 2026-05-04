package com.ra34.projecte2.service;

import com.ra34.projecte2.dto.AddressRequestDTO;
import com.ra34.projecte2.dto.CustomerResponseDTO;
import com.ra34.projecte2.mapper.CustomerMapper;
import com.ra34.projecte2.model.Address;
import com.ra34.projecte2.model.Customer;
import com.ra34.projecte2.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Optional<CustomerResponseDTO> getCustomerById(Long id) {
        try {
            Optional<Customer> customer = customerRepository.findById(id);
            
            if (customer.isPresent()) {
                return Optional.of(CustomerMapper.toResponseDTO(customer.get()));
            }
            
            return Optional.empty();
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public List<CustomerResponseDTO> getAllCustomers() {
        try {
            List<Customer> customers = customerRepository.findAll();
            List<CustomerResponseDTO> dtos = new ArrayList<>();
            
            for (Customer customer : customers) {
                dtos.add(CustomerMapper.toResponseDTO(customer));
            }
            
            return dtos;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Transactional
    public Optional<CustomerResponseDTO> addAddresses(Long customerId, List<AddressRequestDTO> addressDTOs) {
        try {
            Optional<Customer> customerOpt = customerRepository.findById(customerId);
            
            if (customerOpt.isPresent()) {
                Customer customer = customerOpt.get();
                
                // Añadir cada dirección
                for (AddressRequestDTO dto : addressDTOs) {
                    Address address = new Address(dto.getStreet(), dto.getCity(), 
                                                 dto.getPostalCode(), dto.getCountry());
                    address.setCustomer(customer);
                    customer.getAddresses().add(address);
                }
                
                customerRepository.save(customer);
                return Optional.of(CustomerMapper.toResponseDTO(customer));
            }
            
            return Optional.empty();
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Transactional
    public Optional<CustomerResponseDTO> deleteAddresses(Long customerId) {
        try {
            Optional<Customer> customerOpt = customerRepository.findById(customerId);
            
            if (customerOpt.isPresent()) {
                Customer customer = customerOpt.get();
                customer.getAddresses().clear();
                
                return Optional.of(CustomerMapper.toResponseDTO(customerRepository.save(customer)));
            }
            
            return Optional.empty();
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
}