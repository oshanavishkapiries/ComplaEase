package edu.cms.controller;

import edu.cms.dto.ComplaintDTO;
import edu.cms.dao.DAOFactory;
import edu.cms.repository.ComplaintRepository;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.sql.DataSource;
import java.io.IOException;

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("update".equalsIgnoreCase(action)) {
            try {
                int id = Integer.parseInt(req.getParameter("id"));
                String status = req.getParameter("status");
                String remarks = req.getParameter("remarks");

                DataSource dataSource = (DataSource) getServletContext().getAttribute("ds");
                DAOFactory daoFactory = DAOFactory.getInstance(dataSource);
                ComplaintRepository complaintRepository = daoFactory.getComplaintRepository();

                boolean updated = complaintRepository.updateStatus(id, status, remarks);

                if (updated) {
                    req.setAttribute("message", "Complaint updated successfully.");
                } else {
                    req.setAttribute("error", "Failed to update complaint. Try again.");
                }
            } catch (Exception e) {
                req.setAttribute("error", "An error occurred while updating the complaint.");
                e.printStackTrace();
            }
        }

        RequestDispatcher dispatcher = req.getRequestDispatcher("/complaint");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/views/admin_dashboard.jsp");
        rd.forward(req, resp);
    }
}
