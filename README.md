# Web-Based Complaint Management System

![Project Banner](assets/poster.png)

---

## 🚀 Overview

This project is a **Web-Based Complaint Management System** developed as an individual assignment for the **Advanced API Development** module of the **IJSE - Graduate Diploma in Software Engineering (GDSE)** program.

The system is a full-stack application built using JSP, Servlets, and MySQL, following the MVC architecture. It allows users to manage complaints based on their roles (Employee or Admin), providing a streamlined process for complaint submission, tracking, and resolution.

### 📅 Project Duration

- **Release Date:** 11th June 2025
- **Deadline:** 17th June 2025 (11:59 PM)

---

## 🎯 Learning Objectives

- **Independent Application of JSP:** Demonstrate the ability to independently apply JSP technologies in building a full-stack web application.
- **HTTP Form Processing:** Design and implement robust HTTP form processing using GET and POST methods for data submission and retrieval.
- **MVC Architecture:** Apply the Model-View-Controller (MVC) architectural pattern to structure the application for modularity and maintainability.
- **Version Control & Documentation:** Maintain a clean and well-documented codebase using Git and provide comprehensive project documentation.
- **Technical Demonstration:** Deliver a clear and concise technical demonstration of the project, explaining its architecture and core functionalities.

---

## ⚙️ System Requirements

### Functional Modules

#### 1. Authentication

- **User Login:** Secure user login with username and password.
- **Session Management:** Maintain user sessions to handle authentication state.
- **Role-Based Access Control:**
  - **Employee:** Can submit and manage their own complaints.
  - **Admin:** Has full access to manage all complaints and users.

#### 2. Complaint Management

**Employee:**

- **Submit Complaint:** Register a new complaint with a title and description.
- **View Complaints:** View a list of all complaints they have submitted.
- **Edit Complaint:** Modify the details of their own unresolved complaints.
- **Delete Complaint:** Remove their own unresolved complaints.

**Admin:**

- **View All Complaints:** Access a comprehensive list of all complaints in the system.
- **Update Complaint:** Modify the status (e.g., "Pending", "Resolved") and add administrative remarks to any complaint.
- **Delete Any Complaint:** Remove any complaint from the system.

---

## 🛠️ Technical Stack

- **Frontend:** JSP, HTML, CSS, JavaScript (for client-side validation only)
- **Backend:** Jakarta EE (Servlets)
- **Database:** MySQL
- **Database Connection Pooling:** Apache Commons DBCP
- **Web Server:** Apache Tomcat
- **Build Tool:** Apache Maven

### Constraints

- **No AJAX:** All server interactions are synchronous, using standard JSP form submissions (GET/POST).

---

## 🏛️ Architecture

The application is built using the **Model-View-Controller (MVC)** design pattern to ensure a clear separation of concerns:

- **Model:** Represents the application's data and business logic.
  - **JavaBeans (DTOs):** `UserDTO`, `ComplaintDTO` for data transfer.
  - **DAO (Data Access Objects):** Handles all database operations, separating business logic from data persistence.
- **View:** Responsible for the user interface.
  - **JSP (JavaServer Pages):** Renders the UI and displays data to the user.
- **Controller:** Manages user input and application flow.
  - **Servlets:** Handle incoming HTTP requests, process user actions, and dispatch to the appropriate views.

### HTTP Method Usage

- **POST:** Used for all state-changing actions (Create, Update, Delete), such as submitting a complaint, updating its status, or deleting it.
- **GET:** Used for all read-only actions, such as viewing a list of complaints or navigating to a form page.

---

## 📦 Setup and Configuration

Follow these steps to set up and run the project locally:

### 1. Prerequisites

- **Java (JDK):** Version 21 or higher
- **Apache Maven:** Version 3.8 or higher
- **Apache Tomcat:** Version 11.0 or higher
- **MySQL Server:** Version 8.0 or higher

### 2. Database Setup

- Create a new database in MySQL named `cms`.
- Execute the SQL script located at `/db/schema.sql` to create the necessary tables (`users`, `complaints`).

### 3. Configuration

- The database connection details are hardcoded in the `DBCPDataSourceFactory.java` file.
  ```java
  // src/main/java/edu/cms/util/DBCPDataSourceFactory.java
  ds.setUrl("jdbc:mysql://root:1234@95.111.248.142:3306/cms");
  ds.setUsername("root");
  ds.setPassword("1234");
  ```
- **Update these values** to match your local MySQL server configuration if needed.

### 4. Build the Project

- Open a terminal in the project's root directory.
- Run the following Maven command to compile the code and package it into a WAR file:
  ```sh
  mvn clean install
  ```
- A `complaint-management-system.war` file will be generated in the `/target` directory.

### 5. Deployment

- Copy the generated `.war` file to the `webapps` directory of your Apache Tomcat installation.
- Start the Apache Tomcat server.
- The application will be accessible at `http://localhost:8080/complaint-management-system/`.

---

## 📹 Video Demo

A 5-minute video demonstration of the project is required for submission. The video should cover:

- A brief overview of the project.
- A walkthrough of the core features for both Employee and Admin roles.
- An explanation of the project's architecture (MVC).
- A demonstration of how JSP forms are used to handle GET and POST requests.

**YouTube Title Format:**
`JakartaEE Project 2025 - IJSE <Batch> <Name> <Branch>`

---

## 📊 Evaluation Criteria

| Area                         | Weight |
| ---------------------------- | ------ |
| Functional Correctness       | 35%    |
| JSP Usage & Compliance       | 25%    |
| Code Structure & Modularity  | 15%    |
| GitHub Usage & Documentation | 10%    |
| Video Explanation Clarity    | 15%    |

---

## ⚖️ Academic Integrity

This is an individual assignment. All work submitted must be your own. Plagiarism or sharing of code is strictly prohibited and will be dealt with according to the institution's academic integrity policies.

---
