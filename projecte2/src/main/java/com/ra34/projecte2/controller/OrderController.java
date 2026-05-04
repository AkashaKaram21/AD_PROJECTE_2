package com.ra34.projecte2.controller;

import com.ra34.projecte2.dto.OrderItemRequestDTO;
import com.ra34.projecte2.dto.OrderRequestDTO;
import com.ra34.projecte2.dto.OrderResponseDTO;
import com.ra34.projecte2.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderResponseDTO> createOrder(@RequestBody OrderRequestDTO dto) {
        try {
            Optional<OrderResponseDTO> result = orderService.createOrder(dto);
            
            if (result.isPresent()) {
                return ResponseEntity.status(HttpStatus.CREATED).body(result.get());
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> getOrderById(@PathVariable Long id) {
        try {
            Optional<OrderResponseDTO> result = orderService.getOrderById(id);
            
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

    @PatchMapping("/{id}/process")
    public ResponseEntity<OrderResponseDTO> processOrder(@PathVariable Long id) {
        try {
            Optional<OrderResponseDTO> result = orderService.processOrder(id);
            
            if (result.isPresent()) {
                return ResponseEntity.ok(result.get());
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<OrderResponseDTO> cancelOrder(@PathVariable Long id) {
        try {
            Optional<OrderResponseDTO> result = orderService.cancelOrder(id);
            
            if (result.isPresent()) {
                return ResponseEntity.ok(result.get());
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/{id}/items")
    public ResponseEntity<OrderResponseDTO> addItemsToOrder(@PathVariable Long id, 
                                                             @RequestBody List<OrderItemRequestDTO> dtos) {
        try {
            Optional<OrderResponseDTO> result = orderService.addItemsToOrder(id, dtos);
            
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