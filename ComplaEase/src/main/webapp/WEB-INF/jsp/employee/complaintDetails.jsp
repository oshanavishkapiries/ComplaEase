<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
  <head>
    <title>Complaint Details - Complaint Management System</title>
    <link rel="stylesheet" href="/css/style.css" />
  </head>
  <body>
    <div class="container">
      <h2>Complaint Details</h2>
      <table>
        <tr>
          <th>Title:</th>
          <td>${complaint.title}</td>
        </tr>
        <tr>
          <th>Description:</th>
          <td>${complaint.description}</td>
        </tr>
        <tr>
          <th>Status:</th>
          <td>${complaint.statusDisplayName}</td>
        </tr>
        <tr>
          <th>Priority:</th>
          <td>${complaint.priorityDisplayName}</td>
        </tr>
        <tr>
          <th>Created At:</th>
          <td>${complaint.createdAt}</td>
        </tr>
        <tr>
          <th>Admin Remarks:</th>
          <td>${complaint.adminRemarks}</td>
        </tr>
      </table>
      <a href="../complaints">Back to My Complaints</a>
    </div>
  </body>
</html>
