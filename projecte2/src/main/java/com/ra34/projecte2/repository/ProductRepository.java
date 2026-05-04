package com.ra34.projecte2.repository;

import com.ra34.projecte2.model.Product;
import com.ra34.projecte2.model.Condition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositori per a l'entitat Product
 * Conté tots els mètodes de consulta personalitzats amb JPQL
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // Buscar por nombre (contiene) y disponibles
    List<Product> findByNameContainingAndStatusTrue(String prefix);
    
    // Buscar por condición y disponibles
    List<Product> findByConditionAndStatusTrue(Condition condition);
    
    // Ordenar por precio ASC/DESC y disponibles
    List<Product> findByStatusTrueOrderByPriceAsc();
    
    List<Product> findByStatusTrueOrderByPriceDesc();
    
    // Ordenar por rating ASC/DESC y disponibles
    List<Product> findByStatusTrueOrderByRatingAsc();
    
    List<Product> findByStatusTrueOrderByRatingDesc();
    
    // Paginación (heredado de JpaRepository)
    Page<Product> findByStatusTrue(Pageable pageable);
    
    
    // Rango de precio con límite
    @Query("SELECT p FROM Product p WHERE p.price BETWEEN :priceMin AND :priceMax " +
            "AND p.status = true ORDER BY p.price ASC")
    List<Product> findByPriceRange(@Param("priceMin") Double priceMin,
                                    @Param("priceMax") Double priceMax,
                                    Pageable pageable);
    
    // Top 5 mejor relación calidad-precio (rating / price)
    @Query("SELECT p FROM Product p WHERE p.status = true AND p.rating IS NOT NULL " +
            "ORDER BY (p.rating / p.price) DESC")
    List<Product> findTop5ByQualityPrice(Pageable pageable);
    
    // Rango de rating con límite
    @Query("SELECT p FROM Product p WHERE p.rating BETWEEN :ratingMin AND :ratingMax " +
            "AND p.status = true ORDER BY p.rating DESC")
    List<Product> findByRatingRange(@Param("ratingMin") Double ratingMin,
                                     @Param("ratingMax") Double ratingMax,
                                     Pageable pageable);
    
    // Productos con precio superior al indicado
    @Query("SELECT p FROM Product p WHERE p.price > :price AND p.status = true ORDER BY p.price ASC")
    List<Product> findByPriceGreaterThan(@Param("price") Double price);
    
    // Top 10 productos nuevos (NOU) con mejor valoración
    @Query("SELECT p FROM Product p WHERE p.condition = 'NOU' AND p.status = true " +
            "AND p.rating IS NOT NULL ORDER BY p.rating DESC")
    List<Product> findTop10NewWithBestRating(Pageable pageable); 
         
}
