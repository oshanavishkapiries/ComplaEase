<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Login - Complaint Management System</title>
        <link href="${pageContext.request.contextPath}/css/login.css" rel="stylesheet">
        <script>
            function validateLoginForm() {
                const username = document.forms["loginForm"]["username"].value.trim();
                const password = document.forms["loginForm"]["password"].value.trim();

                if (username === "") {
                    alert("Username is required.");
                    return false;
                }

                if (password === "") {
                    alert("Password is required.");
                    return false;
                }

                return true;
            }
        </script>
    </head>
    <body>
        <div class="login-container">
            <h1>Login</h1>
            <%
                String errorMessage = (String) request.getAttribute("errorMessage");
                if (errorMessage != null) {
            %>
                    <div style="color: red;"><%= errorMessage %></div>
            <%
                }
            %>

            <form name="loginForm" action="<%= request.getContextPath() %>/login" method="post" onsubmit="return validateLoginForm()">
                <input type="text" name="username" placeholder="Username" required />
                <input type="password" name="password" placeholder="Password" required />
                <input type="submit" value="Login" />
            </form>

            <a class="signup-link" href="<%= request.getContextPath() %>/signup">Don't have an account? Sign up</a>
        </div>
    </body>
</html>

