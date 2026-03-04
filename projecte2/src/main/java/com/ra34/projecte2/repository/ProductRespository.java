package com.ra34.projecte2.repository;

import java.util.List;

import org.hibernate.boot.models.JpaAnnotations;
import org.springframework.data.repository.CrudRepository;

import com.ra34.projecte2.model.Product;

public interface ProductRespository extends JpaAnnotations<Product, Long> {

    List<Product> findbyNameandStatus();

    @query(select p from prduct p where )
    List<Product> findBuscar();

}
