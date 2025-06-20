<%@ page import="edu.cms.dto.ComplaintDTO" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    List<ComplaintDTO> complaints = (List<ComplaintDTO>) request.getAttribute("complaints");
    String username = (String) session.getAttribute("user");

    String editId = request.getParameter("editId");
    ComplaintDTO editComplaint = null;
    if (editId != null && complaints != null) {
        for (ComplaintDTO c : complaints) {
            if (String.valueOf(c.getId()).equals(editId)) {
                editComplaint = c;
                break;
            }
        }
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Employee Dashboard - Complaint Management System</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
        }

        .dashboard-container {
            display: flex;
            min-height: 100vh;
        }

        /* Sidebar */
        .sidebar {
            width: 280px;
            background: linear-gradient(180deg, #4CAF50 0%, #45a049 100%);
            color: white;
            padding: 30px 0;
            position: fixed;
            height: 100vh;
            overflow-y: auto;
            box-shadow: 2px 0 10px rgba(0, 0, 0, 0.1);
        }

        .sidebar-header {
            padding: 0 30px 30px;
            border-bottom: 1px solid rgba(255, 255, 255, 0.1);
            margin-bottom: 30px;
        }

        .sidebar-logo {
            display: flex;
            align-items: center;
            font-size: 1.5rem;
            font-weight: 700;
            margin-bottom: 10px;
        }

        .sidebar-logo i {
            margin-right: 10px;
            font-size: 2rem;
        }

        .user-info {
            font-size: 0.9rem;
            opacity: 0.9;
        }

        .sidebar-nav {
            padding: 0 30px;
        }

        .nav-item {
            margin-bottom: 10px;
        }

        .nav-link {
            display: flex;
            align-items: center;
            padding: 12px 15px;
            color: white;
            text-decoration: none;
            border-radius: 10px;
            transition: all 0.3s ease;
            font-weight: 500;
        }

        .nav-link:hover {
            background: rgba(255, 255, 255, 0.1);
            transform: translateX(5px);
        }

        .nav-link.active {
            background: rgba(255, 255, 255, 0.2);
        }

        .nav-link i {
            margin-right: 12px;
            width: 20px;
            text-align: center;
        }

        .logout-btn {
            margin-top: 30px;
            background: rgba(255, 255, 255, 0.1);
            border: 1px solid rgba(255, 255, 255, 0.2);
        }

        .logout-btn:hover {
            background: rgba(255, 255, 255, 0.2);
        }

        /* Main Content */
        .main-content {
            flex: 1;
            margin-left: 280px;
            padding: 30px;
            background: #f8f9fa;
        }

        .page-header {
            background: white;
            padding: 30px;
            border-radius: 15px;
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.05);
            margin-bottom: 30px;
        }

        .page-title {
            font-size: 2rem;
            color: #333;
            margin-bottom: 10px;
            font-weight: 600;
        }

        .page-subtitle {
            color: #666;
            font-size: 1rem;
        }

        .stats-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
            gap: 20px;
            margin-bottom: 30px;
        }

        .stat-card {
            background: white;
            padding: 25px;
            border-radius: 15px;
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.05);
            text-align: center;
            border-left: 4px solid #4CAF50;
        }

        .stat-icon {
            font-size: 2.5rem;
            color: #4CAF50;
            margin-bottom: 15px;
        }

        .stat-number {
            font-size: 2rem;
            font-weight: 700;
            color: #333;
            margin-bottom: 5px;
        }

        .stat-label {
            color: #666;
            font-size: 0.9rem;
        }

        /* Form Section */
        .form-section {
            background: white;
            padding: 30px;
            border-radius: 15px;
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.05);
            margin-bottom: 30px;
        }

        .section-title {
            font-size: 1.5rem;
            color: #333;
            margin-bottom: 20px;
            font-weight: 600;
            display: flex;
            align-items: center;
        }

        .section-title i {
            margin-right: 10px;
            color: #4CAF50;
        }

        .form-group {
            margin-bottom: 20px;
        }

        .form-group label {
            display: block;
            margin-bottom: 8px;
            color: #333;
            font-weight: 500;
            font-size: 0.9rem;
        }

        .form-control {
            width: 100%;
            padding: 12px 15px;
            border: 2px solid #e0e0e0;
            border-radius: 10px;
            font-size: 1rem;
            transition: all 0.3s ease;
            background: white;
        }

        .form-control:focus {
            outline: none;
            border-color: #4CAF50;
            box-shadow: 0 0 0 3px rgba(76, 175, 80, 0.1);
        }

        .form-control::placeholder {
            color: #999;
        }

        textarea.form-control {
            resize: vertical;
            min-height: 100px;
        }

        .form-buttons {
            display: flex;
            gap: 15px;
            align-items: center;
        }

        .btn {
            padding: 12px 25px;
            border: none;
            border-radius: 10px;
            font-size: 1rem;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.3s ease;
            text-decoration: none;
            display: inline-flex;
            align-items: center;
        }

        .btn i {
            margin-right: 8px;
        }

        .btn-primary {
            background: #4CAF50;
            color: white;
        }

        .btn-primary:hover {
            background: #45a049;
            transform: translateY(-2px);
            box-shadow: 0 8px 20px rgba(76, 175, 80, 0.3);
        }

        .btn-secondary {
            background: #6c757d;
            color: white;
        }

        .btn-secondary:hover {
            background: #5a6268;
            transform: translateY(-2px);
        }

        .btn-danger {
            background: #dc3545;
            color: white;
        }

        .btn-danger:hover {
            background: #c82333;
            transform: translateY(-2px);
        }

        /* Table Section */
        .table-section {
            background: white;
            padding: 30px;
            border-radius: 15px;
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.05);
        }

        .table-container {
            overflow-x: auto;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }

        th, td {
            padding: 15px;
            text-align: left;
            border-bottom: 1px solid #e0e0e0;
        }

        th {
            background: #f8f9fa;
            font-weight: 600;
            color: #333;
            font-size: 0.9rem;
        }

        td {
            color: #666;
        }

        .status-badge {
            padding: 5px 12px;
            border-radius: 20px;
            font-size: 0.8rem;
            font-weight: 600;
            text-transform: uppercase;
        }

        .status-pending {
            background: #fff3cd;
            color: #856404;
        }

        .status-resolved {
            background: #d4edda;
            color: #155724;
        }

        .actions {
            display: flex;
            gap: 10px;
            align-items: center;
        }

        .action-link {
            color: #4CAF50;
            text-decoration: none;
            font-weight: 500;
            transition: color 0.3s ease;
        }

        .action-link:hover {
            color: #45a049;
        }

        .action-link.disabled {
            color: #999;
            cursor: not-allowed;
        }

        .empty-state {
            text-align: center;
            padding: 60px 20px;
            color: #666;
        }

        .empty-state i {
            font-size: 4rem;
            color: #ddd;
            margin-bottom: 20px;
        }

        .empty-state h3 {
            font-size: 1.5rem;
            margin-bottom: 10px;
            color: #333;
        }

        @media (max-width: 768px) {
            .sidebar {
                transform: translateX(-100%);
                transition: transform 0.3s ease;
            }

            .sidebar.open {
                transform: translateX(0);
            }

            .main-content {
                margin-left: 0;
                padding: 20px;
            }

            .stats-grid {
                grid-template-columns: 1fr;
            }

            .form-buttons {
                flex-direction: column;
            }

            .btn {
                width: 100%;
                justify-content: center;
            }
        }
    </style>
