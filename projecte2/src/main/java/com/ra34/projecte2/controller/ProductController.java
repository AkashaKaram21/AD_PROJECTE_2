package com.ra34.projecte2.controller;

import com.ra34.projecte2.model.Condition;
import com.ra34.projecte2.service.ProductService;
import com.ra34.projecte2.dto.*;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    /**
     * GET /api/products
     * Obtener todos los productos disponibles
     */
    @GetMapping
    public ResponseEntity<?> getAllProducts() {
        try {
            List<ProductResponseDTO> products = productService.findAll();
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            ErrorDTO error = new ErrorDTO(500, "Error al obtener productos: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    /**
     * GET /api/products/{id}
     * Obtener un producto por ID
     * Retorna:
     * 200 = OK con Product
     * 404 = NOT_FOUND
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        try {
            Optional<ProductResponseDTO> product = productService.findById(id);
            
            if (product.isPresent()) {
                return ResponseEntity.ok(product.get());
            } else {
                ErrorDTO error = new ErrorDTO(404, "Producto con ID " + id + " no encontrado");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
            }
        } catch (Exception e) {
            ErrorDTO error = new ErrorDTO(500, "Error al obtener producto: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    /**
     * POST /api/products
     * Crear un nuevo producto
     * Retorna:
     * 201 = CREATED
     * 400 = BAD REQUEST
     */
    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody ProductRequestDTO dto) {
        try {
            ProductResponseDTO created = productService.create(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (Exception e) {
            ErrorDTO error = new ErrorDTO(400, "Error al crear producto: " + e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    /**
     * PUT /api/products/{id}
     * Actualizar todos los campos de un producto
     * Retorna:
     * 200 = OK
     * 404 = NOT_FOUND
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody ProductRequestDTO dto) {
        try {
            Optional<ProductResponseDTO> updated = productService.update(id, dto);
            
            if (updated.isPresent()) {
                return ResponseEntity.ok(updated.get());
            } else {
                ErrorDTO error = new ErrorDTO(404, "Producto con ID " + id + " no encontrado");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
            }
        } catch (Exception e) {
            ErrorDTO error = new ErrorDTO(500, "Error al actualizar producto: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    /**
     * PATCH /api/products/{id}/stock
     * Actualizar solo el stock
     * Retorna:
     * 200 = OK
     * 404 = NOT_FOUND
     */
    @PatchMapping("/{id}/stock")
    public ResponseEntity<?> updateStock(@PathVariable Long id, @RequestParam Integer stock) {
        try {
            Optional<ProductResponseDTO> updated = productService.updateStock(id, stock);
            
            if (updated.isPresent()) {
                return ResponseEntity.ok(updated.get());
            } else {
                ErrorDTO error = new ErrorDTO(404, "Producto con ID " + id + " no encontrado");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
            }
        } catch (Exception e) {
            ErrorDTO error = new ErrorDTO(500, "Error al actualizar stock: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    /**
     * PATCH /api/products/{id}/price
     * Actualizar solo el precio
     * Retorna:
     * 200 = OK
     * 404 = NOT_FOUND
     */
    @PatchMapping("/{id}/price")
    public ResponseEntity<?> updatePrice(@PathVariable Long id, @RequestParam Double price) {
        try {
            Optional<ProductResponseDTO> updated = productService.updatePrice(id, price);
            
            if (updated.isPresent()) {
                return ResponseEntity.ok(updated.get());
            } else {
                ErrorDTO error = new ErrorDTO(404, "Producto con ID " + id + " no encontrado");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
            }
        } catch (Exception e) {
            ErrorDTO error = new ErrorDTO(500, "Error al actualizar precio: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    /**
     * DELETE /api/products/{id}
     * Borrado físico (eliminación real)
     * Retorna:
     * 204 = NO_CONTENT
     * 404 = NOT_FOUND
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        try {
            boolean deleted = productService.deleteById(id);
            
            if (deleted) {
                return ResponseEntity.noContent().build();
            } else {
                ErrorDTO error = new ErrorDTO(404, "Producto con ID " + id + " no encontrado");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
            }
        } catch (Exception e) {
            ErrorDTO error = new ErrorDTO(500, "Error al eliminar producto: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    /**
     * DELETE /api/products/{id}/logical
     * Borrado lógico (status = false)
     * Retorna:
     * 200 = OK
     * 404 = NOT_FOUND
     */
    @DeleteMapping("/{id}/logical")
    public ResponseEntity<?> logicalDeleteProduct(@PathVariable Long id) {
        try {
            Optional<ProductResponseDTO> deleted = productService.logicalDelete(id);
            
            if (deleted.isPresent()) {
                return ResponseEntity.ok(deleted.get());
            } else {
                ErrorDTO error = new ErrorDTO(404, "Producto con ID " + id + " no encontrado");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
            }
        } catch (Exception e) {
            ErrorDTO error = new ErrorDTO(500, "Error al eliminar producto: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }


    /**
     * GET /api/products/count
     * Contar el total de productos
     */
    @GetMapping("/count")
    public ResponseEntity<?> countProducts() {
        try {
            long count = productService.count();
            return ResponseEntity.ok(count);
        } catch (Exception e) {
            ErrorDTO error = new ErrorDTO(500, "Error al contar productos: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    /**
     * GET /api/products/search/name?prefix=text
     * Buscar por nombre (contiene)
     */
    @GetMapping("/search/name")
    public ResponseEntity<?> searchByName(@RequestParam String prefix) {
        try {
            List<ProductResponseDTO> products = productService.searchByName(prefix);
            
            if (products.isEmpty()) {
                ErrorDTO error = new ErrorDTO(404, "No se encontraron productos con nombre '" + prefix + "'");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
            }
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            ErrorDTO error = new ErrorDTO(500, "Error en búsqueda: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    /**
     * GET /api/products/search/condition?condition=NOU
     * Buscar por condición
     */
    @GetMapping("/search/condition")
    public ResponseEntity<?> searchByCondition(@RequestParam String condition) {
        try {
            Condition cond;
            try {
                cond = Condition.valueOf(condition.toUpperCase());
            } catch (IllegalArgumentException e) {
                ErrorDTO error = new ErrorDTO(400, "Condición no válida: " + condition);
                return ResponseEntity.badRequest().body(error);
            }
            
            List<ProductResponseDTO> products = productService.searchByCondition(cond);
            
            if (products.isEmpty()) {
                ErrorDTO error = new ErrorDTO(404, "No se encontraron productos con condición '" + condition + "'");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
            }
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            ErrorDTO error = new ErrorDTO(500, "Error en búsqueda: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    /**
     * GET /api/products/search/price/asc
     * Ordenar por precio ascendente
     */
    @GetMapping("/search/price/asc")
    public ResponseEntity<?> orderByPriceAsc() {
        try {
            List<ProductResponseDTO> products = productService.orderByPrice("asc");
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            ErrorDTO error = new ErrorDTO(500, "Error en ordenamiento: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    /**
     * GET /api/products/search/price/desc
     * Ordenar por precio descendente
     */
    @GetMapping("/search/price/desc")
    public ResponseEntity<?> orderByPriceDesc() {
        try {
            List<ProductResponseDTO> products = productService.orderByPrice("desc");
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            ErrorDTO error = new ErrorDTO(500, "Error en ordenamiento: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    /**
     * GET /api/products/search/rating/asc
     * Ordenar por rating ascendente
     */
    @GetMapping("/search/rating/asc")
    public ResponseEntity<?> orderByRatingAsc() {
        try {
            List<ProductResponseDTO> products = productService.orderByRating("asc");
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            ErrorDTO error = new ErrorDTO(500, "Error en ordenamiento: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    /**
     * GET /api/products/search/rating/desc
     * Ordenar por rating descendente
     */
    @GetMapping("/search/rating/desc")
    public ResponseEntity<?> orderByRatingDesc() {
        try {
            List<ProductResponseDTO> products = productService.orderByRating("desc");
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            ErrorDTO error = new ErrorDTO(500, "Error en ordenamiento: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    /**
     * GET /api/products/search/price-range?priceMin=10&priceMax=100&limit=10
     * Rango de precio
     */
    @GetMapping("/search/price-range")
    public ResponseEntity<?> findByPriceRange(
            @RequestParam Double priceMin,
            @RequestParam Double priceMax,
            @RequestParam(defaultValue = "10") int limit) {
        try {
            List<ProductResponseDTO> products = productService.findByPriceRange(priceMin, priceMax, limit);
            
            if (products.isEmpty()) {
                ErrorDTO error = new ErrorDTO(404, "No se encontraron productos en el rango especificado");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
            }
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            ErrorDTO error = new ErrorDTO(500, "Error en búsqueda: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    /**
     * GET /api/products/search/top-quality-price
     * Top 5 mejor relación calidad-precio
     */
    @GetMapping("/search/top-quality-price")
    public ResponseEntity<?> findTop5QualityPrice() {
        try {
            List<ProductResponseDTO> products = productService.findTop5QualityPrice();
            
            if (products.isEmpty()) {
                ErrorDTO error = new ErrorDTO(404, "No se encontraron productos con rating");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
            }
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            ErrorDTO error = new ErrorDTO(500, "Error en búsqueda: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    /**
     * GET /api/products/search/rating-range?ratingMin=3&ratingMax=5&limit=10
     * Rango de rating
     */
    @GetMapping("/search/rating-range")
    public ResponseEntity<?> findByRatingRange(
            @RequestParam Double ratingMin,
            @RequestParam Double ratingMax,
            @RequestParam(defaultValue = "10") int limit) {
        try {
            List<ProductResponseDTO> products = productService.findByRatingRange(ratingMin, ratingMax, limit);
            
            if (products.isEmpty()) {
                ErrorDTO error = new ErrorDTO(404, "No se encontraron productos en el rango especificado");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
            }
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            ErrorDTO error = new ErrorDTO(500, "Error en búsqueda: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    /**
     * GET /api/products/search/price-above?price=100
     * Productos con precio superior
     */
    @GetMapping("/search/price-above")
    public ResponseEntity<?> findByPriceGreaterThan(@RequestParam Double price) {
        try {
            List<ProductResponseDTO> products = productService.findByPriceGreaterThan(price);
            
            if (products.isEmpty()) {
                ErrorDTO error = new ErrorDTO(404, "No se encontraron productos con precio superior a " + price);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
            }
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            ErrorDTO error = new ErrorDTO(500, "Error en búsqueda: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    /**
     * GET /api/products/search/top-new
     * Top 10 productos nuevos con mejor rating
     */
    @GetMapping("/search/top-new")
    public ResponseEntity<?> findTop10NewWithBestRating() {
        try {
            List<ProductResponseDTO> products = productService.findTop10NewWithBestRating();
            
            if (products.isEmpty()) {
                ErrorDTO error = new ErrorDTO(404, "No se encontraron productos nuevos");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
            }
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            ErrorDTO error = new ErrorDTO(500, "Error en búsqueda: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    /**
     * POST /api/products/bulk
     * Cargar productos desde CSV (multipart/form-data, file=...)
     */
    @PostMapping("/bulk")
    public ResponseEntity<?> bulkLoadCsv(@RequestParam("file") MultipartFile file) {
        try {
            if (file == null || file.isEmpty()) {
                ErrorDTO error = new ErrorDTO(400, "El archivo está vacío");
                return ResponseEntity.badRequest().body(error);
            }
            
            int count = productService.bulkLoadFromCsv(file);
            String message = "Se han añadido correctamente " + count + " productos.";
            return ResponseEntity.ok(message);
            
        } catch (Exception e) {
            ErrorDTO error = new ErrorDTO(400, "Error carga CSV: " + e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
}