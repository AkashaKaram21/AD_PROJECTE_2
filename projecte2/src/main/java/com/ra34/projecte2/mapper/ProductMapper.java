package com.ra34.projecte2.mapper;

import com.ra34.projecte2.dto.ProductRequestDTO;
import com.ra34.projecte2.dto.ProductResponseDTO;
import com.ra34.projecte2.model.Product;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class ProductMapper {
    public ProductResponseDTO toDTO(Product p) {
        if (p == null) return null;
        ProductResponseDTO dto = new ProductResponseDTO();
        dto.setId(p.getId());
        dto.setName(p.getName());
        dto.setDescription(p.getDescription());
        dto.setStock(p.getStock());
        dto.setPrice(p.getPrice());
        dto.setRating(p.getRating());
        dto.setCondition(p.getCondition());
        return dto;
    }

    public Product toEntity(ProductRequestDTO dto) {
        if (dto == null) return null;
        Product p = new Product();
        p.setName(dto.getName());
        p.setDescription(dto.getDescription());
        p.setStock(dto.getStock());
        p.setPrice(dto.getPrice());
        p.setRating(dto.getRating());
        p.setCondition(dto.getCondition());
        return p;
    }

    public List<ProductResponseDTO> toDTOList(List<Product> list) {
        List<ProductResponseDTO> dtos = new ArrayList<>();
        for (Product p : list) {
            dtos.add(toDTO(p));
        }
        return dtos;
    }
}