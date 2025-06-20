# Complaint Management System

## Project Overview
The Complaint Management System is a web-based application designed to streamline the process of lodging, tracking, and resolving complaints within an organization. It supports user registration, complaint submission, administrative review, and status tracking, providing a transparent and efficient workflow for both users and administrators.

## Setup and Configuration Guide

### Prerequisites
- Java Development Kit (JDK) 8 or higher
- Apache Tomcat or compatible servlet container
- Maven
- MySQL or compatible database

### Steps
1. **Clone the repository:**
   ```bash
   git clone <repository-url>
   ```
2. **Database Setup:**
    - Create a database (e.g., `complaint_db`).
    - Run the SQL script in `db/schema.sql` to set up the required tables.
3. **Configure Database Connection:**
    - Update the database connection settings in `src/main/java/edu/vidura/util/DBCPDataSourceFactory.java`.
4. **Build the project:**
   ```bash
   mvn clean package
   ```
5. **Deploy:**
    - Deploy the generated WAR file to your servlet container (e.g., Tomcat's `webapps` directory).
6. **Access the Application:**
    - Open your browser and navigate to `http://localhost:8080/complaint_management_system_Web_exploded` (or your configured context path).

## Source Code Layout

```
complaint-management-system/
│
├── db/
│   └── schema.sql                # Database schema
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── edu/vidura/
│   │   │       ├── controller/   # Servlets (SignUpServlet, LoginServlet, etc.)
│   │   │       ├── model/
│   │   │       │   ├── bean/     # Data beans (User, Complaint)
│   │   │       │   └── dao/      # Data access objects (UserDAO, ComplaintDAO)
│   │   │       └── util/         # Utilities (DBCPDataSourceFactory, CorsFilter)
│   │   └── resources/            
│   └── test/                     # Test sources
│
├── web/
│   ├── css/                      # Stylesheets
│   ├── index.jsp                 # Main entry JSP
│   └── WEB-INF/
│       ├── web.xml               # Servlet configuration
│       └── views/                # JSP views (dashboard, login, signup, admin_dashboard, etc.)
│
├── pom.xml                       # Maven build file
└── README.md                     # Project documentation
```