</head>
<body>
    <div class="dashboard-container">
        <!-- Sidebar -->
        <div class="sidebar">
            <div class="sidebar-header">
                <div class="sidebar-logo">
                    <i class="fas fa-clipboard-check"></i>
                    CMS
                </div>
                <div class="user-info">
                    Welcome, <%= username != null ? username : "Employee" %>
                </div>
            </div>

            <nav class="sidebar-nav">
                <div class="nav-item">
                    <a href="#" class="nav-link active">
                        <i class="fas fa-tachometer-alt"></i>
                        Dashboard
                    </a>
                </div>
                <div class="nav-item">
                    <a href="#" class="nav-link">
                        <i class="fas fa-file-alt"></i>
                        My Complaints
                    </a>
                </div>
                <div class="nav-item">
                    <a href="#" class="nav-link">
                        <i class="fas fa-chart-bar"></i>
                        Reports
                    </a>
                </div>
                <div class="nav-item">
                    <a href="#" class="nav-link">
                        <i class="fas fa-cog"></i>
                        Settings
                    </a>
                </div>
                <div class="nav-item">
                    <a href="<%= request.getContextPath() %>/logout" class="nav-link logout-btn">
                        <i class="fas fa-sign-out-alt"></i>
                        Logout
                    </a>
                </div>
            </nav>
        </div>

        <!-- Main Content -->
        <div class="main-content">
            <!-- Page Header -->
            <div class="page-header">
                <h1 class="page-title">Employee Dashboard</h1>
                <p class="page-subtitle">Manage your complaints and track their progress</p>
            </div>

            <!-- Statistics -->
            <div class="stats-grid">
                <div class="stat-card">
                    <div class="stat-icon">
                        <i class="fas fa-file-alt"></i>
                    </div>
                    <div class="stat-number"><%= complaints != null ? complaints.size() : 0 %></div>
                    <div class="stat-label">Total Complaints</div>
                </div>
                <div class="stat-card">
                    <div class="stat-icon">
                        <i class="fas fa-clock"></i>
                    </div>
                    <div class="stat-number">
                        <%= complaints != null ? complaints.stream().filter(c -> "PENDING".equalsIgnoreCase(c.getStatus())).count() : 0 %>
                    </div>
                    <div class="stat-label">Pending</div>
                </div>
                <div class="stat-card">
                    <div class="stat-icon">
                        <i class="fas fa-check-circle"></i>
                    </div>
                    <div class="stat-number">
                        <%= complaints != null ? complaints.stream().filter(c -> "RESOLVED".equalsIgnoreCase(c.getStatus())).count() : 0 %>
                    </div>
                    <div class="stat-label">Resolved</div>
                </div>
            </div>

            <!-- Complaint Form -->
            <div class="form-section">
                <h2 class="section-title">
                    <i class="fas fa-plus-circle"></i>
                    <%= editComplaint != null ? "Update Complaint" : "Submit a New Complaint" %>
                </h2>

                <form id="complaintForm" action="<%= request.getContextPath() %><%= editComplaint != null ? "/updateComplaint" : "/complaint" %>" method="post" onsubmit="return validateComplaintForm()">
                    <%
                        if (editComplaint != null) {
                    %>
                        <input type="hidden" name="id" value="<%= editComplaint.getId() %>" />
                    <%
                        }
                    %>

                    <div class="form-group">
                        <label for="title">Complaint Title</label>
                        <input type="text" id="title" name="title" class="form-control" 
                               placeholder="Enter complaint title"
                               value="<%= editComplaint != null ? editComplaint.getTitle() : "" %>" 
                               required maxlength="100" />
                    </div>

                    <div class="form-group">
                        <label for="description">Description</label>
                        <textarea id="description" name="description" class="form-control" 
                                  placeholder="Describe your complaint in detail" 
                                  rows="4" required maxlength="500"><%= editComplaint != null ? editComplaint.getDescription() : "" %></textarea>
                    </div>

                    <div class="form-buttons">
                        <button type="submit" class="btn btn-primary">
                            <i class="fas fa-<%= editComplaint != null ? "save" : "paper-plane" %>"></i>
                            <%= editComplaint != null ? "Update Complaint" : "Submit Complaint" %>
                        </button>

                        <%
                            if (editComplaint != null) {
                        %>
                            <a href="<%= request.getContextPath() %>/complaint" class="btn btn-secondary">
                                <i class="fas fa-times"></i> Cancel
                            </a>
                        <%
                            }
                        %>
                    </div>
                </form>
            </div>

            <!-- Complaints Table -->
            <div class="table-section">
                <h2 class="section-title">
                    <i class="fas fa-list"></i>
                    Your Complaints
                </h2>

                <div class="table-container">
                    <%
                        if (complaints != null && !complaints.isEmpty()) {
                    %>
                        <table>
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Title</th>
                                    <th>Description</th>
                                    <th>Status</th>
                                    <th>Remarks</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                <%
                                    for (ComplaintDTO c : complaints) {
                                %>
                                    <tr>
                                        <td><%= c.getId() %></td>
                                        <td><%= c.getTitle() %></td>
                                        <td><%= c.getDescription() %></td>
                                        <td>
                                            <span class="status-badge <%= "PENDING".equalsIgnoreCase(c.getStatus()) ? "status-pending" : "status-resolved" %>">
                                                <%= c.getStatus() %>
                                            </span>
                                        </td>
                                        <td><%= c.getRemarks() != null ? c.getRemarks() : "-" %></td>
                                        <td class="actions">
                                            <% if ("RESOLVED".equalsIgnoreCase(c.getStatus())) { %>
                                                <span class="action-link disabled">Edit</span>
                                                <button class="btn btn-danger" disabled style="opacity: 0.5;">Delete</button>
                                            <% } else { %>
                                                <a class="action-link" href="<%= request.getContextPath() %>/complaint?editId=<%= c.getId() %>">
                                                    <i class="fas fa-edit"></i> Edit
                                                </a>
                                                <form action="<%= request.getContextPath() %>/complaintAction" method="post" 
                                                      onsubmit="return confirm('Are you sure you want to delete this complaint?');" 
                                                      style="display:inline;">
                                                    <input type="hidden" name="id" value="<%= c.getId() %>" />
                                                    <button type="submit" class="btn btn-danger">
                                                        <i class="fas fa-trash"></i> Delete
                                                    </button>
                                                </form>
                                            <% } %>
                                        </td>
                                    </tr>
                                <%
                                    }
                                %>
                            </tbody>
                        </table>
                    <%
                        } else {
                    %>
                        <div class="empty-state">
                            <i class="fas fa-inbox"></i>
                            <h3>No Complaints Yet</h3>
                            <p>You haven't submitted any complaints yet. Start by submitting your first complaint above.</p>
                        </div>
                    <%
                        }
                    %>
                </div>
            </div>
        </div>
    </div>

    <script>
        function validateComplaintForm() {
            const title = document.getElementById('title').value.trim();
            const description = document.getElementById('description').value.trim();

            if (!title) {
                alert("Please enter a complaint title.");
                return false;
            }
            if (title.length > 100) {
                alert("Complaint title must be less than or equal to 100 characters.");
                return false;
            }

            if (!description) {
                alert("Please enter a complaint description.");
                return false;
            }
            if (description.length > 500) {
                alert("Complaint description must be less than or equal to 500 characters.");
                return false;
            }

            return true;
        }
    </script>
</body>
</html>
