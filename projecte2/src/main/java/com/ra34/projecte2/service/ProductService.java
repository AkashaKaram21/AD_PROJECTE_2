package com.ra34.projecte2.service;

import com.ra34.projecte2.model.*;
import com.ra34.projecte2.repository.ProductRepository;
import com.ra34.projecte2.mapper.ProductMapper;
import com.ra34.projecte2.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;


    // Obtener todos los productos
    public List<ProductResponseDTO> findAll() {
        List<Product> products = (List<Product>) productRepository.findAll();
        List<ProductResponseDTO> dtos = new ArrayList<>();
        
        for (Product p : products) {
            if (p.getStatus() == true) {
                ProductResponseDTO dto = productMapper.toDTO(p);
                dtos.add(dto);
            }
        }
        return dtos;
    }

    // Obtener un producto por ID
    public Optional<ProductResponseDTO> findById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        
        if (product.isPresent()) {
            Product p = product.get();
            if (p.getStatus() == true) {
                ProductResponseDTO dto = productMapper.toDTO(p);
                return Optional.of(dto);
            }
        }
        return Optional.empty();
    }

    // Crear un nuevo producto
    @Transactional
    public ProductResponseDTO create(ProductRequestDTO dto) {
        Product product = productMapper.toEntity(dto);
        product.setStatus(true);
        product.setDataCreated(LocalDateTime.now());
        product.setDataUpdated(LocalDateTime.now());
        
        Product saved = productRepository.save(product);
        ProductResponseDTO result = productMapper.toDTO(saved);
        return result;
    }

    // Actualizar producto completo
    @Transactional
    public Optional<ProductResponseDTO> update(Long id, ProductRequestDTO dto) {
        Optional<Product> optional = productRepository.findById(id);
        
        if (optional.isPresent()) {
            Product p = optional.get();
            
            if (p.getStatus() == true) {
                p.setName(dto.getName());
                p.setDescription(dto.getDescription());
                p.setStock(dto.getStock());
                p.setPrice(dto.getPrice());
                p.setRating(dto.getRating());
                p.setCondition(dto.getCondition());
                p.setDataUpdated(LocalDateTime.now());
                
                Product saved = productRepository.save(p);
                ProductResponseDTO result = productMapper.toDTO(saved);
                return Optional.of(result);
            }
        }
        return Optional.empty();
    }

    // Actualizar solo el stock
    @Transactional
    public Optional<ProductResponseDTO> updateStock(Long id, Integer stock) {
        Optional<Product> optional = productRepository.findById(id);
        
        if (optional.isPresent()) {
            Product p = optional.get();
            
            if (p.getStatus() == true) {
                p.setStock(stock);
                p.setDataUpdated(LocalDateTime.now());
                
                Product saved = productRepository.save(p);
                ProductResponseDTO result = productMapper.toDTO(saved);
                return Optional.of(result);
            }
        }
        return Optional.empty();
    }

    // Actualizar solo el precio
    @Transactional
    public Optional<ProductResponseDTO> updatePrice(Long id, Double price) {
        Optional<Product> optional = productRepository.findById(id);
        
        if (optional.isPresent()) {
            Product p = optional.get();
            
            if (p.getStatus() == true) {
                p.setPrice(price);
                p.setDataUpdated(LocalDateTime.now());
                
                Product saved = productRepository.save(p);
                ProductResponseDTO result = productMapper.toDTO(saved);
                return Optional.of(result);
            }
        }
        return Optional.empty();
    }

    // Borrado lógico (status = false)
    @Transactional
    public Optional<ProductResponseDTO> logicalDelete(Long id) {
        Optional<Product> optional = productRepository.findById(id);
        
        if (optional.isPresent()) {
            Product p = optional.get();
            p.setStatus(false);
            p.setDataUpdated(LocalDateTime.now());
            
            Product saved = productRepository.save(p);
            ProductResponseDTO result = productMapper.toDTO(saved);
            return Optional.of(result);
        }
        return Optional.empty();
    }

    // Borrado físico
    @Transactional
    public boolean deleteById(Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }


    public List<ProductResponseDTO> searchByName(String prefix) {
        List<Product> products = productRepository.findByNameContainingAndStatusTrue(prefix);
        List<ProductResponseDTO> dtos = new ArrayList<>();
        
        for (Product p : products) {
            ProductResponseDTO dto = productMapper.toDTO(p);
            dtos.add(dto);
        }
        return dtos;
    }

    public List<ProductResponseDTO> searchByCondition(Condition condition) {
        List<Product> products = productRepository.findByConditionAndStatusTrue(condition);
        List<ProductResponseDTO> dtos = new ArrayList<>();
        
        for (Product p : products) {
            ProductResponseDTO dto = productMapper.toDTO(p);
            dtos.add(dto);
        }
        return dtos;
    }

    public List<ProductResponseDTO> orderByPrice(String order) {
        List<Product> products;
        
        if ("desc".equalsIgnoreCase(order)) {
            products = productRepository.findByStatusTrueOrderByPriceDesc();
        } else {
            products = productRepository.findByStatusTrueOrderByPriceAsc();
        }
        
        List<ProductResponseDTO> dtos = new ArrayList<>();
        for (Product p : products) {
            ProductResponseDTO dto = productMapper.toDTO(p);
            dtos.add(dto);
        }
        return dtos;
    }

    public List<ProductResponseDTO> orderByRating(String order) {
        List<Product> products;
        
        if ("desc".equalsIgnoreCase(order)) {
            products = productRepository.findByStatusTrueOrderByRatingDesc();
        } else {
            products = productRepository.findByStatusTrueOrderByRatingAsc();
        }
        
        List<ProductResponseDTO> dtos = new ArrayList<>();
        for (Product p : products) {
            ProductResponseDTO dto = productMapper.toDTO(p);
            dtos.add(dto);
        }
        return dtos;
    }

    public List<ProductResponseDTO> findByPriceRange(Double priceMin, Double priceMax, int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        List<Product> products = productRepository.findByPriceRange(priceMin, priceMax, pageable);
        List<ProductResponseDTO> dtos = new ArrayList<>();
        
        for (Product p : products) {
            ProductResponseDTO dto = productMapper.toDTO(p);
            dtos.add(dto);
        }
        return dtos;
    }

    public List<ProductResponseDTO> findTop5QualityPrice() {
        Pageable pageable = PageRequest.of(0, 5);
        List<Product> products = productRepository.findTop5ByQualityPrice(pageable);
        List<ProductResponseDTO> dtos = new ArrayList<>();
        
        for (Product p : products) {
            ProductResponseDTO dto = productMapper.toDTO(p);
            dtos.add(dto);
        }
        return dtos;
    }

    public List<ProductResponseDTO> findByRatingRange(Double ratingMin, Double ratingMax, int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        List<Product> products = productRepository.findByRatingRange(ratingMin, ratingMax, pageable);
        List<ProductResponseDTO> dtos = new ArrayList<>();
        
        for (Product p : products) {
            ProductResponseDTO dto = productMapper.toDTO(p);
            dtos.add(dto);
        }
        return dtos;
    }

    public List<ProductResponseDTO> findByPriceGreaterThan(Double price) {
        List<Product> products = productRepository.findByPriceGreaterThan(price);
        List<ProductResponseDTO> dtos = new ArrayList<>();
        
        for (Product p : products) {
            ProductResponseDTO dto = productMapper.toDTO(p);
            dtos.add(dto);
        }
        return dtos;
    }

    public List<ProductResponseDTO> findTop10NewWithBestRating() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Product> products = productRepository.findTop10NewWithBestRating(pageable);
        List<ProductResponseDTO> dtos = new ArrayList<>();
        
        for (Product p : products) {
            ProductResponseDTO dto = productMapper.toDTO(p);
            dtos.add(dto);
        }
        return dtos;
    }

    public long count() {
        return productRepository.count();
    }


    @Transactional
    public int bulkLoadFromCsv(MultipartFile file) throws Exception {
        int count = 0;
        BufferedReader reader = null;
        
        try {
            reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
            String line;
            int lineNumber = 0;
            
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                
                if (lineNumber == 1) {
                    continue;
                }
                
                String[] fields = line.split(",");
                
                if (fields.length < 6) {
                    throw new RuntimeException("Error línea " + lineNumber + ": faltan campos");
                }
                
                String name = fields[0].trim();
                if (name.isEmpty()) {
                    throw new RuntimeException("Error línea " + lineNumber + ": nombre vacío");
                }
                
                try {
                    String description = fields[1].trim();
                    Integer stock = Integer.parseInt(fields[2].trim());
                    Double price = Double.parseDouble(fields[3].trim());
                    
                    Double rating = null;
                    String ratingStr = fields[4].trim();
                    if (!ratingStr.isEmpty()) {
                        rating = Double.parseDouble(ratingStr);
                    }
                    
                    Condition condition = Condition.valueOf(fields[5].trim().toUpperCase());
                    
                    Product p = new Product(name, description, stock, price, rating, condition);
                    productRepository.save(p);
                    count++;
                    
                } catch (NumberFormatException e) {
                    throw new RuntimeException("Error línea " + lineNumber + ": formato de número inválido: " + e.getMessage());
                } catch (IllegalArgumentException e) {
                    throw new RuntimeException("Error línea " + lineNumber + ": condición no válida");
                }
            }
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        
        return count;
    }
}