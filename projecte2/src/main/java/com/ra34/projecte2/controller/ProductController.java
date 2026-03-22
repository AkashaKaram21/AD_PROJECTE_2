package com.ra34.projecte2.controller;

import com.ra34.projecte2.dto.ErrorDTO;
import com.ra34.projecte2.dto.ProductResponseDTO;
import com.ra34.projecte2.model.Product;
import com.ra34.projecte2.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    // Injecció per constructor 
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // Càrrega massiva de dades d'un fitxer en format .csv
    @PostMapping("/csv")
    public ResponseEntity<?> processCsv(@RequestParam("file") MultipartFile file) {
        try {
            int count = productService.processCsv(file);
            return ResponseEntity.ok("Productes carregats correctament: " + count);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorDTO(400, e.getMessage()));
        }
    }

    // Consultar tots els productes
    @GetMapping
    public ResponseEntity<?> findAll() {
        try {
            List<ProductResponseDTO> products = productService.findAll();
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorDTO(500, e.getMessage()));
        }
    }

    // Consultar un producte per id
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(productService.findById(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorDTO(404, e.getMessage()));
        }
    }

    // Afegir un producte
    @PostMapping
    public ResponseEntity<?> saveProduct(@RequestBody Product product) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(productService.saveProduct(product));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorDTO(400, e.getMessage()));
        }
    }

    // Actualitzar un producte sencer
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        try {
            return ResponseEntity.ok(productService.updateProduct(id, product));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorDTO(404, e.getMessage()));
        }
    }

    // Modificar l'estoc d'un producte
    @PatchMapping("/{id}/estoc")
    public ResponseEntity<?> updateEstoc(@PathVariable Long id, @RequestParam int stock) {
        try {
            return ResponseEntity.ok(productService.updateEstoc(id, stock));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorDTO(404, e.getMessage()));
        }
    }

    // Modificar el preu d'un producte
    @PatchMapping("/{id}/preu")
    public ResponseEntity<?> updatePrice(@PathVariable Long id, @RequestParam double price) {
        try {
            return ResponseEntity.ok(productService.updatePrice(id, price));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorDTO(404, e.getMessage()));
        }
    }

    // Borrat físic d'un producte
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorDTO(404, e.getMessage()));
        }
    }

    // Borrat lògic d'un producte
    @DeleteMapping("/logic/{id}")
    public ResponseEntity<?> deleteLogicProduct(@PathVariable Long id) {
        try {
            productService.deleteLogicProduct(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorDTO(404, e.getMessage()));
        }
    }

    // Cerca per nom que contingui el valor i status true
    @GetMapping("/search/nom")
    public ResponseEntity<?> searchByName(@RequestParam String prefix) {
        try {
            return ResponseEntity.ok(productService.searchByName(prefix));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorDTO(500, e.getMessage()));
        }
    }

    // Cerca per condició i status true
    @GetMapping("/search/condition")
    public ResponseEntity<?> findByCondition(@RequestParam String condition) {
        try {
            return ResponseEntity.ok(productService.findByCondition(condition));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorDTO(400, e.getMessage()));
        }
    }

    // Cerca per camp preu i ordre (asc/desc), amb status true
    @GetMapping("/search/order")
    public ResponseEntity<?> getProductsOrderedByCamp(@RequestParam String camp,
                                                       @RequestParam String order) {
        try {
            return ResponseEntity.ok(productService.getProductsOrderedByCamp(camp, order));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorDTO(400, e.getMessage()));
        }
    }

    // Cerca per rang de valor preu, prefix i status true
    @GetMapping("/search/rang")
    public ResponseEntity<?> getProductsBetweenValuesTrue(@RequestParam Double min,
                                                           @RequestParam Double max,
                                                           @RequestParam String prefix,
                                                           @RequestParam String camp) {
        try {
            return ResponseEntity.ok(productService.getProductsBetweenValuesTrue(min, max, prefix, camp));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorDTO(400, e.getMessage()));
        }
    }

    // Cerca per valor mínim preu i status true
    @GetMapping("/search/minim")
    public ResponseEntity<?> getProductsOverMinValueTrue(@RequestParam Double min,
                                                          @RequestParam String camp) {
        try {
            return ResponseEntity.ok(productService.getProductsOverMinValueTrue(min, camp));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorDTO(400, e.getMessage()));
        }
    }

    // Top N productes amb millor relació qualitat-preu
    @GetMapping("/top-qualitat-preu")
    public ResponseEntity<?> getTopQualitatPreu(@RequestParam(required = false) Integer limit) {
        try {
            return ResponseEntity.ok(productService.getTopQualitatPreu(limit));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorDTO(500, e.getMessage()));
        }
    }

    // Top N productes nous amb millor valoració
    @GetMapping("/nous")
    public ResponseEntity<?> getNewProducts(@RequestParam String condicio,
                                             @RequestParam(required = false) Integer limit) {
        try {
            return ResponseEntity.ok(productService.getNewProducts(condicio, limit));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorDTO(400, e.getMessage()));
        }
    }

    // Cerca per lots paginats de 5 productes
    @GetMapping("/paginats")
    public ResponseEntity<?> getProductsPaginated(@RequestParam int pagina) {
        try {
            return ResponseEntity.ok(productService.getProductsPaginated(pagina));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorDTO(500, e.getMessage()));
        }
    }
}