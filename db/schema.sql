-- Drop existing tables if they exist
DROP TABLE IF EXISTS complaints;
DROP TABLE IF EXISTS users;

-- Create users table with improved structure
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL, -- Increased length for hashed passwords
    role ENUM('EMPLOYEE', 'ADMIN') NOT NULL DEFAULT 'EMPLOYEE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_username (username),
    INDEX idx_role (role)
);

-- Create complaints table with improved structure
CREATE TABLE complaints (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    title VARCHAR(100) NOT NULL,
    description TEXT NOT NULL,
    status ENUM('PENDING', 'IN_PROGRESS', 'RESOLVED', 'CLOSED') DEFAULT 'PENDING',
    priority ENUM('LOW', 'MEDIUM', 'HIGH', 'URGENT') DEFAULT 'MEDIUM',
    remarks TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    INDEX idx_user_id (user_id),
    INDEX idx_status (status),
    INDEX idx_priority (priority),
    INDEX idx_created_at (created_at)
);

-- Insert sample users (passwords should be hashed in production)
INSERT INTO users (username, password, role) VALUES
('admin', 'admin123', 'ADMIN'),
('john.doe', 'password123', 'EMPLOYEE'),
('jane.smith', 'password123', 'EMPLOYEE'),
('mike.wilson', 'password123', 'EMPLOYEE'),
('sarah.jones', 'password123', 'EMPLOYEE'),
('david.brown', 'password123', 'EMPLOYEE');

-- Insert sample complaints
INSERT INTO complaints (user_id, title, description, status, priority, remarks) VALUES
(2, 'Network Connectivity Issues', 'Unable to connect to the office network. Getting timeout errors when trying to access shared drives.', 'PENDING', 'HIGH', NULL),
(3, 'Software Installation Request', 'Need Adobe Creative Suite installed on my workstation for design work.', 'IN_PROGRESS', 'MEDIUM', 'Software license approved. Installation scheduled for tomorrow.'),
(4, 'Printer Not Working', 'The office printer on floor 2 is showing offline status and not responding to print jobs.', 'RESOLVED', 'MEDIUM', 'Printer was restarted and is now working properly.'),
(5, 'Email Configuration', 'Outlook is not syncing properly with the company email server. Getting authentication errors.', 'PENDING', 'HIGH', NULL),
(6, 'Hardware Replacement', 'My laptop keyboard has several keys that are not responding. Need replacement or repair.', 'IN_PROGRESS', 'LOW', 'Replacement keyboard ordered. Will be available next week.'),
(2, 'VPN Access Issues', 'Cannot connect to company VPN from home. Getting connection timeout errors.', 'CLOSED', 'HIGH', 'VPN configuration updated. Issue resolved.'),
(3, 'Meeting Room Booking System', 'The meeting room booking system is not showing available time slots correctly.', 'PENDING', 'MEDIUM', NULL),
(4, 'Software License Renewal', 'Need to renew licenses for Microsoft Office suite. Current licenses expire next month.', 'RESOLVED', 'LOW', 'Licenses renewed successfully. All users have access.'),
(5, 'Internet Speed Issues', 'Internet connection is very slow in the marketing department. Affecting work productivity.', 'IN_PROGRESS', 'HIGH', 'Investigating network congestion. Temporary bandwidth increase applied.'),
(6, 'Backup System Failure', 'Automated backup system failed to run last night. Need immediate attention.', 'PENDING', 'URGENT', NULL);

-- Create indexes for better performance
CREATE INDEX idx_complaints_user_status ON complaints(user_id, status);
CREATE INDEX idx_complaints_created_status ON complaints(created_at, status);