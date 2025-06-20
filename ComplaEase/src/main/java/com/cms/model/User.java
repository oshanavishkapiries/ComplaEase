package com.cms.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;

/**
 * User model class representing users in the Complaint Management System
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private int id;
    private String username;
    private String password;
    private String fullName;
    private String email;
    private UserRole role;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    // Enum for user roles
    public enum UserRole {
        EMPLOYEE, ADMIN
    }

    // Default constructor
    public User() {
    }

    // Constructor with all fields
    public User(int id, String username, String password, String fullName, String email, UserRole role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.role = role;
    }

    // Constructor without id (for creating new users)
    public User(String username, String password, String fullName, String email, UserRole role) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.role = role;
    }

    // Helper methods
    public boolean isAdmin() {
        return UserRole.ADMIN.equals(this.role);
    }

    public boolean isEmployee() {
        return UserRole.EMPLOYEE.equals(this.role);
    }
}