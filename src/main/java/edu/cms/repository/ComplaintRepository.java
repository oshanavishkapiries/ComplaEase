package edu.cms.repository;

import edu.cms.dto.ComplaintDTO;

import java.util.List;
import java.util.Optional;

public interface ComplaintRepository {

    
    Optional<ComplaintDTO> save(ComplaintDTO complaintDTO);

    
    Optional<ComplaintDTO> findById(Integer id);

    
    List<ComplaintDTO> findAll();

    
    List<ComplaintDTO> findByUserId(Integer userId);

    
    List<ComplaintDTO> findByStatus(String status);

    
    boolean update(ComplaintDTO complaintDTO);

    
    boolean updateStatus(Integer id, String status, String remarks);

    
    boolean deleteById(Integer id);

    
    long countByUserId(Integer userId);

    
    long countByStatus(String status);
}