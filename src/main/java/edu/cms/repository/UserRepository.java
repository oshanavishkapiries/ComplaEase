package edu.cms.repository;

import edu.cms.dto.UserDTO;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    
    Optional<UserDTO> save(UserDTO userDTO);

    
    Optional<UserDTO> findById(Integer id);

    
    Optional<UserDTO> findByUsername(String username);

    
    Optional<UserDTO> findByUsernameAndPassword(String username, String password);

    
    List<UserDTO> findAll();

    
    boolean update(UserDTO userDTO);

    
    boolean deleteById(Integer id);

    
    boolean existsByUsername(String username);
}