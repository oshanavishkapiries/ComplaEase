package com.cms.controller;

import com.cms.dao.ComplaintDAO;
import com.cms.model.Complaint;
import com.cms.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

/**
 * Servlet for handling complaint operations in the Complaint Management System
 */
@WebServlet("/complaints/*")
public class ComplaintServlet extends HttpServlet {
    private ComplaintDAO complaintDAO;

    @Override
    public void init() throws ServletException {
        complaintDAO = new ComplaintDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Check if user is logged in
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login");
            return;
        }

        User user = (User) session.getAttribute("user");
        String pathInfo = request.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            // List complaints
            listComplaints(request, response, user);
        } else if (pathInfo.equals("/new")) {
            // Show new complaint form
            showNewComplaintForm(request, response);
        } else if (pathInfo.equals("/edit")) {
            // Show edit complaint form
            showEditComplaintForm(request, response, user);
        } else if (pathInfo.equals("/view")) {
            // Show complaint details
            showComplaintDetails(request, response, user);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Check if user is logged in
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login");
            return;
        }

        User user = (User) session.getAttribute("user");
        String pathInfo = request.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            // Create new complaint
            createComplaint(request, response, user);
        } else if (pathInfo.equals("/update")) {
            // Update complaint
            updateComplaint(request, response, user);
        } else if (pathInfo.equals("/delete")) {
            // Delete complaint
            deleteComplaint(request, response, user);
        } else if (pathInfo.equals("/status")) {
            // Update complaint status (admin only)
            updateComplaintStatus(request, response, user);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void listComplaints(HttpServletRequest request, HttpServletResponse response, User user)
            throws ServletException, IOException {
        List<Complaint> complaints;

        if (user.isAdmin()) {
            // Admin sees all complaints
            complaints = complaintDAO.getAllComplaints();
        } else {
            // Employee sees only their complaints
            complaints = complaintDAO.getComplaintsByUserId(user.getId());
        }

        request.setAttribute("complaints", complaints);
        request.setAttribute("isAdmin", user.isAdmin());

        if (user.isAdmin()) {
            request.getRequestDispatcher("/WEB-INF/jsp/admin/complaintList.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("/WEB-INF/jsp/employee/complaintList.jsp").forward(request, response);
        }
    }

    private void showNewComplaintForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsp/employee/newComplaint.jsp").forward(request, response);
    }

    private void showEditComplaintForm(HttpServletRequest request, HttpServletResponse response, User user)
            throws ServletException, IOException {
        String complaintId = request.getParameter("id");
        if (complaintId == null || complaintId.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Complaint ID is required");
            return;
        }

        try {
            int id = Integer.parseInt(complaintId);
            Complaint complaint = complaintDAO.getComplaintById(id);

            if (complaint == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Complaint not found");
                return;
            }

            // Check if user can edit this complaint
            if (!user.isAdmin() && complaint.getUserId() != user.getId()) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied");
                return;
            }

            // Check if complaint is resolved (cannot edit resolved complaints)
            if (complaint.isResolved()) {
                request.setAttribute("error", "Cannot edit resolved complaints");
                listComplaints(request, response, user);
                return;
            }

            request.setAttribute("complaint", complaint);
            request.setAttribute("isAdmin", user.isAdmin());

            if (user.isAdmin()) {
                request.getRequestDispatcher("/WEB-INF/jsp/admin/editComplaint.jsp").forward(request, response);
            } else {
                request.getRequestDispatcher("/WEB-INF/jsp/employee/editComplaint.jsp").forward(request, response);
            }

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid complaint ID");
        }
    }

    private void showComplaintDetails(HttpServletRequest request, HttpServletResponse response, User user)
            throws ServletException, IOException {
        String complaintId = request.getParameter("id");
        if (complaintId == null || complaintId.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Complaint ID is required");
            return;
        }

        try {
            int id = Integer.parseInt(complaintId);
            Complaint complaint = complaintDAO.getComplaintById(id);

            if (complaint == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Complaint not found");
                return;
            }

            // Check if user can view this complaint
            if (!user.isAdmin() && complaint.getUserId() != user.getId()) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied");
                return;
            }

            request.setAttribute("complaint", complaint);
            request.setAttribute("isAdmin", user.isAdmin());

            if (user.isAdmin()) {
                request.getRequestDispatcher("/WEB-INF/jsp/admin/complaintDetails.jsp").forward(request, response);
            } else {
                request.getRequestDispatcher("/WEB-INF/jsp/employee/complaintDetails.jsp").forward(request, response);
            }

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid complaint ID");
        }
    }

    private void createComplaint(HttpServletRequest request, HttpServletResponse response, User user)
            throws ServletException, IOException {
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String priority = request.getParameter("priority");

        // Validate input
        if (title == null || title.trim().isEmpty() ||
                description == null || description.trim().isEmpty() ||
                priority == null || priority.trim().isEmpty()) {
            request.setAttribute("error", "All fields are required");
            request.setAttribute("title", title);
            request.setAttribute("description", description);
            request.setAttribute("priority", priority);
            request.getRequestDispatcher("/WEB-INF/jsp/employee/newComplaint.jsp").forward(request, response);
            return;
        }

        try {
            Complaint.ComplaintPriority complaintPriority = Complaint.ComplaintPriority.valueOf(priority.toUpperCase());

            Complaint complaint = new Complaint(title.trim(), description.trim(), complaintPriority, user.getId());

            if (complaintDAO.createComplaint(complaint)) {
                request.setAttribute("success", "Complaint submitted successfully");
            } else {
                request.setAttribute("error", "Failed to submit complaint");
            }

        } catch (IllegalArgumentException e) {
            request.setAttribute("error", "Invalid priority value");
            request.setAttribute("title", title);
            request.setAttribute("description", description);
            request.setAttribute("priority", priority);
            request.getRequestDispatcher("/WEB-INF/jsp/employee/newComplaint.jsp").forward(request, response);
            return;
        }

        // Redirect to complaint list
        response.sendRedirect("complaints");
    }

    private void updateComplaint(HttpServletRequest request, HttpServletResponse response, User user)
            throws ServletException, IOException {
        String complaintId = request.getParameter("id");
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String priority = request.getParameter("priority");

        // Validate input
        if (complaintId == null || complaintId.trim().isEmpty() ||
                title == null || title.trim().isEmpty() ||
                description == null || description.trim().isEmpty() ||
                priority == null || priority.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "All fields are required");
            return;
        }

        try {
            int id = Integer.parseInt(complaintId);
            Complaint complaint = complaintDAO.getComplaintById(id);

            if (complaint == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Complaint not found");
                return;
            }

            // Check if user can edit this complaint
            if (!user.isAdmin() && complaint.getUserId() != user.getId()) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied");
                return;
            }

            // Check if complaint is resolved
            if (complaint.isResolved()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Cannot edit resolved complaints");
                return;
            }

            Complaint.ComplaintPriority complaintPriority = Complaint.ComplaintPriority.valueOf(priority.toUpperCase());

            complaint.setTitle(title.trim());
            complaint.setDescription(description.trim());
            complaint.setPriority(complaintPriority);

            if (complaintDAO.updateComplaint(complaint)) {
                request.setAttribute("success", "Complaint updated successfully");
            } else {
                request.setAttribute("error", "Failed to update complaint");
            }

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid complaint ID");
            return;
        } catch (IllegalArgumentException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid priority value");
            return;
        }

        // Redirect to complaint list
        response.sendRedirect("complaints");
    }

    private void deleteComplaint(HttpServletRequest request, HttpServletResponse response, User user)
            throws ServletException, IOException {
        String complaintId = request.getParameter("id");

        if (complaintId == null || complaintId.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Complaint ID is required");
            return;
        }

        try {
            int id = Integer.parseInt(complaintId);
            Complaint complaint = complaintDAO.getComplaintById(id);

            if (complaint == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Complaint not found");
                return;
            }

            // Check if user can delete this complaint
            if (!user.isAdmin() && complaint.getUserId() != user.getId()) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied");
                return;
            }

            // Check if complaint is resolved (cannot delete resolved complaints)
            if (complaint.isResolved()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Cannot delete resolved complaints");
                return;
            }

            if (complaintDAO.deleteComplaint(id)) {
                request.setAttribute("success", "Complaint deleted successfully");
            } else {
                request.setAttribute("error", "Failed to delete complaint");
            }

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid complaint ID");
            return;
        }

        // Redirect to complaint list
        response.sendRedirect("complaints");
    }

    private void updateComplaintStatus(HttpServletRequest request, HttpServletResponse response, User user)
            throws ServletException, IOException {
        // Only admins can update complaint status
        if (!user.isAdmin()) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied");
            return;
        }

        String complaintId = request.getParameter("id");
        String status = request.getParameter("status");
        String adminRemarks = request.getParameter("adminRemarks");

        if (complaintId == null || complaintId.trim().isEmpty() ||
                status == null || status.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Complaint ID and status are required");
            return;
        }

        try {
            int id = Integer.parseInt(complaintId);
            Complaint.ComplaintStatus complaintStatus = Complaint.ComplaintStatus.valueOf(status.toUpperCase());

            if (complaintDAO.updateComplaintStatus(id, complaintStatus, adminRemarks)) {
                request.setAttribute("success", "Complaint status updated successfully");
            } else {
                request.setAttribute("error", "Failed to update complaint status");
            }

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid complaint ID");
            return;
        } catch (IllegalArgumentException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid status value");
            return;
        }

        // Redirect to complaint list
        response.sendRedirect("complaints");
    }
}