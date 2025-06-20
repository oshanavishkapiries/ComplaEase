package edu.cms.dao;

import edu.cms.dto.ComplaintDTO;
import edu.cms.repository.ComplaintRepository;
import edu.cms.util.CrudUtil;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class ComplaintDAOImpl implements ComplaintRepository {

    private final CrudUtil crudUtil;

    public ComplaintDAOImpl(DataSource dataSource) {
        this.crudUtil = new CrudUtil(dataSource);
    }

    @Override
    public Optional<ComplaintDTO> save(ComplaintDTO complaintDTO) {
        String sql = "INSERT INTO complaints (user_id, title, description, status) VALUES (?, ?, ?, ?)";

        return crudUtil.executeInsert(sql,
                complaintDTO.getUserId(),
                complaintDTO.getTitle(),
                complaintDTO.getDescription(),
                complaintDTO.getStatus() != null ? complaintDTO.getStatus() : "PENDING")
                .map(id -> {
                    complaintDTO.setId(id);
                    return complaintDTO;
                });
    }

    @Override
    public Optional<ComplaintDTO> findById(Integer id) {
        String sql = "SELECT * FROM complaints WHERE id = ?";
        return crudUtil.executeQuery(sql, this::mapResultSetToComplaintDTO, id);
    }

    @Override
    public List<ComplaintDTO> findAll() {
        String sql = "SELECT * FROM complaints ORDER BY created_at DESC";
        return crudUtil.executeQueryList(sql, this::mapResultSetToComplaintDTO);
    }

    @Override
    public List<ComplaintDTO> findByUserId(Integer userId) {
        String sql = "SELECT * FROM complaints WHERE user_id = ? ORDER BY created_at DESC";
        return crudUtil.executeQueryList(sql, this::mapResultSetToComplaintDTO, userId);
    }

    @Override
    public List<ComplaintDTO> findByStatus(String status) {
        String sql = "SELECT * FROM complaints WHERE status = ? ORDER BY created_at DESC";
        return crudUtil.executeQueryList(sql, this::mapResultSetToComplaintDTO, status);
    }

    @Override
    public boolean update(ComplaintDTO complaintDTO) {
        String sql = "UPDATE complaints SET title = ?, description = ? WHERE id = ?";
        int affectedRows = crudUtil.executeUpdate(sql,
                complaintDTO.getTitle(),
                complaintDTO.getDescription(),
                complaintDTO.getId());
        return affectedRows > 0;
    }

    @Override
    public boolean updateStatus(Integer id, String status, String remarks) {
        String sql = "UPDATE complaints SET status = ?, remarks = ? WHERE id = ?";
        int affectedRows = crudUtil.executeUpdate(sql, status, remarks, id);
        return affectedRows > 0;
    }

    @Override
    public boolean deleteById(Integer id) {
        String sql = "DELETE FROM complaints WHERE id = ?";
        int affectedRows = crudUtil.executeUpdate(sql, id);
        return affectedRows > 0;
    }

    @Override
    public long countByUserId(Integer userId) {
        String sql = "SELECT COUNT(*) FROM complaints WHERE user_id = ?";
        return crudUtil.executeQuery(sql, rs -> {
            try {
                return rs.getLong(1);
            } catch (SQLException e) {
                System.err.println("Error counting complaints by user ID: " + e.getMessage());
                return 0L;
            }
        }, userId).orElse(0L);
    }

    @Override
    public long countByStatus(String status) {
        String sql = "SELECT COUNT(*) FROM complaints WHERE status = ?";
        return crudUtil.executeQuery(sql, rs -> {
            try {
                return rs.getLong(1);
            } catch (SQLException e) {
                System.err.println("Error counting complaints by status: " + e.getMessage());
                return 0L;
            }
        }, status).orElse(0L);
    }

    private ComplaintDTO mapResultSetToComplaintDTO(ResultSet rs) {
        try {
            return ComplaintDTO.builder()
                    .id(rs.getInt("id"))
                    .userId(rs.getInt("user_id"))
                    .title(rs.getString("title"))
                    .description(rs.getString("description"))
                    .status(rs.getString("status"))
                    .remarks(rs.getString("remarks"))
                    .createdAt(convertTimestampToLocalDateTime(rs.getTimestamp("created_at")))
                    .updatedAt(convertTimestampToLocalDateTime(rs.getTimestamp("updated_at")))
                    .build();
        } catch (SQLException e) {
            System.err.println("Error mapping ResultSet to ComplaintDTO: " + e.getMessage());
            return null;
        }
    }

    private LocalDateTime convertTimestampToLocalDateTime(Timestamp timestamp) {
        return timestamp != null ? timestamp.toLocalDateTime() : null;
    }
}