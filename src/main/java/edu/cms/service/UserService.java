package edu.cms.service;

import edu.cms.dto.UserDTO;
import edu.cms.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@Slf4j
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    
    public Optional<UserDTO> registerUser(UserDTO userDTO) {
        try {
            
            if (userRepository.existsByUsername(userDTO.getUsername())) {
                log.warn("Username already exists: {}", userDTO.getUsername());
                return Optional.empty();
            }

            
            if (userDTO.getRole() == null || userDTO.getRole().trim().isEmpty()) {
                userDTO.setRole("EMPLOYEE");
            }

            return userRepository.save(userDTO);
        } catch (Exception e) {
            log.error("Error registering user: {}", userDTO.getUsername(), e);
            return Optional.empty();
        }
    }

    
    public Optional<UserDTO> authenticateUser(String username, String password) {
        try {
            return userRepository.findByUsernameAndPassword(username, password);
        } catch (Exception e) {
            log.error("Error authenticating user: {}", username, e);
            return Optional.empty();
        }
    }

    
    public Optional<UserDTO> getUserById(Integer id) {
        try {
            return userRepository.findById(id);
        } catch (Exception e) {
            log.error("Error getting user by ID: {}", id, e);
            return Optional.empty();
        }
    }

    
    public Optional<UserDTO> getUserByUsername(String username) {
        try {
            return userRepository.findByUsername(username);
        } catch (Exception e) {
            log.error("Error getting user by username: {}", username, e);
            return Optional.empty();
        }
    }

    
    public List<UserDTO> getAllUsers() {
        try {
            return userRepository.findAll();
        } catch (Exception e) {
            log.error("Error getting all users", e);
            return List.of();
        }
    }

    
    public boolean updateUser(UserDTO userDTO) {
        try {
            return userRepository.update(userDTO);
        } catch (Exception e) {
            log.error("Error updating user: {}", userDTO.getId(), e);
            return false;
        }
    }

    
    public boolean deleteUser(Integer id) {
        try {
            return userRepository.deleteById(id);
        } catch (Exception e) {
            log.error("Error deleting user: {}", id, e);
            return false;
        }
    }

    
    public boolean isAdmin(Integer userId) {
        try {
            return userRepository.findById(userId)
                    .map(user -> "ADMIN".equalsIgnoreCase(user.getRole()))
                    .orElse(false);
        } catch (Exception e) {
            log.error("Error checking if user is admin: {}", userId, e);
            return false;
        }
    }
}