package com.ra34.projecte2.controller;

import com.ra34.projecte2.dto.*;
import com.ra34.projecte2.service.ProjecteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
public class MainController {

    @Autowired private ProjecteService service;

    @PostMapping("/users")
    public ResponseEntity<?> crearUsuari(@RequestBody UserRequestDTO dto) {
        return ResponseEntity.ok(service.crearUsuari(dto));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> consultarUsuari(@PathVariable Long id) {
        return ResponseEntity.ok(service.consultarUsuari(id));
    }

    @DeleteMapping("/customers/{id}/addresses")
    public ResponseEntity<?> esborrarAdreces(@PathVariable Long id) {
        service.esborrarAdreces(id);
        return ResponseEntity.ok("Adreces eliminades correctament");
    }

    @GetMapping("/customers")
    public ResponseEntity<?> consultarCustomers() {
        return ResponseEntity.ok(service.consultarTotsCustomers());
    }

    @PostMapping("/orders")
    public ResponseEntity<?> crearOrder(@RequestBody OrderRequestDTO dto) {
        return ResponseEntity.ok(service.crearOrder(dto));
    }

    @PatchMapping("/orders/{id}/process")
    public ResponseEntity<?> processarOrder(@PathVariable Long id) {
        return ResponseEntity.ok(service.processarOrder(id));
    }

    @DeleteMapping("/users/{id}/roles")
    public ResponseEntity<?> esborrarRols(@PathVariable Long id, @RequestBody List<Long> roleIds) {
        return ResponseEntity.ok(service.esborrarRols(id, roleIds));
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<?> modificarUsuari(@PathVariable Long id, @RequestBody UserRequestDTO dto) {
        return ResponseEntity.ok(service.modificarUsuari(id, dto));
    }

    @GetMapping("/users")
    public ResponseEntity<?> consultarUsuaris() {
        return ResponseEntity.ok(service.consultarTotsUsuaris());
    }

    @PostMapping("/customers/{id}/addresses")
    public ResponseEntity<?> afegirAdreces(@PathVariable Long id, @RequestBody List<AddressDTO> adreces) {
        return ResponseEntity.ok(service.afegirAdreces(id, adreces));
    }

    @GetMapping("/customers/{id}")
    public ResponseEntity<?> consultarCustomer(@PathVariable Long id) {
        return ResponseEntity.ok(service.consultarCustomer(id));
    }

    @PostMapping("/orders/{id}/products")
    public ResponseEntity<?> afegirProductesAOrder(@PathVariable Long id, @RequestBody List<Long> productIds) {
        return ResponseEntity.ok(service.afegirProductesAOrder(id, productIds));
    }

    @PatchMapping("/orders/{id}/cancel")
    public ResponseEntity<?> cancelarOrder(@PathVariable Long id) {
        return ResponseEntity.ok(service.cancelarOrder(id));
    }

    @PostMapping("/users/{id}/roles")
    public ResponseEntity<?> afegirRols(@PathVariable Long id, @RequestBody List<Long> roleIds) {
        return ResponseEntity.ok(service.afegirRols(id, roleIds));
    }
}