<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Complaint Management System</title>
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
        max-width: 1200px;
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

      .hero-content {
        position: relative;
        z-index: 2;
      }

      .hero-icon {
        font-size: 4rem;
        margin-bottom: 20px;
        color: #e8f5e8;
      }

      .hero-title {
        font-size: 2.5rem;
        font-weight: 700;
        margin-bottom: 20px;
        line-height: 1.2;
      }

      .hero-subtitle {
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

      .cta-button {
        display: inline-block;
        background: white;
        color: #4caf50;
        padding: 15px 30px;
        text-decoration: none;
        border-radius: 50px;
        font-weight: 600;
        font-size: 1.1rem;
        transition: all 0.3s ease;
        box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
      }

      .cta-button:hover {
        transform: translateY(-2px);
        box-shadow: 0 8px 25px rgba(0, 0, 0, 0.15);
        background: #f8f9fa;
      }

      .right-panel {
        flex: 1;
        padding: 60px 40px;
        display: flex;
        flex-direction: column;
        justify-content: center;
        background: #f8f9fa;
      }

      .welcome-section {
        text-align: center;
        margin-bottom: 40px;
      }

      .welcome-icon {
        font-size: 3rem;
        color: #4caf50;
        margin-bottom: 20px;
      }

      .welcome-title {
        font-size: 2rem;
        color: #333;
        margin-bottom: 15px;
        font-weight: 600;
      }

      .welcome-text {
        color: #666;
        font-size: 1.1rem;
        line-height: 1.6;
        margin-bottom: 30px;
      }

      .action-buttons {
        display: flex;
        gap: 20px;
        justify-content: center;
        flex-wrap: wrap;
      }

      .action-btn {
        display: flex;
        align-items: center;
        padding: 15px 25px;
        border-radius: 10px;
        text-decoration: none;
        font-weight: 600;
        transition: all 0.3s ease;
        min-width: 160px;
        justify-content: center;
      }

      .btn-primary {
        background: #4caf50;
        color: white;
      }

      .btn-primary:hover {
        background: #45a049;
        transform: translateY(-2px);
        box-shadow: 0 8px 20px rgba(76, 175, 80, 0.3);
      }

      .btn-secondary {
        background: white;
        color: #4caf50;
        border: 2px solid #4caf50;
      }

      .btn-secondary:hover {
        background: #4caf50;
        color: white;
        transform: translateY(-2px);
      }

      .action-btn i {
        margin-right: 8px;
        font-size: 1.1rem;
      }

      .stats-section {
        margin-top: 40px;
        display: grid;
        grid-template-columns: repeat(3, 1fr);
        gap: 20px;
      }

      .stat-card {
        background: white;
        padding: 20px;
        border-radius: 10px;
        text-align: center;
        box-shadow: 0 4px 15px rgba(0, 0, 0, 0.05);
        border-left: 4px solid #4caf50;
      }

      .stat-number {
        font-size: 1.8rem;
        font-weight: 700;
        color: #4caf50;
        margin-bottom: 5px;
      }

      .stat-label {
        color: #666;
        font-size: 0.9rem;
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

        .hero-title {
          font-size: 2rem;
        }

        .action-buttons {
          flex-direction: column;
        }

        .stats-section {
          grid-template-columns: 1fr;
        }
      }
    </style>
  </head>
  <body>
    <div class="container">
      <!-- Left Panel - Hero Section -->
      <div class="left-panel">
        <div class="hero-content">
          <div class="hero-icon">
            <i class="fas fa-clipboard-check"></i>
          </div>
          <h1 class="hero-title">Complaint Management System</h1>
          <p class="hero-subtitle">
            Streamline your complaint handling process with our comprehensive
            management solution. Track, resolve, and improve customer
            satisfaction efficiently.
          </p>
          <ul class="features">
            <li>
              <i class="fas fa-check-circle"></i> Easy complaint submission
            </li>
            <li>
              <i class="fas fa-check-circle"></i> Real-time status tracking
            </li>
            <li>
              <i class="fas fa-check-circle"></i> Automated workflow management
            </li>
            <li><i class="fas fa-check-circle"></i> Comprehensive reporting</li>
          </ul>
          <a href="login" class="cta-button">
            <i class="fas fa-rocket"></i> Get Started Now
          </a>
        </div>
      </div>

      <!-- Right Panel - Welcome Section -->
      <div class="right-panel">
        <div class="welcome-section">
          <div class="welcome-icon">
            <i class="fas fa-users"></i>
          </div>
          <h2 class="welcome-title">Welcome Back!</h2>
          <p class="welcome-text">
            Access your dashboard to manage complaints, track progress, and
            ensure customer satisfaction. Choose your role to get started.
          </p>

          <div class="action-buttons">
            <a href="login" class="action-btn btn-primary">
              <i class="fas fa-sign-in-alt"></i> Login
            </a>
            <a href="signup" class="action-btn btn-secondary">
              <i class="fas fa-user-plus"></i> Sign Up
            </a>
          </div>
        </div>

        <div class="stats-section">
          <div class="stat-card">
            <div class="stat-number">500+</div>
            <div class="stat-label">Complaints Resolved</div>
          </div>
          <div class="stat-card">
            <div class="stat-number">98%</div>
            <div class="stat-label">Satisfaction Rate</div>
          </div>
          <div class="stat-card">
            <div class="stat-number">24h</div>
            <div class="stat-label">Avg Response Time</div>
          </div>
        </div>
      </div>
    </div>
  </body>
</html>
