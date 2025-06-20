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
<html>
    <head>
        <title>Employee Dashboard</title>
        <link href="${pageContext.request.contextPath}/css/dashboard.css" rel="stylesheet">
    </head>

    <body>
        <div class="container">
            <h1>
                Welcome, <%= username != null ? username : "Employee" %>
                <a class="logout-btn" href="<%= request.getContextPath() %>/logout">Logout</a>
            </h1>

            <h2><%= editComplaint != null ? "Update Complaint" : "Submit a New Complaint" %></h2>

            <form id="complaintForm" action="<%= request.getContextPath() %><%= editComplaint != null ? "/updateComplaint" : "/complaint" %>" method="post" onsubmit="return validateComplaintForm()">
                <%
                    if (editComplaint != null) {
                %>
                        <input type="hidden" name="id" value="<%= editComplaint.getId() %>" />
                <%
                    }
                %>

                <input type="text" id="title" name="title" placeholder="Complaint Title"
                       value="<%= editComplaint != null ? editComplaint.getTitle() : "" %>" required maxlength="100" /><br/>

                <textarea id="description" name="description" placeholder="Complaint Description" rows="4" required maxlength="500"><%= editComplaint != null ? editComplaint.getDescription() : "" %></textarea><br/>

                <div class="form-buttons">
                    <input type="submit"
                           value="<%= editComplaint != null ? "Update Complaint" : "Submit Complaint" %>"
                           class="<%= editComplaint != null ? "update-btn" : "" %>" />

                    <%
                        if (editComplaint != null) {
                    %>
                            <a href="<%= request.getContextPath() %>/complaint" class="cancel-btn">Cancel</a>
                    <%
                        }
                    %>
                </div>
            </form>

            <h2>Your Complaints</h2>
            <table>
                <tr>
                    <th>ID</th>
                    <th>Title</th>
                    <th>Description</th>
                    <th>Status</th>
                    <th>Remarks</th>
                    <th>Actions</th>
                </tr>
                <%
                    if (complaints != null && !complaints.isEmpty()) {
                        for (ComplaintDTO c : complaints) {
                %>
                            <tr>
                                <td><%= c.getId() %></td>
                                <td><%= c.getTitle() %></td>
                                <td><%= c.getDescription() %></td>
                                <td><%= c.getStatus() %></td>
                                <td><%= c.getRemarks() != null ? c.getRemarks() : "-" %></td>
                                <td class="actions">
                                    <% if ("RESOLVED".equalsIgnoreCase(c.getStatus())) { %>
                                    <span class="edit-link disabled" style="color:gray; cursor: not-allowed;">Edit</span>
                                    <button class="delete-link disabled" disabled style="color:gray; cursor: not-allowed;">Delete</button>
                                    <% } else { %>
                                    <a class="edit-link" href="<%= request.getContextPath() %>/complaint?editId=<%= c.getId() %>">Edit</a>
                                    <form action="<%= request.getContextPath() %>/complaintAction" method="post" onsubmit="return confirm('Are you sure you want to delete this complaint?');" style="display:inline;">
                                        <input type="hidden" name="id" value="<%= c.getId() %>" />
                                        <input type="submit" class="delete-link" value="Delete" />
                                    </form>
                                    <% } %>
                                </td>
                            </tr>
                <%
                        }
                    } else {
                %>
                        <tr>
                            <td colspan="6">You haven't submitted any complaints yet.</td>
                        </tr>
                <%
                    }
                %>
            </table>
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
