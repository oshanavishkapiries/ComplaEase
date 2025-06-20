Complaint Management System Development Guide
This document provides a detailed guide for developing a Web-Based Complaint Management System using JavaServer Pages (JSP) as per the requirements of the IJSE Graduate Diploma in Software Engineering (GDSE) Advanced API Development assignment. The goal is to create a full-stack web application adhering to the specified technologies and constraints, suitable for explaining to another AI agent for implementation.
Project Overview
The project is a prototype for a Complaint Management System (CMS) for the Municipal IT Division. It serves two user roles: Employees and Admins, enabling complaint submission, tracking, and resolution. The system must be developed using Jakarta EE, JSP, MySQL, and Apache Tomcat, with all backend interactions handled through synchronous HTML form submissions (GET/POST). Asynchronous methods like AJAX are prohibited.
Key Details

Release Date: 11th June 2025
Submission Deadline: 17th June 2025, 11:59 PM
Technologies:
Frontend: JSP, HTML, CSS, JavaScript (for validation only)
Backend: Jakarta EE (Servlets), Apache Commons DBCP
Database: MySQL with DBCP connection pooling
Deployment: Apache Tomcat (local environment)


Architecture: Model-View-Controller (MVC) pattern
View: JSP
Controller: Servlets
Model: JavaBeans (POJOs) and DAO classes


Constraints:
Use HTTP POST for state-changing operations
Use HTTP GET for read-only operations
No AJAX, fetch, or XMLHttpRequest



System Requirements
Functional Requirements
Authentication Module

User Login:
Implement a login system with session management using HttpSession.
Users (Employees and Admins) log in with credentials (e.g., username and password).
Validate credentials against the MySQL database.
Store user role and ID in session for access control.


Role-Based Access:
Employee: Can submit, view, edit, or delete their own unresolved complaints.
Admin: Can view all complaints, update status, add remarks, and delete any complaint.



Complaint Management Module

Employee Role:
Submit Complaint: Form to input complaint details (e.g., title, description).
View Complaints: Display a list of the employee’s submitted complaints.
Edit Complaint: Update complaint details if not yet resolved.
Delete Complaint: Remove unresolved complaints.


Admin Role:
View All Complaints: Display a list of all complaints in the system.
Update Status/Remarks: Change complaint status (e.g., Pending, In Progress, Resolved) and add remarks.
Delete Complaint: Remove any complaint from the system.



Technical Requirements

Frontend:
Use JSP to render dynamic HTML pages.
Include HTML and CSS for layout and styling.
Use JavaScript only for client-side form validation (e.g., checking for empty fields).


Backend:
Implement Servlets to handle HTTP GET and POST requests.
Use Apache Commons DBCP for database connection pooling.
Create JavaBeans for data models (e.g., User, Complaint).
Use DAO (Data Access Object) classes for database operations.


Database:
Design a MySQL schema to store users, complaints, and related data.
Include a SQL dump file (schema.sql) for easy database setup.


Deployment:
Deploy the application on a local Apache Tomcat server.



Architectural Constraints

Follow the MVC pattern:
Model: JavaBeans for data (e.g., User.java, Complaint.java) and DAO classes for database access (e.g., UserDAO.java, ComplaintDAO.java).
View: JSP files for rendering UI (e.g., login.jsp, complaintList.jsp).
Controller: Servlets to handle requests and coordinate between Model and View (e.g., LoginServlet.java, ComplaintServlet.java).


Use HTML <form> elements for all interactions:
POST for actions like login, submitting complaints, updating status, or deleting.
GET for retrieving data like complaint lists.


Avoid asynchronous HTTP mechanisms.

Development Plan
Step 1: Project Setup

Set Up Development Environment:
Install Java JDK (compatible with Jakarta EE).
Install Apache Tomcat (e.g., version 10.x for Jakarta EE).
Set up MySQL server and MySQL Workbench for database management.
Use an IDE like IntelliJ IDEA or Eclipse for Java EE development.


Create Project Structure:
Initialize a Maven project for dependency management.
Directory structure:/src
  /main
    /java
      /com
        /cms
          /controller (Servlets)
          /model (JavaBeans)
          /dao (DAO classes)
    /webapp
      /WEB-INF
        /jsp (JSP files)
      /css
      /js
/db
  schema.sql




Configure Dependencies:
Add dependencies in pom.xml:
Jakarta EE (Servlet API)
Apache Commons DBCP
MySQL Connector/J


Example pom.xml:






    4.0.0
    com.cms
    ComplaintManagementSystem
    1.0-SNAPSHOT
    
        
            jakarta.servlet
            jakarta.servlet-api
            6.0.0
            provided
        
        
            jakarta.servlet.jsp
            jakarta.servlet.jsp-api
            3.0.0
            provided
        
        
            org.apache.commons
            commons-dbcp2
            2.9.0
        
        
            mysql
            mysql-connector-java
            8.0.33
        
    
    
        
            
                indx, artifact_id, title, content_type, content
                org.apache.maven.plugins
                maven-war-plugin
                3.3.2
            
        
    
