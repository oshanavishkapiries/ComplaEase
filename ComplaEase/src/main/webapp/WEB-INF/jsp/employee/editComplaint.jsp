<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Edit Complaint - Complaint Management System</title>
    <link rel="stylesheet" href="/css/style.css">
    <script src="/js/formValidation.js"></script>
</head>
<body>
<div class="container">
    <h2>Edit Complaint</h2>
    <form name="editForm" action="../complaints/update" method="post" onsubmit="return validateEditForm();">
        <input type="hidden" name="id" value="${complaint.id}">
        <div class="form-group">
            <label for="title">Title:</label>
            <input type="text" id="title" name="title" value="${complaint.title}" required>
        </div>
        <div class="form-group">
            <label for="description">Description:</label>
            <textarea id="description" name="description" required>${complaint.description}</textarea>
        </div>
        <div class="form-group">
            <label for="priority">Priority:</label>
            <select id="priority" name="priority" required>
                <option value="LOW" ${complaint.priority == 'LOW' ? 'selected' : ''}>Low</option>
                <option value="MEDIUM" ${complaint.priority == 'MEDIUM' ? 'selected' : ''}>Medium</option>
                <option value="HIGH" ${complaint.priority == 'HIGH' ? 'selected' : ''}>High</option>
            </select>
        </div>
        <c:if test="${not empty error}">
            <div class="error">${error}</div>
        </c:if>
        <button type="submit">Update</button>
    </form>
    <a href="../complaints">Back to My Complaints</a>
</div>
</body>
</html> 