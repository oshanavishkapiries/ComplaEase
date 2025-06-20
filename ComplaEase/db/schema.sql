-- Complaint Management System Database Schema
-- Created for IJSE GDSE Advanced API Development Assignment

-- Create database
CREATE DATABASE IF NOT EXISTS cms_db;
USE cms_db;

-- Drop tables if they exist (for clean setup)
DROP TABLE IF EXISTS complaints;
DROP TABLE IF EXISTS users;

-- Users table
CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    role ENUM('EMPLOYEE', 'ADMIN') NOT NULL DEFAULT 'EMPLOYEE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Complaints table
CREATE TABLE complaints (
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(200) NOT NULL,
    description TEXT NOT NULL,
    status ENUM('PENDING', 'IN_PROGRESS', 'RESOLVED') NOT NULL DEFAULT 'PENDING',
    priority ENUM('LOW', 'MEDIUM', 'HIGH') NOT NULL DEFAULT 'MEDIUM',
    user_id INT NOT NULL,
    admin_remarks TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Insert sample data for testing

-- Sample Admin user (password: admin123)
INSERT INTO users (username, password, full_name, email, role) VALUES
('admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDa', 'System Administrator', 'admin@municipal.gov', 'ADMIN');

-- Sample Employee users (password: employee123)
INSERT INTO users (username, password, full_name, email, role) VALUES
('john.doe', '$2a$10$8K1p/a0dL1LXMIgoEDFrwOfgqwAGmQvJvVrQYVHrQYVHrQYVHrQYV', 'John Doe', 'john.doe@municipal.gov', 'EMPLOYEE'),
('jane.smith', '$2a$10$8K1p/a0dL1LXMIgoEDFrwOfgqwAGmQvJvVrQYVHrQYVHrQYVHrQYV', 'Jane Smith', 'jane.smith@municipal.gov', 'EMPLOYEE'),
('mike.johnson', '$2a$10$8K1p/a0dL1LXMIgoEDFrwOfgqwAGmQvJvVrQYVHrQYVHrQYVHrQYV', 'Mike Johnson', 'mike.johnson@municipal.gov', 'EMPLOYEE');

-- Sample complaints
INSERT INTO complaints (title, description, status, priority, user_id) VALUES
('Network Connectivity Issues', 'Unable to connect to the office network from my workstation. Shows "Limited connectivity" error.', 'PENDING', 'HIGH', 2),
('Software Installation Request', 'Need Adobe Acrobat Pro installed on my computer for document processing.', 'IN_PROGRESS', 'MEDIUM', 3),
('Printer Not Working', 'The office printer is showing "Paper Jam" error but there is no paper stuck.', 'RESOLVED', 'LOW', 2),
('Email Configuration', 'Outlook is not syncing properly with the exchange server.', 'PENDING', 'MEDIUM', 4),
('System Performance', 'Computer is running very slow, takes 5 minutes to start up.', 'IN_PROGRESS', 'HIGH', 3);

-- Add some admin remarks to resolved complaints
UPDATE complaints SET admin_remarks = 'Printer maintenance completed. Issue resolved.' WHERE id = 3;

-- Create indexes for better performance
CREATE INDEX idx_complaints_user_id ON complaints(user_id);
CREATE INDEX idx_complaints_status ON complaints(status);
CREATE INDEX idx_complaints_created_at ON complaints(created_at);
CREATE INDEX idx_users_username ON users(username);
CREATE INDEX idx_users_role ON users(role); 