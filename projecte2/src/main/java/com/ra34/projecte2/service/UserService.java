package com.ra34.projecte2.service;

import com.ra34.projecte2.dto.RoleAssignmentDTO;
import com.ra34.projecte2.dto.UserRequestDTO;
import com.ra34.projecte2.dto.UserResponseDTO;
import com.ra34.projecte2.dto.UserRoleResponseDTO;
import com.ra34.projecte2.mapper.UserMapper;
import com.ra34.projecte2.model.Customer;
import com.ra34.projecte2.model.Role;
import com.ra34.projecte2.model.User;
import com.ra34.projecte2.repository.RoleRepository;
import com.ra34.projecte2.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Transactional
    public Optional<UserResponseDTO> createUser(UserRequestDTO dto) {
        try {
            // Comprobar si el email ya existe
            Optional<User> existingUser = userRepository.findByEmail(dto.getEmail());
            if (existingUser.isPresent()) {
                return Optional.empty();
            }

            // Crear el usuario
            User user = new User(dto.getEmail(), dto.getPassword());
            
            // Crear el customer
            Customer customer = new Customer(dto.getFirstName(), dto.getLastName(), dto.getPhone());

            // Establecer la relación en ambos lados
            customer.setUser(user);
            user.setCustomer(customer);

            // Guardar el usuario (cascade guardará el customer)
            userRepository.save(user);
            
            return Optional.of(UserMapper.toResponseDTO(user));
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public Optional<UserResponseDTO> getUserById(Long id) {
        try {
            Optional<User> user = userRepository.findById(id);
            if (user.isPresent()) {
                return Optional.of(UserMapper.toResponseDTO(user.get()));
            }
            return Optional.empty();
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public List<UserResponseDTO> getAllUsers() {
        try {
            List<User> users = userRepository.findAll();
            List<UserResponseDTO> dtos = new ArrayList<>();
            
            for (User user : users) {
                dtos.add(UserMapper.toResponseDTO(user));
            }
            
            return dtos;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Transactional
    public Optional<UserResponseDTO> updateUser(Long id, UserRequestDTO dto) {
        try {
            Optional<User> userOpt = userRepository.findById(id);
            
            if (userOpt.isPresent()) {
                User user = userOpt.get();
                user.setEmail(dto.getEmail());
                
                if (user.getCustomer() != null) {
                    user.getCustomer().setFirstName(dto.getFirstName());
                    user.getCustomer().setLastName(dto.getLastName());
                    user.getCustomer().setPhone(dto.getPhone());
                }
                
                userRepository.save(user);
                return Optional.of(UserMapper.toResponseDTO(user));
            }
            
            return Optional.empty();
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Transactional
    public Optional<UserRoleResponseDTO> assignRoles(Long userId, RoleAssignmentDTO dto) {
        try {
            Optional<User> userOpt = userRepository.findById(userId);
            
            if (userOpt.isPresent()) {
                User user = userOpt.get();
                
                // Obtener todos los roles por sus IDs
                List<Role> roles = roleRepository.findAllById(dto.getRoleIds());
                
                // Limpiar los roles actuales y agregar los nuevos
                user.getRoles().clear();
                for (Role role : roles) {
                    user.getRoles().add(role);
                }
                
                userRepository.save(user);
                return Optional.of(UserMapper.toRoleResponseDTO(user));
            }
            
            return Optional.empty();
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Transactional
    public Optional<UserRoleResponseDTO> removeRoles(Long userId, RoleAssignmentDTO dto) {
        try {
            Optional<User> userOpt = userRepository.findById(userId);
            
            if (userOpt.isPresent()) {
                User user = userOpt.get();
                
                // Eliminar los roles especificados
                List<Role> rolesToRemove = new ArrayList<>();
                for (Role role : user.getRoles()) {
                    if (dto.getRoleIds().contains(role.getId())) {
                        rolesToRemove.add(role);
                    }
                }
                
                for (Role role : rolesToRemove) {
                    user.getRoles().remove(role);
                }
                
                userRepository.save(user);
                return Optional.of(UserMapper.toRoleResponseDTO(user));
            }
            
            return Optional.empty();
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Transactional
    public Optional<Boolean> deleteUser(Long id) {
        try {
            Optional<User> userOpt = userRepository.findById(id);
            
            if (userOpt.isPresent()) {
                userRepository.deleteById(id);
                return Optional.of(true);
            }
            
            return Optional.empty();
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
}