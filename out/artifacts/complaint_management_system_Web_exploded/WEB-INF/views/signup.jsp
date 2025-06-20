<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sign Up - Complaint Management System</title>
    <link href="${pageContext.request.contextPath}/css/signup.css" rel="stylesheet">
</head>
<body>
<div class="signup-container">
    <h1>Sign Up</h1>

    <%
        String errorMessage = (String) request.getAttribute("error");
        if (errorMessage != null) {
    %>
    <div style="color: red;"><%= errorMessage %></div>
    <%
        }
    %>

    <form id="signupForm" action="<%= request.getContextPath() %>/signup" method="post">
        <input type="text" id="username" name="username" placeholder="Username" required />
        <input type="password" id="password" name="password" placeholder="Password" required />
        <input type="password" id="confirmPassword" name="confirmPassword" placeholder="Confirm Password" required />
        <select id="role" name="role" required>
            <option value="" disabled selected>Select Role</option>
            <option value="ADMIN">Admin</option>
            <option value="EMPLOYEE">Employee</option>
        </select>
        <input type="submit" value="Sign Up" />
    </form>

    <a class="signin-link" href="<%= request.getContextPath() %>/login">Already have an account? Sign in</a>
</div>

<script>
    function validateForm() {
        const username = document.getElementById("username").value.trim();
        const password = document.getElementById("password").value;
        const confirmPassword = document.getElementById("confirmPassword").value;
        const role = document.getElementById("role").value;

        if (!username || !password || !confirmPassword || !role) {
            alert("All fields are required.");
            return false;
        }

        if (password.length < 8) {
            alert("Password must be at least 8 characters.");
            return false;
        }

        if (password !== confirmPassword) {
            alert("Passwords do not match.");
            return false;
        }

        return true;
    }

    document.getElementById("signupForm").onsubmit = validateForm;
</script>
</body>
</html>
