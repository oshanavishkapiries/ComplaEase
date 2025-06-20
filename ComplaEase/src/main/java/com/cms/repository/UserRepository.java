package com.cms.repository;

import com.cms.model.User;
import com.cms.util.CrudUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    public User findById(int id) {
        String sql = "SELECT * FROM users WHERE id = ?";
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

    public User findByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        try {
            return CrudUtils.executeQuery(sql, rs -> {
                try {
                    if (rs.next())
                        return map(rs);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return null;
            }, username);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<User> findAll() {
        String sql = "SELECT * FROM users ORDER BY created_at DESC";
        try {
            return CrudUtils.executeQuery(sql, rs -> {
                List<User> list = new ArrayList<>();
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

    public boolean save(User user) {
        String sql = "INSERT INTO users (username, password, full_name, email, role) VALUES (?, ?, ?, ?, ?)";
        try {
            return CrudUtils.executeUpdate(sql, user.getUsername(), user.getPassword(), user.getFullName(),
                    user.getEmail(), user.getRole().toString()) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(User user) {
        String sql = "UPDATE users SET username = ?, password = ?, full_name = ?, email = ?, role = ? WHERE id = ?";
        try {
            return CrudUtils.executeUpdate(sql, user.getUsername(), user.getPassword(), user.getFullName(),
                    user.getEmail(), user.getRole().toString(), user.getId()) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM users WHERE id = ?";
        try {
            return CrudUtils.executeUpdate(sql, id) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private User map(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        user.setFullName(rs.getString("full_name"));
        user.setEmail(rs.getString("email"));
        user.setRole(User.UserRole.valueOf(rs.getString("role")));
        user.setCreatedAt(rs.getTimestamp("created_at"));
        user.setUpdatedAt(rs.getTimestamp("updated_at"));
        return user;
    }
}