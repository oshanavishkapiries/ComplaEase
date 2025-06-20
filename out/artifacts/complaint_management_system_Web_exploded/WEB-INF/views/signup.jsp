<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Sign Up - Complaint Management System</title>
    <link
      href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css"
      rel="stylesheet"
    />
    <style>
      * {
        margin: 0;
        padding: 0;
        box-sizing: border-box;
      }

      body {
        font-family: "Segoe UI", Tahoma, Geneva, Verdana, sans-serif;
        background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
        min-height: 100vh;
        display: flex;
        align-items: center;
        justify-content: center;
      }

      .container {
        display: flex;
        width: 90%;
        max-width: 1000px;
        background: white;
        border-radius: 20px;
        box-shadow: 0 20px 40px rgba(0, 0, 0, 0.1);
        overflow: hidden;
        min-height: 600px;
      }

      .left-panel {
        flex: 1;
        background: linear-gradient(135deg, #4caf50 0%, #45a049 100%);
        color: white;
        padding: 60px 40px;
        display: flex;
        flex-direction: column;
        justify-content: center;
        position: relative;
        overflow: hidden;
      }

      .left-panel::before {
        content: "";
        position: absolute;
        top: -50%;
        right: -50%;
        width: 200%;
        height: 200%;
        background: url('data:image/svg+xml,<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 100 100"><defs><pattern id="grain" width="100" height="100" patternUnits="userSpaceOnUse"><circle cx="25" cy="25" r="1" fill="white" opacity="0.1"/><circle cx="75" cy="75" r="1" fill="white" opacity="0.1"/><circle cx="50" cy="10" r="0.5" fill="white" opacity="0.1"/><circle cx="10" cy="60" r="0.5" fill="white" opacity="0.1"/><circle cx="90" cy="40" r="0.5" fill="white" opacity="0.1"/></pattern></defs><rect width="100" height="100" fill="url(%23grain)"/></svg>');
        opacity: 0.3;
      }

      .welcome-content {
        position: relative;
        z-index: 2;
      }

      .welcome-icon {
        font-size: 4rem;
        margin-bottom: 20px;
        color: #e8f5e8;
      }

      .welcome-title {
        font-size: 2.5rem;
        font-weight: 700;
        margin-bottom: 20px;
        line-height: 1.2;
      }

      .welcome-subtitle {
        font-size: 1.1rem;
        margin-bottom: 30px;
        opacity: 0.9;
        line-height: 1.6;
      }

      .features {
        list-style: none;
        margin-bottom: 40px;
      }

      .features li {
        margin-bottom: 15px;
        display: flex;
        align-items: center;
        font-size: 1rem;
      }

      .features i {
        margin-right: 12px;
        color: #e8f5e8;
        font-size: 1.1rem;
      }

      .back-link {
        color: #e8f5e8;
        text-decoration: none;
        display: inline-flex;
        align-items: center;
        font-weight: 500;
        transition: all 0.3s ease;
      }

      .back-link:hover {
        color: white;
        transform: translateX(-5px);
      }

      .back-link i {
        margin-right: 8px;
      }

      .right-panel {
        flex: 1;
        padding: 60px 40px;
        display: flex;
        flex-direction: column;
        justify-content: center;
        background: #f8f9fa;
      }

      .signup-section {
        max-width: 400px;
        margin: 0 auto;
        width: 100%;
      }

      .signup-header {
        text-align: center;
        margin-bottom: 40px;
      }

      .signup-icon {
        font-size: 3rem;
        color: #4caf50;
        margin-bottom: 20px;
      }

      .signup-title {
        font-size: 2rem;
        color: #333;
        margin-bottom: 10px;
        font-weight: 600;
      }

      .signup-subtitle {
        color: #666;
        font-size: 1rem;
      }

      .error-message {
        background: #ffebee;
        color: #c62828;
        padding: 15px;
        border-radius: 10px;
        margin-bottom: 20px;
        border-left: 4px solid #f44336;
        font-size: 0.9rem;
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

      .input-group {
        position: relative;
      }

      .input-group i {
        position: absolute;
        left: 15px;
        top: 50%;
        transform: translateY(-50%);
        color: #4caf50;
        font-size: 1.1rem;
      }

      .form-control {
        width: 100%;
        padding: 15px 15px 15px 45px;
        border: 2px solid #e0e0e0;
        border-radius: 10px;
        font-size: 1rem;
        transition: all 0.3s ease;
        background: white;
      }

      .form-control:focus {
        outline: none;
        border-color: #4caf50;
        box-shadow: 0 0 0 3px rgba(76, 175, 80, 0.1);
      }

      .form-control::placeholder {
        color: #999;
      }

      .select-group {
        position: relative;
      }

      .select-group i {
        position: absolute;
        left: 15px;
        top: 50%;
        transform: translateY(-50%);
        color: #4caf50;
        font-size: 1.1rem;
        pointer-events: none;
      }

      .form-select {
        width: 100%;
        padding: 15px 15px 15px 45px;
        border: 2px solid #e0e0e0;
        border-radius: 10px;
        font-size: 1rem;
        transition: all 0.3s ease;
        background: white;
        appearance: none;
        cursor: pointer;
      }

      .form-select:focus {
        outline: none;
        border-color: #4caf50;
        box-shadow: 0 0 0 3px rgba(76, 175, 80, 0.1);
      }

      .signup-btn {
        width: 100%;
        background: #4caf50;
        color: white;
        padding: 15px;
        border: none;
        border-radius: 10px;
        font-size: 1.1rem;
        font-weight: 600;
        cursor: pointer;
        transition: all 0.3s ease;
        margin-bottom: 20px;
      }

      .signup-btn:hover {
        background: #45a049;
        transform: translateY(-2px);
        box-shadow: 0 8px 20px rgba(76, 175, 80, 0.3);
      }

      .signup-btn:active {
        transform: translateY(0);
      }

      .login-link {
        text-align: center;
        color: #666;
        font-size: 0.9rem;
      }

      .login-link a {
        color: #4caf50;
        text-decoration: none;
        font-weight: 600;
        transition: color 0.3s ease;
      }

      .login-link a:hover {
        color: #45a049;
      }

      @media (max-width: 768px) {
        .container {
          flex-direction: column;
          width: 95%;
        }

        .left-panel,
        .right-panel {
          padding: 40px 20px;
        }

        .welcome-title {
          font-size: 2rem;
        }

        .signup-title {
          font-size: 1.8rem;
        }
      }
    </style>
  </head>
  <body>
    <div class="container">
      <!-- Left Panel - Welcome Section -->
      <div class="left-panel">
        <div class="welcome-content">
          <div class="welcome-icon">
            <i class="fas fa-user-plus"></i>
          </div>
          <h1 class="welcome-title">Join Our Team!</h1>
          <p class="welcome-subtitle">
            Create your account and start managing complaints efficiently.
            Choose your role and begin your journey with us.
          </p>
          <ul class="features">
            <li><i class="fas fa-check-circle"></i> Quick registration</li>
            <li><i class="fas fa-check-circle"></i> Role-based access</li>
            <li><i class="fas fa-check-circle"></i> Secure platform</li>
            <li><i class="fas fa-check-circle"></i> Instant access</li>
          </ul>
          <a href="${pageContext.request.contextPath}/" class="back-link">
            <i class="fas fa-arrow-left"></i> Back to Home
          </a>
        </div>
      </div>

      <!-- Right Panel - Signup Form -->
      <div class="right-panel">
        <div class="signup-section">
          <div class="signup-header">
            <div class="signup-icon">
              <i class="fas fa-user-plus"></i>
            </div>
            <h2 class="signup-title">Sign Up</h2>
            <p class="signup-subtitle">Create your account to get started</p>
          </div>

          <% String errorMessage = (String) request.getAttribute("error"); if
          (errorMessage != null) { %>
          <div class="error-message">
            <i class="fas fa-exclamation-triangle"></i> <%= errorMessage %>
          </div>
          <% } %>

          <form
            id="signupForm"
            action="<%= request.getContextPath() %>/signup"
            method="post"
            onsubmit="return validateForm()"
          >
            <div class="form-group">
              <label for="username">Username</label>
              <div class="input-group">
                <i class="fas fa-user"></i>
                <input
                  type="text"
                  id="username"
                  name="username"
                  class="form-control"
                  placeholder="Enter your username"
                  required
                />
              </div>
            </div>

            <div class="form-group">
              <label for="password">Password</label>
              <div class="input-group">
                <i class="fas fa-lock"></i>
                <input
                  type="password"
                  id="password"
                  name="password"
                  class="form-control"
                  placeholder="Enter your password"
                  required
                />
              </div>
            </div>

            <div class="form-group">
              <label for="confirmPassword">Confirm Password</label>
              <div class="input-group">
                <i class="fas fa-lock"></i>
                <input
                  type="password"
                  id="confirmPassword"
                  name="confirmPassword"
                  class="form-control"
                  placeholder="Confirm your password"
                  required
                />
              </div>
            </div>

            <div class="form-group">
              <label for="role">Role</label>
              <div class="select-group">
                <i class="fas fa-user-tag"></i>
                <select id="role" name="role" class="form-select" required>
                  <option value="" disabled selected>Select Role</option>
                  <option value="ADMIN">Admin</option>
                  <option value="EMPLOYEE">Employee</option>
                </select>
              </div>
            </div>

            <button type="submit" class="signup-btn">
              <i class="fas fa-user-plus"></i> Sign Up
            </button>
          </form>

          <div class="login-link">
            Already have an account?
            <a href="<%= request.getContextPath() %>/login">Sign in here</a>
          </div>
        </div>
      </div>
    </div>

    <script>
      function validateForm() {
        const username = document.getElementById("username").value.trim();
        const password = document.getElementById("password").value;
        const confirmPassword =
          document.getElementById("confirmPassword").value;
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
    </script>
  </body>
</html>
