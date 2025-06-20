<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
  <head>
    <title>Login - Complaint Management System</title>
    <link rel="stylesheet" href="/css/style.css" />
    <script src="/js/formValidation.js"></script>
  </head>
  <body>
    <div class="container">
      <h2>Complaint Management System Login</h2>
      <form
        name="loginForm"
        action="login"
        method="post"
        onsubmit="return validateLoginForm();"
      >
        <div class="form-group">
          <label for="username">Username:</label>
          <input
            type="text"
            id="username"
            name="username"
            value="${username != null ? username : ''}"
            required
          />
        </div>
        <div class="form-group">
          <label for="password">Password:</label>
          <input type="password" id="password" name="password" required />
        </div>
        <c:if test="${not empty error}">
          <div class="error">${error}</div>
        </c:if>
        <button type="submit">Login</button>
      </form>
    </div>
  </body>
</html>
