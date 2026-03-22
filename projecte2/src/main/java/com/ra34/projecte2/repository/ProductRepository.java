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

    // Cerca per nom que contingui el valor i que el camp status sigui true
    @Query("SELECT p FROM Product p WHERE p.name LIKE %:nom% AND p.status = true")
    List<Product> trobarPerNomQueContinguiIEstatActiu(@Param("nom") String nom);

    // Cerca per condició i que el camp status sigui true
    @Query("SELECT p FROM Product p WHERE p.condition = :condicio AND p.status = true")
    List<Product> trobarPerCondicioIEstatActiu(@Param("condicio") ProductCondition condicio);

    // Ordenació per preu ascendent i que el camp status sigui true
    @Query("SELECT p FROM Product p WHERE p.status = true ORDER BY p.price ASC")
    List<Product> trobarActiusOrdenatsPriceAscendent();

    // Ordenació per preu descendent i que el camp status sigui true
    @Query("SELECT p FROM Product p WHERE p.status = true ORDER BY p.price DESC")
    List<Product> trobarActiusOrdenatsPriceDescendent();

    // Ordenació per rating ascendent i que el camp status sigui true
    @Query("SELECT p FROM Product p WHERE p.status = true ORDER BY p.rating ASC")
    List<Product> trobarActiusOrdenatsRatingAscendent();

    // Ordenació per rating descendent i que el camp status sigui true
    @Query("SELECT p FROM Product p WHERE p.status = true ORDER BY p.rating DESC")
    List<Product> trobarActiusOrdenatsRatingDescendent();

    // Cerca per rang de preu, prefix i que el camp status sigui true
    @Query("SELECT p FROM Product p WHERE p.status = true AND p.price BETWEEN :min AND :max AND p.name LIKE %:prefix%")
    List<Product> trobarPerRangPreuIPrefixIEstatActiu(@Param("min") Double min, @Param("max") Double max, @Param("prefix") String prefix);

    // Cerca per rang de rating, prefix i que el camp status sigui true
    @Query("SELECT p FROM Product p WHERE p.status = true AND p.rating BETWEEN :min AND :max AND p.name LIKE %:prefix%")
    List<Product> trobarPerRangRatingIPrefixIEstatActiu(@Param("min") Double min, @Param("max") Double max, @Param("prefix") String prefix);

    // Cerca per preu mínim i que el camp status sigui true
    @Query("SELECT p FROM Product p WHERE p.status = true AND p.price >= :min")
    List<Product> trobarActiusPreuMinim(@Param("min") Double min);

    // Cerca per rating mínim i que el camp status sigui true
    @Query("SELECT p FROM Product p WHERE p.status = true AND p.rating >= :min")
    List<Product> trobarActiusRatingMinim(@Param("min") Double min);

    // Consulta per obtenir el top N basat en el càlcul price
    @Query("SELECT p FROM Product p WHERE p.status = true AND p.price > 0 ORDER BY (p.rating / p.price) DESC")
    List<Product> trobarTopQualitatPreu(Pageable pageable);

    // Consulta top N productes per condició amb major rating i més nous
    @Query("SELECT p FROM Product p WHERE p.condition = :condicio AND p.status = true ORDER BY p.dataCreated DESC, p.rating DESC")
    List<Product> trobarMillorsProductesPerCondicio(@Param("condicio") ProductCondition condicio, Pageable pageable);

    // Cerca per lots paginats i que el camp status sigui true
    Page<Product> findByStatusTrue(Pageable pageable);
}