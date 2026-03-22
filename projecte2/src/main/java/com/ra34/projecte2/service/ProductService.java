package com.ra34.projecte2.service;

import com.ra34.projecte2.dto.ProductResponseDTO;
import com.ra34.projecte2.model.Product;
import com.ra34.projecte2.model.ProductCondition;
import com.ra34.projecte2.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    // Injecció per constructor 
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // converteix Product → ProductResponseDTO
    private ProductResponseDTO toDTO(Product p) {
        return new ProductResponseDTO(
                p.getId(), p.getName(), p.getDescription(),
                p.getStock(), p.getPrice(), p.getRating(), p.getCondition()
        );
    }

    // converteix List<Product> → List<ProductResponseDTO>
    private List<ProductResponseDTO> toDTOList(List<Product> products) {
        List<ProductResponseDTO> dtos = new ArrayList<>();
        for (Product p : products) {
            dtos.add(toDTO(p));
        }
        return dtos;
    }

    // Càrrega massiva de dades d'un fitxer en format .csv
    @Transactional
    public int processCsv(MultipartFile file) throws Exception {
        int count = 0;
        int liniaNum = 1;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String linia = br.readLine(); // capçalera
            while ((linia = br.readLine()) != null) {
                liniaNum++;
                if (linia.trim().isEmpty()) continue;
                String[] data = linia.split(",");
                if (data.length < 6) {
                    throw new Exception("Falten columnes a la línia " + liniaNum);
                }
                try {
                    Product p = new Product();
                    p.setName(data[0].trim());
                    p.setDescription(data[1].trim());
                    p.setStock(Integer.parseInt(data[2].trim()));
                    p.setPrice(Double.parseDouble(data[3].trim()));
                    p.setRating(Double.parseDouble(data[4].trim()));
                    p.setCondition(ProductCondition.valueOf(data[5].trim().toUpperCase()));
                    p.setStatus(true);
                    p.setDataCreated(LocalDateTime.now());
                    productRepository.save(p);
                    count++;
                } catch (Exception e) {
                    throw new Exception("Error a la línia nº " + liniaNum + ": " + e.getMessage());
                }
            }
        }
        return count;
    }

    // Consultar tots els productes
    public List<ProductResponseDTO> findAll() {
        return toDTOList(productRepository.findAll());
    }

    // Consultar un producte per id
    public ProductResponseDTO findById(Long id) {
        Optional<Product> p = productRepository.findById(id);
        if (p.isPresent()) {
            return toDTO(p.get());
        }
        throw new RuntimeException("Producte no trobat amb ID: " + id);
    }

    // Afegir un producte
    public ProductResponseDTO saveProduct(Product product) {
        product.setStatus(true);
        product.setDataCreated(LocalDateTime.now());
        return toDTO(productRepository.save(product));
    }

    // Actualitzar un producte sencer
    public ProductResponseDTO updateProduct(Long id, Product productDTO) {
        Optional<Product> p = productRepository.findById(id);
        if (p.isPresent()) {
            Product product = p.get();
            product.setName(productDTO.getName());
            product.setDescription(productDTO.getDescription());
            product.setStock(productDTO.getStock());
            product.setPrice(productDTO.getPrice());
            product.setRating(productDTO.getRating());
            product.setCondition(productDTO.getCondition());
            product.setStatus(true);
            product.setDataUpdated(LocalDateTime.now());
            return toDTO(productRepository.save(product));
        }
        throw new RuntimeException("Producte no trobat amb ID: " + id);
    }

    // Modificar l'estoc d'un producte
    public ProductResponseDTO updateEstoc(Long id, int stock) {
        Optional<Product> pOpt = productRepository.findById(id);
        if (pOpt.isPresent()) {
            Product p = pOpt.get();
            p.setStock(stock);
            p.setDataUpdated(LocalDateTime.now());
            return toDTO(productRepository.save(p));
        }
        throw new RuntimeException("Producte no trobat amb ID: " + id);
    }

    // Modificar el preu d'un producte
    public ProductResponseDTO updatePrice(Long id, double price) {
        Optional<Product> p = productRepository.findById(id);
        if (p.isPresent()) {
            Product product = p.get();
            product.setPrice(price);
            product.setDataUpdated(LocalDateTime.now());
            return toDTO(productRepository.save(product));
        }
        throw new RuntimeException("Producte no trobat amb ID: " + id);
    }

    // Borrat físic d'un producte
    public void deleteProduct(Long id) {
        Optional<Product> p = productRepository.findById(id);
        if (p.isPresent()) {
            productRepository.deleteById(id);
        } else {
            throw new RuntimeException("Producte no trobat amb ID: " + id);
        }
    }

    // Borrat lògic d'un producte
    public void deleteLogicProduct(Long id) {
        Optional<Product> p = productRepository.findById(id);
        if (p.isPresent()) {
            Product product = p.get();
            product.setStatus(false);
            product.setDataUpdated(LocalDateTime.now());
            productRepository.save(product);
        } else {
            throw new RuntimeException("Producte no trobat amb ID: " + id);
        }
    }

    // Cerca per nom que contingui el valor i status true
    @Transactional(readOnly = true)
    public List<ProductResponseDTO> searchByName(String prefix) {
        return toDTOList(productRepository.trobarPerNomQueContinguiIEstatActiu(prefix));
    }

    // Cerca per condició i status true
    @Transactional(readOnly = true)
    public List<ProductResponseDTO> findByCondition(String condicio) {
        return toDTOList(productRepository.trobarPerCondicioIEstatActiu(
                ProductCondition.valueOf(condicio.toUpperCase())
        ));
    }

    // Cerca per camp preu i ordre (asc/desc), amb status true
    @Transactional(readOnly = true)
    public List<ProductResponseDTO> getProductsOrderedByCamp(String camp, String ordre) {
        boolean isDesc = "desc".equalsIgnoreCase(ordre);
        List<Product> productes;

        if ("rating".equalsIgnoreCase(camp)) {
            productes = isDesc
                    ? productRepository.trobarActiusOrdenatsRatingDescendent()
                    : productRepository.trobarActiusOrdenatsRatingAscendent();
        } else {
            productes = isDesc
                    ? productRepository.trobarActiusOrdenatsPriceDescendent()
                    : productRepository.trobarActiusOrdenatsPriceAscendent();
        }
        return toDTOList(productes);
    }

    // Cerca per rang de valor preu, prefix i status true
    @Transactional(readOnly = true)
    public List<ProductResponseDTO> getProductsBetweenValuesTrue(Double min, Double max,
                                                                  String prefix, String camp) {
        if ("price".equalsIgnoreCase(camp)) {
            return toDTOList(productRepository.trobarPerRangPreuIPrefixIEstatActiu(min, max, prefix));
        } else if ("rating".equalsIgnoreCase(camp)) {
            return toDTOList(productRepository.trobarPerRangRatingIPrefixIEstatActiu(min, max, prefix));
        }
        throw new RuntimeException("El camp no és vàlid, ha de ser 'price' o 'rating'.");
    }

    // Cerca per valor mínim preu i status true
    @Transactional(readOnly = true)
    public List<ProductResponseDTO> getProductsOverMinValueTrue(Double min, String camp) {
        if ("price".equalsIgnoreCase(camp)) {
            return toDTOList(productRepository.trobarActiusPreuMinim(min));
        } else if ("rating".equalsIgnoreCase(camp)) {
            return toDTOList(productRepository.trobarActiusRatingMinim(min));
        }
        throw new RuntimeException("El camp no és vàlid, ha de ser 'price' o 'rating'.");
    }

    // Top N productes amb millor relació qualitat-preu
    @Transactional(readOnly = true)
    public List<ProductResponseDTO> getTopQualitatPreu(Integer limit) {
        Pageable num = PageRequest.of(0, limit != null ? limit : 5);
        return toDTOList(productRepository.trobarTopQualitatPreu(num));
    }

    // Top N productes nous amb millor valoració
    @Transactional(readOnly = true)
    public List<ProductResponseDTO> getNewProducts(String condicio, Integer limit) {
        ProductCondition productCondition = ProductCondition.valueOf(condicio.toUpperCase());
        if (productCondition == ProductCondition.NOU) {
            Pageable num = PageRequest.of(0, limit != null ? limit : 10);
            return toDTOList(productRepository.trobarMillorsProductesPerCondicio(productCondition, num));
        }
        throw new RuntimeException("Aquesta cerca només admet la condició 'NOU'");
    }

    // Cerca per lots paginats de 5 productes
    @Transactional(readOnly = true)
    public List<ProductResponseDTO> getProductsPaginated(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 5);
        Page<Product> productPage = productRepository.findByStatusTrue(pageable);
        return toDTOList(productPage.getContent());
    }
}