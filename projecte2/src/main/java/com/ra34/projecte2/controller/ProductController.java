package com.ra34.projecte2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import com.ra34.projecte2.service.ProducteService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProducteService producteService;

    @PostMapping("/csv-upload")
    public ResponseEntity<String> carregarCSV (@RequestParam MultipartFile csvFile) {
        return producteService.carregarCSV(csvFile);
    }

}   
