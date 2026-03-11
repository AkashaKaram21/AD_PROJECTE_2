package com.ra34.projecte2.repository;

import com.ra34.projecte2.model.Product;
import com.ra34.projecte2.model.ProductCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

        //Fer una consulta que retorni una llista de productes que el nom contingui el valor de prefix i que el camp status sigui true.
        List<Product> findByNameContainingAndStatusTrue(String prefix);

        List<Product> findByStatusTrue(Pageable pageable);

        //Fer una consulta que retorni una llista de productes que la condició sigui el valor de condition i el camp status sigui true.
        List<Product> findByConditionAndStatusTrue(ProductCondition condition);

        @Query("SELECT p FROM Product p WHERE p.price >= :priceMin AND p.price <= :priceMax AND p.status = true")
        List<Product> findByPriceRangeAndStatusTrue(
                @Param("priceMin") Double priceMin,
                @Param("priceMax") Double priceMax,
                Pageable pageable
        );


        //Fer una consulta que retorni una llista de productes ordenats pel camp rating ascendent o descendent segons el valor de order i el camp status sigui true.
        @Query("SELECT p FROM Product p WHERE p.status = true AND p.rating IS NOT NULL " +
                "ORDER BY (p.rating / p.price) DESC LIMIT 5")
        List<Product> findTop5BestQualityPrice();

        @Query("SELECT p FROM Product p WHERE p.rating >= :ratingMin AND p.rating <= :ratingMax AND p.status = true")
        List<Product> findByRatingRangeAndStatusTrue(
                @Param("ratingMin") Double ratingMin,
                @Param("ratingMax") Double ratingMax,
                Pageable pageable
        );
        @Query("SELECT p FROM Product p WHERE p.condition = 'NOU' AND p.status = true " +
                "AND p.rating IS NOT NULL ORDER BY p.rating DESC LIMIT 10")
        List<Product> findTop10NewProductsByRating();

        @Query("SELECT p FROM Product p WHERE p.status = true")
        Page<Product> findAllActiveWithPagination(Pageable pageable);
}
