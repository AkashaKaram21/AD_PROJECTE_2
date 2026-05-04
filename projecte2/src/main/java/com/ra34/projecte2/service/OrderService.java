package com.ra34.projecte2.service;

import com.ra34.projecte2.dto.OrderItemRequestDTO;
import com.ra34.projecte2.dto.OrderRequestDTO;
import com.ra34.projecte2.dto.OrderResponseDTO;
import com.ra34.projecte2.mapper.OrderMapper;
import com.ra34.projecte2.model.Customer;
import com.ra34.projecte2.model.Order;
import com.ra34.projecte2.model.OrderItem;
import com.ra34.projecte2.model.OrderStatus;
import com.ra34.projecte2.model.Product;
import com.ra34.projecte2.repository.CustomerRepository;
import com.ra34.projecte2.repository.OrderRepository;
import com.ra34.projecte2.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository, CustomerRepository customerRepository, 
                        ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public Optional<OrderResponseDTO> createOrder(OrderRequestDTO dto) {
        try {
            Optional<Customer> customerOpt = customerRepository.findById(dto.getCustomerId());

            if (!customerOpt.isPresent()) {
                return Optional.empty();
            }

            // Crear la orden
            Order order = new Order(customerOpt.get());

            // Añadir los items a la orden
            double totalAmount = 0.0;
            
            for (OrderItemRequestDTO itemDto : dto.getItems()) {
                Optional<Product> productOpt = productRepository.findById(itemDto.getProductId());
                
                if (!productOpt.isPresent()) {
                    return Optional.empty();
                }

                Product product = productOpt.get();
                double itemPrice = product.getPrice();
                int itemQuantity = itemDto.getQuantity();
                
                // Crear el item de la orden
                OrderItem item = new OrderItem(itemQuantity, itemPrice, order, product);
                order.getOrderItems().add(item);
                
                // Sumar al total
                totalAmount = totalAmount + (itemQuantity * itemPrice);
            }

            // Establecer el total
            order.setTotalAmounts(totalAmount);

            // Guardar la orden
            orderRepository.save(order);
            
            return Optional.of(OrderMapper.toResponseDTO(order));
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public Optional<OrderResponseDTO> getOrderById(Long id) {
        try {
            Optional<Order> order = orderRepository.findById(id);
            
            if (order.isPresent()) {
                return Optional.of(OrderMapper.toResponseDTO(order.get()));
            }
            
            return Optional.empty();
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Transactional
    public Optional<OrderResponseDTO> processOrder(Long orderId) {
        try {
            Optional<Order> orderOpt = orderRepository.findById(orderId);
            
            if (orderOpt.isPresent()) {
                Order order = orderOpt.get();
                
                // Comprobar que está en estado PENDING
                if (order.getOrderStatus() == OrderStatus.PENDING) {
                    order.setOrderStatus(OrderStatus.PROCESSED);
                    orderRepository.save(order);
                    return Optional.of(OrderMapper.toResponseDTO(order));
                }
            }
            
            return Optional.empty();
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Transactional
    public Optional<OrderResponseDTO> cancelOrder(Long orderId) {
        try {
            Optional<Order> orderOpt = orderRepository.findById(orderId);
            
            if (orderOpt.isPresent()) {
                Order order = orderOpt.get();
                
                // Comprobar que está en estado PENDING
                if (order.getOrderStatus() == OrderStatus.PENDING) {
                    order.setOrderStatus(OrderStatus.CANCELLED);
                    orderRepository.save(order);
                    return Optional.of(OrderMapper.toResponseDTO(order));
                }
            }
            
            return Optional.empty();
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Transactional
    public Optional<OrderResponseDTO> addItemsToOrder(Long orderId, List<OrderItemRequestDTO> itemDtos) {
        try {
            Optional<Order> orderOpt = orderRepository.findById(orderId);
            
            if (orderOpt.isPresent()) {
                Order order = orderOpt.get();
                
                // Añadir nuevos items
                for (OrderItemRequestDTO itemDto : itemDtos) {
                    Optional<Product> productOpt = productRepository.findById(itemDto.getProductId());
                    
                    if (productOpt.isPresent()) {
                        Product product = productOpt.get();
                        OrderItem item = new OrderItem(itemDto.getQuantity(), product.getPrice(), order, product);
                        order.getOrderItems().add(item);
                    }
                }

                // Recalcular el total
                double total = 0.0;
                for (OrderItem item : order.getOrderItems()) {
                    total = total + (item.getQuantity() * item.getPrice());
                }
                order.setTotalAmounts(total);

                // Guardar
                orderRepository.save(order);
                return Optional.of(OrderMapper.toResponseDTO(order));
            }
            
            return Optional.empty();
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
}