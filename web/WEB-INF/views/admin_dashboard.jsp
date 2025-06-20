<%@ page import="edu.cms.dto.ComplaintDTO" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    List<ComplaintDTO> complaints = (List<ComplaintDTO>) request.getAttribute("complaints");
    String username = (String) session.getAttribute("user");

    String updateId = request.getParameter("updateId");
    ComplaintDTO updateComplaint = null;
    if (updateId != null && complaints != null) {
        for (ComplaintDTO c : complaints) {
            if (String.valueOf(c.getId()).equals(updateId)) {
                updateComplaint = c;
                break;
            }
        }
    }
%>
<html>
    <head>
        <title>Admin Dashboard</title>
        <link href="${pageContext.request.contextPath}/css/admin_dashboard.css" rel="stylesheet">
    </head>

    <body>
        <div class="container">
            <div class="admin-header">
                <h1>
                    Admin Dashboard - <%= username != null ? username : "Administrator" %>
                    <a class="logout-btn" href="<%= request.getContextPath() %>/logout">Logout</a>
                </h1>
                <p>Complaint Management System</p>
            </div>

            <%
                if (request.getAttribute("message") != null) {
            %>
                    <div class="message success">
                        <%= request.getAttribute("message") %>
                    </div>
            <%
                } else if (request.getAttribute("error") != null) {
            %>
                    <div class="message error">
                        <%= request.getAttribute("error") %>
                    </div>
            <%
                }
            %>

            <%
                if (updateComplaint != null) {
            %>
                    <div class="update-form-container">
                        <h2>Update Complaint Status & Remarks</h2>
                        <form id="adminUpdateForm" action="<%= request.getContextPath() %>/admin" method="post"
                              onsubmit="return validateAdminUpdateForm()">

                            <input type="hidden" name="action" value="update"/>
                            <input type="hidden" name="id" value="<%= updateComplaint.getId() %>"/>

                            <div style="background-color: #f5f5f5; padding: 15px; border-radius: 5px; margin-bottom: 15px;">
                                <p><strong>Complaint ID:</strong> <%= updateComplaint.getId() %></p>
                                <p><strong>User ID:</strong> <%= updateComplaint.getUserId() %></p>
                                <p><strong>Title:</strong> <%= updateComplaint.getTitle() %></p>
                                <p><strong>Description:</strong> <%= updateComplaint.getDescription() %></p>
                                <p><strong>Current Status:</strong>
                                    <span class="status-badge <%= "PENDING".equalsIgnoreCase(updateComplaint.getStatus()) ? "status-pending" :
                                                                 "RESOLVED".equalsIgnoreCase(updateComplaint.getStatus()) ? "status-resolved" : "" %>">
                                        <%= updateComplaint.getStatus() %>
                                    </span>
                                </p>
                            </div>

                            <label for="status"><strong>Update Status:</strong></label>
                            <select name="status" id="status" required>
                                <option value="">Select Status</option>
                                <option value="PENDING" <%= "PENDING".equalsIgnoreCase(updateComplaint.getStatus()) ? "selected" : "" %>>
                                    Pending
                                </option>
                                <option value="RESOLVED" <%= "RESOLVED".equalsIgnoreCase(updateComplaint.getStatus()) ? "selected" : "" %>>
                                    Resolved
                                </option>
                            </select>

                            <label for="remarks"><strong>Admin Remarks:</strong></label>
                            <textarea name="remarks" id="remarks" placeholder="Add your remarks here..."
                                      rows="4"><%= updateComplaint.getRemarks() != null ? updateComplaint.getRemarks() : "" %></textarea>

                            <div class="form-buttons" style="margin-top: 15px;">
                                <input type="submit" value="Update Complaint" class="update-btn"/>
                                <a href="<%= request.getContextPath() %>/complaint" class="cancel-btn">Cancel</a>
                            </div>
                        </form>
                    </div>
            <%
                }
            %>

            <h2>All System Complaints</h2>

            <table>
                <tr>
                    <th>ID</th>
                    <th>User ID</th>
                    <th>Title</th>
                    <th>Description</th>
                    <th>Status</th>
                    <th>Remarks</th>
                    <th>Actions</th>
                </tr>

                <%
                    if (complaints != null && !complaints.isEmpty()) {
                        for (ComplaintDTO c : complaints) {
                            String statusClass = "";
                            if ("PENDING".equalsIgnoreCase(c.getStatus())) {
                                statusClass = "status-pending";
                            } else if ("RESOLVED".equalsIgnoreCase(c.getStatus())) {
                                statusClass = "status-resolved";
                            }
                %>

                <tr>
                    <td>
                        <%= c.getId() %>
                    </td>

                    <td class="employee-column">
                        <%= c.getUserId() %>
                    </td>

                    <td>
                        <%= c.getTitle() %>
                    </td>

                    <td>
                        <%= c.getDescription().length() > 50 ? c.getDescription().substring(0, 50) + "..." : c.getDescription() %>
                    </td>

                    <td>
                        <span class="status-badge <%= statusClass %>"><%= c.getStatus() %></span>
                    </td>

                    <td>
                        <%= c.getRemarks() != null ? (c.getRemarks().length() > 30 ? c.getRemarks().substring(0, 30) + "..." : c.getRemarks()) : "-" %>
                    </td>

                    <td class="actions">
                        <a class="update-link"
                           href="<%= request.getContextPath() %>/complaint?updateId=<%= c.getId() %>">Update</a>

                        <form action="<%= request.getContextPath() %>/complaintAction" method="post"
                              onsubmit="return confirm('Are you sure you want to delete this complaint?');"
                              style="display:inline;">
                            <input type="hidden" name="action" value="delete"/>
                            <input type="hidden" name="id" value="<%= c.getId() %>"/>
                            <input type="submit" class="delete-link" value="Delete"/>
                        </form>

                    </td>

                </tr>

                <%
                    }
                } else {
                %>
                <tr>
                    <td colspan="7">No complaints found in the system.</td>
                </tr>
                <%
                    }
                %>
            </table>
        </div>

    <script>
        function validateAdminUpdateForm() {
            const status = document.getElementById('status').value.trim();
            const remarks = document.getElementById('remarks').value.trim();

            if (!status) {
                alert("Please select a status.");
                return false;
            }

            if (remarks.length > 500) {
                alert("Remarks cannot exceed 500 characters.");
                return false;
            }
            return true;
        }
    </script>

    </body>
</html>
