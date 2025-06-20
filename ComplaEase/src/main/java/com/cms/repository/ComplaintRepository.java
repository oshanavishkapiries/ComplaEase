package com.cms.repository;

import com.cms.model.Complaint;
import com.cms.util.CrudUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ComplaintRepository {
    public Complaint findById(int id) {
        String sql = "SELECT * FROM complaints WHERE id = ?";
        try {
            return CrudUtils.executeQuery(sql, rs -> {
                try {
                    if (rs.next())
                        return map(rs);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return null;
            }, id);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Complaint> findAll() {
        String sql = "SELECT * FROM complaints ORDER BY created_at DESC";
        try {
            return CrudUtils.executeQuery(sql, rs -> {
                List<Complaint> list = new ArrayList<>();
                try {
                    while (rs.next())
                        list.add(map(rs));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return list;
            });
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<Complaint> findByUserId(int userId) {
        String sql = "SELECT * FROM complaints WHERE user_id = ? ORDER BY created_at DESC";
        try {
            return CrudUtils.executeQuery(sql, rs -> {
                List<Complaint> list = new ArrayList<>();
                try {
                    while (rs.next())
                        list.add(map(rs));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return list;
            }, userId);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public boolean save(Complaint c) {
        String sql = "INSERT INTO complaints (title, description, status, priority, user_id) VALUES (?, ?, ?, ?, ?)";
        try {
            return CrudUtils.executeUpdate(sql, c.getTitle(), c.getDescription(), c.getStatus().toString(),
                    c.getPriority().toString(), c.getUserId()) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(Complaint c) {
        String sql = "UPDATE complaints SET title = ?, description = ?, status = ?, priority = ?, admin_remarks = ? WHERE id = ?";
        try {
            return CrudUtils.executeUpdate(sql, c.getTitle(), c.getDescription(), c.getStatus().toString(),
                    c.getPriority().toString(), c.getAdminRemarks(), c.getId()) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM complaints WHERE id = ?";
        try {
            return CrudUtils.executeUpdate(sql, id) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Complaint map(ResultSet rs) throws SQLException {
        Complaint c = new Complaint();
        c.setId(rs.getInt("id"));
        c.setTitle(rs.getString("title"));
        c.setDescription(rs.getString("description"));
        c.setStatus(Complaint.ComplaintStatus.valueOf(rs.getString("status")));
        c.setPriority(Complaint.ComplaintPriority.valueOf(rs.getString("priority")));
        c.setUserId(rs.getInt("user_id"));
        c.setAdminRemarks(rs.getString("admin_remarks"));
        c.setCreatedAt(rs.getTimestamp("created_at"));
        c.setUpdatedAt(rs.getTimestamp("updated_at"));
        return c;
    }
}