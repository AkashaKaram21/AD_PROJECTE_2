package com.ra34.projecte2.mapper;

import com.ra34.projecte2.dto.OrderItemResponseDTO;
import com.ra34.projecte2.dto.OrderResponseDTO;
import com.ra34.projecte2.model.OrderItem;
import com.ra34.projecte2.model.Order;
import java.util.ArrayList;
import java.util.List;

public class OrderMapper {

    public static OrderResponseDTO toResponseDTO(Order order) {
        OrderResponseDTO dto = new OrderResponseDTO();
        dto.setId(order.getId());
        
        if (order.getCustomer() != null) {
            dto.setCustomerId(order.getCustomer().getId());
        }
        
        dto.setOrderStatus(order.getOrderStatus());
        dto.setTotalAmounts(order.getTotalAmounts());
        dto.setDataCreated(order.getDataCreated());

        // Convertir items de la orden a DTOs
        List<OrderItemResponseDTO> itemList = new ArrayList<>();
        for (OrderItem item : order.getOrderItems()) {
            OrderItemResponseDTO itemDto = new OrderItemResponseDTO();
            itemDto.setId(item.getId());
            itemDto.setProductId(item.getProduct().getId());
            itemDto.setProductName(item.getProduct().getName());
            itemDto.setQuantity(item.getQuantity());
            itemDto.setPrice(item.getPrice());
            itemList.add(itemDto);
        }
        
        dto.setOrderItems(itemList);
        return dto;
    }
}