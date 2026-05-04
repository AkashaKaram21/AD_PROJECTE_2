package com.ra34.projecte2.controller;

import com.ra34.projecte2.dto.RoleAssignmentDTO;
import com.ra34.projecte2.dto.UserRequestDTO;
import com.ra34.projecte2.dto.UserResponseDTO;
import com.ra34.projecte2.dto.UserRoleResponseDTO;
import com.ra34.projecte2.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserRequestDTO dto) {
        try {
            Optional<UserResponseDTO> result = userService.createUser(dto);
            
            if (result.isPresent()) {
                return ResponseEntity.status(HttpStatus.CREATED).body(result.get());
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
        try {
            Optional<UserResponseDTO> result = userService.getUserById(id);
            
            if (result.isPresent()) {
                return ResponseEntity.ok(result.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        try {
            List<UserResponseDTO> users = userService.getAllUsers();
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long id, 
                                                       @RequestBody UserRequestDTO dto) {
        try {
            Optional<UserResponseDTO> result = userService.updateUser(id, dto);
            
            if (result.isPresent()) {
                return ResponseEntity.ok(result.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/{id}/roles")
    public ResponseEntity<UserRoleResponseDTO> assignRoles(@PathVariable Long id, 
                                                            @RequestBody RoleAssignmentDTO dto) {
        try {
            Optional<UserRoleResponseDTO> result = userService.assignRoles(id, dto);
            
            if (result.isPresent()) {
                return ResponseEntity.ok(result.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}/roles")
    public ResponseEntity<UserRoleResponseDTO> removeRoles(@PathVariable Long id, 
                                                            @RequestBody RoleAssignmentDTO dto) {
        try {
            Optional<UserRoleResponseDTO> result = userService.removeRoles(id, dto);
            
            if (result.isPresent()) {
                return ResponseEntity.ok(result.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        try {
            Optional<Boolean> result = userService.deleteUser(id);
            
            if (result.isPresent()) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}