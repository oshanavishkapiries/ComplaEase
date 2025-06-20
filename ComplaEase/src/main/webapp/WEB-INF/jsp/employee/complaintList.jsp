<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>My Complaints - Complaint Management System</title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
<div class="container">
    <h2>My Complaints</h2>
    <a href="complaints/new">Submit New Complaint</a>
    <table>
        <thead>
        <tr>
            <th>Title</th>
            <th>Status</th>
            <th>Priority</th>
            <th>Created At</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="complaint" items="${complaints}">
            <tr>
                <td><a href="complaints/view?id=${complaint.id}">${complaint.title}</a></td>
                <td>${complaint.statusDisplayName}</td>
                <td>${complaint.priorityDisplayName}</td>
                <td>${complaint.createdAt}</td>
                <td>
                    <c:if test="${!complaint.resolved}">
                        <a href="complaints/edit?id=${complaint.id}">Edit</a>
                        <form action="complaints/delete" method="post" style="display:inline;">
                            <input type="hidden" name="id" value="${complaint.id}">
                            <button type="submit" onclick="return confirm('Are you sure you want to delete this complaint?');">Delete</button>
                        </form>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <a href="logout">Logout</a>
</div>
</body>
</html> 