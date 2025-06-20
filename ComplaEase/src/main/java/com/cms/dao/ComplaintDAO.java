package com.cms.dao;

import com.cms.model.Complaint;
import com.cms.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Complaint operations in the Complaint Management
 * System
 */
public class ComplaintDAO {

    /**
     * Get complaint by ID
     * 
     * @param id the complaint ID
     * @return Complaint object if found, null otherwise
     */
    public Complaint getComplaintById(int id) {
        String sql = "SELECT c.*, u.username, u.full_name FROM complaints c " +
                "JOIN users u ON c.user_id = u.id WHERE c.id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToComplaint(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Get all complaints (for admin view)
     * 
     * @return List of all complaints with user information
     */
    public List<Complaint> getAllComplaints() {
        List<Complaint> complaints = new ArrayList<>();
        String sql = "SELECT c.*, u.username, u.full_name FROM complaints c " +
                "JOIN users u ON c.user_id = u.id ORDER BY c.created_at DESC";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                complaints.add(mapResultSetToComplaint(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return complaints;
    }

    /**
     * Get complaints by user ID (for employee view)
     * 
     * @param userId the user ID
     * @return List of complaints for the specified user
     */
    public List<Complaint> getComplaintsByUserId(int userId) {
        List<Complaint> complaints = new ArrayList<>();
        String sql = "SELECT c.*, u.username, u.full_name FROM complaints c " +
                "JOIN users u ON c.user_id = u.id WHERE c.user_id = ? ORDER BY c.created_at DESC";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    complaints.add(mapResultSetToComplaint(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return complaints;
    }

    /**
     * Get complaints by status
     * 
     * @param status the complaint status
     * @return List of complaints with the specified status
     */
    public List<Complaint> getComplaintsByStatus(Complaint.ComplaintStatus status) {
        List<Complaint> complaints = new ArrayList<>();
        String sql = "SELECT c.*, u.username, u.full_name FROM complaints c " +
                "JOIN users u ON c.user_id = u.id WHERE c.status = ? ORDER BY c.created_at DESC";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, status.toString());

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    complaints.add(mapResultSetToComplaint(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return complaints;
    }

    /**
     * Get complaints by priority
     * 
     * @param priority the complaint priority
     * @return List of complaints with the specified priority
     */
    public List<Complaint> getComplaintsByPriority(Complaint.ComplaintPriority priority) {
        List<Complaint> complaints = new ArrayList<>();
        String sql = "SELECT c.*, u.username, u.full_name FROM complaints c " +
                "JOIN users u ON c.user_id = u.id WHERE c.priority = ? ORDER BY c.created_at DESC";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, priority.toString());

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    complaints.add(mapResultSetToComplaint(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return complaints;
    }

    /**
     * Create a new complaint
     * 
     * @param complaint the complaint to create
     * @return true if successful, false otherwise
     */
    public boolean createComplaint(Complaint complaint) {
        String sql = "INSERT INTO complaints (title, description, status, priority, user_id) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, complaint.getTitle());
            pstmt.setString(2, complaint.getDescription());
            pstmt.setString(3, complaint.getStatus().toString());
            pstmt.setString(4, complaint.getPriority().toString());
            pstmt.setInt(5, complaint.getUserId());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Update complaint information
     * 
     * @param complaint the complaint to update
     * @return true if successful, false otherwise
     */
    public boolean updateComplaint(Complaint complaint) {
        String sql = "UPDATE complaints SET title = ?, description = ?, status = ?, priority = ?, admin_remarks = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, complaint.getTitle());
            pstmt.setString(2, complaint.getDescription());
            pstmt.setString(3, complaint.getStatus().toString());
            pstmt.setString(4, complaint.getPriority().toString());
            pstmt.setString(5, complaint.getAdminRemarks());
            pstmt.setInt(6, complaint.getId());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Update complaint status and admin remarks (for admin use)
     * 
     * @param id           the complaint ID
     * @param status       the new status
     * @param adminRemarks the admin remarks
     * @return true if successful, false otherwise
     */
    public boolean updateComplaintStatus(int id, Complaint.ComplaintStatus status, String adminRemarks) {
        String sql = "UPDATE complaints SET status = ?, admin_remarks = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, status.toString());
            pstmt.setString(2, adminRemarks);
            pstmt.setInt(3, id);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Delete complaint by ID
     * 
     * @param id the complaint ID to delete
     * @return true if successful, false otherwise
     */
    public boolean deleteComplaint(int id) {
        String sql = "DELETE FROM complaints WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Get complaint count by status
     * 
     * @return count of complaints by status
     */
    public int getComplaintCountByStatus(Complaint.ComplaintStatus status) {
        String sql = "SELECT COUNT(*) FROM complaints WHERE status = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, status.toString());

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Get total complaint count
     * 
     * @return total number of complaints
     */
    public int getTotalComplaintCount() {
        String sql = "SELECT COUNT(*) FROM complaints";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Map ResultSet to Complaint object
     * 
     * @param rs the ResultSet
     * @return Complaint object
     * @throws SQLException if database error occurs
     */
    private Complaint mapResultSetToComplaint(ResultSet rs) throws SQLException {
        Complaint complaint = new Complaint();
        complaint.setId(rs.getInt("id"));
        complaint.setTitle(rs.getString("title"));
        complaint.setDescription(rs.getString("description"));
        complaint.setStatus(Complaint.ComplaintStatus.valueOf(rs.getString("status")));
        complaint.setPriority(Complaint.ComplaintPriority.valueOf(rs.getString("priority")));
        complaint.setUserId(rs.getInt("user_id"));
        complaint.setAdminRemarks(rs.getString("admin_remarks"));
        complaint.setCreatedAt(rs.getTimestamp("created_at"));
        complaint.setUpdatedAt(rs.getTimestamp("updated_at"));

        // Set user information if available
        if (rs.getMetaData().getColumnCount() > 8) {
            complaint.setUserName(rs.getString("username"));
            complaint.setUserFullName(rs.getString("full_name"));
        }

        return complaint;
    }
}