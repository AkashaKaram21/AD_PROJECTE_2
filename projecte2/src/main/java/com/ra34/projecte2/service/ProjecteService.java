package com.ra34.projecte2.service;

import com.ra34.projecte2.dto.*;
import com.ra34.projecte2.model.*;
import com.ra34.projecte2.repository.*;
import com.ra34.projecte2.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service
public class ProjecteService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private CustomerRepository customerRepo;

    @Autowired
    private ProductRepository productRepo;

    @Autowired 
    private OrderRepository orderRepo;

    @Autowired 
    private RoleRepository roleRepo;
    
    @Autowired 
    private UserMapper mapper;

    @Transactional
    public UserResponseDTO crearUsuari(UserRequestDTO dto) {
        if (userRepo.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("L'email ja existeix");
        } else {
            User usuari = new User(dto.getEmail(), dto.getPassword());
            Customer customer = new Customer(dto.getFirstName(), dto.getLastName(), dto.getPhone());
            
            customer.setUser(usuari);
            usuari.setCustomer(customer);
            
            User guardat = userRepo.save(usuari);
            return mapper.toUserDTO(guardat);
        }
    }

    public UserResponseDTO consultarUsuari(Long id) {
        Optional<User> opt = userRepo.findById(id);
        if (opt.isPresent()) {
            return mapper.toUserDTO(opt.get());
        } else {
            throw new RuntimeException("Usuari no trobat");
        }
    }

    @Transactional
    public void esborrarAdreces(Long customerId) {
        Optional<Customer> opt = customerRepo.findById(customerId);
        if (opt.isPresent()) {
            Customer c = opt.get();
            c.getAddresses().clear();
            customerRepo.save(c);
        } else {
            throw new RuntimeException("Customer no trobat");
        }
    }

    public List<CustomerResponseDTO> consultarTotsCustomers() {
        List<Customer> llista = customerRepo.findAll();
        List<CustomerResponseDTO> resposta = new ArrayList<>();
        for (Customer c : llista) {
            resposta.add(mapper.toCustomerDTO(c));
        }
        return resposta;
    }

    @Transactional
    public OrderResponseDTO crearOrder(OrderRequestDTO dto) {
        Optional<Customer> optC = customerRepo.findById(dto.getCustomerId());
        if (optC.isPresent()) {
            Order order = new Order(optC.get());
            double total = 0.0;

            for (Long pId : dto.getProductIds()) {
                Optional<Product> optP = productRepo.findById(pId);
                if (optP.isPresent()) {
                    Product p = optP.get();
                    OrderItem item = new OrderItem(order, p, 1, p.getPrice());
                    order.getItems().add(item);
                    total = total + p.getPrice();
                }
            }
            order.setTotalAmount(total);
            Order guardada = orderRepo.save(order);
            return mapper.toOrderDTO(guardada);
        } else {
            throw new RuntimeException("Customer no existeix");
        }
    }

    @Transactional
    public OrderResponseDTO processarOrder(Long id) {
        Optional<Order> opt = orderRepo.findById(id);
        if (opt.isPresent()) {
            Order o = opt.get();
            if (o.getOrderStatus().equals("PENDENT")) {
                o.setOrderStatus("PROCESSAT");
                return mapper.toOrderDTO(orderRepo.save(o));
            } else {
                throw new RuntimeException("Només es poden processar ordres PENDENTS");
            }
        } else {
            throw new RuntimeException("Order no trobada");
        }
    }

    @Transactional
    public UserResponseDTO esborrarRols(Long userId, List<Long> roleIds) {
        Optional<User> opt = userRepo.findById(userId);
        if (opt.isPresent()) {
            User u = opt.get();
            u.getRoles().removeIf(r -> roleIds.contains(r.getId()));
            return mapper.toUserDTO(userRepo.save(u));
        } else {
            throw new RuntimeException("Usuari no trobat");
        }
    }


    @Transactional
    public UserResponseDTO modificarUsuari(Long id, UserRequestDTO dto) {
        Optional<User> opt = userRepo.findById(id);
        if (opt.isPresent()) {
            User u = opt.get();
            u.setEmail(dto.getEmail());
            if (u.getCustomer() != null) {
                u.getCustomer().setFirstName(dto.getFirstName());
                u.getCustomer().setLastName(dto.getLastName());
                u.getCustomer().setPhone(dto.getPhone());
            }
            return mapper.toUserDTO(userRepo.save(u));
        } else {
            throw new RuntimeException("Usuari no trobat");
        }
    }

    public List<UserResponseDTO> consultarTotsUsuaris() {
        List<User> llista = userRepo.findAll();
        List<UserResponseDTO> resposta = new ArrayList<>();
        for (User u : llista) {
            resposta.add(mapper.toUserDTO(u));
        }
        return resposta;
    }

    @Transactional
    public CustomerResponseDTO afegirAdreces(Long customerId, List<AddressDTO> adrecesDTO) {
        Optional<Customer> opt = customerRepo.findById(customerId);
        if (opt.isPresent()) {
            Customer c = opt.get();
            for (AddressDTO d : adrecesDTO) {
                Address a = new Address();
                a.setAddress(d.getAddress());
                a.setCity(d.getCity());
                a.setPostalCode(d.getPostalCode());
                a.setCountry(d.getCountry());
                a.setCustomer(c);
                c.getAddresses().add(a);
            }
            return mapper.toCustomerDTO(customerRepo.save(c));
        } else {
            throw new RuntimeException("Customer no trobat");
        }
    }

    public CustomerResponseDTO consultarCustomer(Long id) {
        Optional<Customer> opt = customerRepo.findById(id);
        if (opt.isPresent()) {
            return mapper.toCustomerDTO(opt.get());
        } else {
            throw new RuntimeException("Customer no trobat");
        }
    }

    @Transactional
    public OrderResponseDTO afegirProductesAOrder(Long orderId, List<Long> productIds) {
        Optional<Order> optO = orderRepo.findById(orderId);
        if (optO.isPresent()) {
            Order order = optO.get();
            double nouTotal = order.getTotalAmount();

            for (Long pId : productIds) {
                Optional<Product> optP = productRepo.findById(pId);
                if (optP.isPresent()) {
                    Product p = optP.get();
                    OrderItem item = new OrderItem(order, p, 1, p.getPrice());
                    order.getItems().add(item);
                    nouTotal = nouTotal + p.getPrice();
                }
            }
            order.setTotalAmount(nouTotal);
            return mapper.toOrderDTO(orderRepo.save(order));
        } else {
            throw new RuntimeException("Order no trobada");
        }
    }

    @Transactional
    public OrderResponseDTO cancelarOrder(Long id) {
        Optional<Order> opt = orderRepo.findById(id);
        if (opt.isPresent()) {
            Order o = opt.get();
            if (o.getOrderStatus().equals("PENDENT")) {
                o.setOrderStatus("CANCELAT");
                return mapper.toOrderDTO(orderRepo.save(o));
            } else {
                throw new RuntimeException("Només es poden cancelar ordres PENDENTS");
            }
        } else {
            throw new RuntimeException("Order no trobada");
        }
    }

    @Transactional
    public UserResponseDTO afegirRols(Long userId, List<Long> roleIds) {
        Optional<User> optU = userRepo.findById(userId);
        if (optU.isPresent()) {
            User u = optU.get();
            for (Long rId : roleIds) {
                Optional<Role> optR = roleRepo.findById(rId);
                if (optR.isPresent()) {
                    u.getRoles().add(optR.get());
                }
            }
            return mapper.toUserDTO(userRepo.save(u));
        } else {
            throw new RuntimeException("Usuari no trobat");
        }
    }
}