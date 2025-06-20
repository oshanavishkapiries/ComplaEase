package edu.cms.service;

import edu.cms.dto.ComplaintDTO;
import edu.cms.repository.ComplaintRepository;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
public class ComplaintService {

    private final ComplaintRepository complaintRepository;

    public ComplaintService(ComplaintRepository complaintRepository) {
        this.complaintRepository = complaintRepository;
    }

    
    public Optional<ComplaintDTO> createComplaint(ComplaintDTO complaintDTO) {
        try {
            
            if (complaintDTO.getStatus() == null || complaintDTO.getStatus().trim().isEmpty()) {
                complaintDTO.setStatus("PENDING");
            }

            
            complaintDTO.setCreatedAt(LocalDateTime.now());
            complaintDTO.setUpdatedAt(LocalDateTime.now());

            return complaintRepository.save(complaintDTO);
        } catch (Exception e) {
            log.error("Error creating complaint for user: {}", complaintDTO.getUserId(), e);
            return Optional.empty();
        }
    }

    
    public Optional<ComplaintDTO> getComplaintById(Integer id) {
        try {
            return complaintRepository.findById(id);
        } catch (Exception e) {
            log.error("Error getting complaint by ID: {}", id, e);
            return Optional.empty();
        }
    }

    
    public List<ComplaintDTO> getAllComplaints() {
        try {
            return complaintRepository.findAll();
        } catch (Exception e) {
            log.error("Error getting all complaints", e);
            return List.of();
        }
    }

    
    public List<ComplaintDTO> getComplaintsByUserId(Integer userId) {
        try {
            return complaintRepository.findByUserId(userId);
        } catch (Exception e) {
            log.error("Error getting complaints for user: {}", userId, e);
            return List.of();
        }
    }

    
    public List<ComplaintDTO> getComplaintsByStatus(String status) {
        try {
            return complaintRepository.findByStatus(status);
        } catch (Exception e) {
            log.error("Error getting complaints by status: {}", status, e);
            return List.of();
        }
    }

    
    public boolean updateComplaint(ComplaintDTO complaintDTO) {
        try {
            complaintDTO.setUpdatedAt(LocalDateTime.now());
            return complaintRepository.update(complaintDTO);
        } catch (Exception e) {
            log.error("Error updating complaint: {}", complaintDTO.getId(), e);
            return false;
        }
    }

    
    public boolean updateComplaintStatus(Integer id, String status, String remarks) {
        try {
            return complaintRepository.updateStatus(id, status, remarks);
        } catch (Exception e) {
            log.error("Error updating complaint status: {}", id, e);
            return false;
        }
    }

    
    public boolean deleteComplaint(Integer id) {
        try {
            return complaintRepository.deleteById(id);
        } catch (Exception e) {
            log.error("Error deleting complaint: {}", id, e);
            return false;
        }
    }

    
    public ComplaintStatistics getComplaintStatistics() {
        try {
            long totalComplaints = complaintRepository.countByUserId(1) +
                    complaintRepository.countByUserId(2) +
                    complaintRepository.countByUserId(3) +
                    complaintRepository.countByUserId(4) +
                    complaintRepository.countByUserId(5) +
                    complaintRepository.countByUserId(6);

            long pendingComplaints = complaintRepository.countByStatus("PENDING");
            long inProgressComplaints = complaintRepository.countByStatus("IN_PROGRESS");
            long resolvedComplaints = complaintRepository.countByStatus("RESOLVED");
            long closedComplaints = complaintRepository.countByStatus("CLOSED");

            return ComplaintStatistics.builder()
                    .totalComplaints(totalComplaints)
                    .pendingComplaints(pendingComplaints)
                    .inProgressComplaints(inProgressComplaints)
                    .resolvedComplaints(resolvedComplaints)
                    .closedComplaints(closedComplaints)
                    .build();
        } catch (Exception e) {
            log.error("Error getting complaint statistics", e);
            return ComplaintStatistics.builder().build();
        }
    }

    
    public ComplaintStatistics getComplaintStatisticsByUser(Integer userId) {
        try {
            long totalComplaints = complaintRepository.countByUserId(userId);

            List<ComplaintDTO> userComplaints = complaintRepository.findByUserId(userId);
            long pendingComplaints = userComplaints.stream()
                    .filter(c -> "PENDING".equalsIgnoreCase(c.getStatus()))
                    .count();
            long inProgressComplaints = userComplaints.stream()
                    .filter(c -> "IN_PROGRESS".equalsIgnoreCase(c.getStatus()))
                    .count();
            long resolvedComplaints = userComplaints.stream()
                    .filter(c -> "RESOLVED".equalsIgnoreCase(c.getStatus()))
                    .count();
            long closedComplaints = userComplaints.stream()
                    .filter(c -> "CLOSED".equalsIgnoreCase(c.getStatus()))
                    .count();

            return ComplaintStatistics.builder()
                    .totalComplaints(totalComplaints)
                    .pendingComplaints(pendingComplaints)
                    .inProgressComplaints(inProgressComplaints)
                    .resolvedComplaints(resolvedComplaints)
                    .closedComplaints(closedComplaints)
                    .build();
        } catch (Exception e) {
            log.error("Error getting complaint statistics for user: {}", userId, e);
            return ComplaintStatistics.builder().build();
        }
    }

    
    @lombok.Data
    @lombok.Builder
    @lombok.NoArgsConstructor
    @lombok.AllArgsConstructor
    public static class ComplaintStatistics {
        private long totalComplaints;
        private long pendingComplaints;
        private long inProgressComplaints;
        private long resolvedComplaints;
        private long closedComplaints;
    }
}