package edu.cms.controller;

import edu.cms.dto.ComplaintDTO;
import edu.cms.dao.DAOFactory;
import edu.cms.repository.ComplaintRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;

@WebServlet("/complaint")
public class ComplaintServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer userId = (Integer) req.getSession().getAttribute("userId");

        if (userId == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String title = req.getParameter("title");
        String description = req.getParameter("description");

        if (title == null || description == null || title.trim().isEmpty() || description.trim().isEmpty()) {
            req.setAttribute("errorMessage", "Title and Description are required.");
            doGet(req, resp);
            return;
        }

        ComplaintDTO complaintDTO = ComplaintDTO.builder()
                .userId(userId)
                .title(title)
                .description(description)
                .status("PENDING")
                .build();

        DataSource dataSource = (DataSource) getServletContext().getAttribute("ds");
        DAOFactory daoFactory = DAOFactory.getInstance(dataSource);
        ComplaintRepository complaintRepository = daoFactory.getComplaintRepository();

        boolean inserted = complaintRepository.save(complaintDTO).isPresent();

        if (!inserted) {
            req.setAttribute("errorMessage", "Failed to submit complaint.");
        }

        resp.sendRedirect(req.getContextPath() + "/complaint");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer userId = (Integer) req.getSession().getAttribute("userId");
        String role = (String) req.getSession().getAttribute("role");

        if (userId == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        DataSource dataSource = (DataSource) getServletContext().getAttribute("ds");
        DAOFactory daoFactory = DAOFactory.getInstance(dataSource);
        ComplaintRepository complaintRepository = daoFactory.getComplaintRepository();

        if ("EMPLOYEE".equalsIgnoreCase(role)) {
            List<ComplaintDTO> complaints = complaintRepository.findByUserId(userId);

            req.setAttribute("complaints", complaints);
            req.getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(req, resp);

        } else if ("ADMIN".equalsIgnoreCase(role)) {
            List<ComplaintDTO> complaints = complaintRepository.findAll();

            req.setAttribute("complaints", complaints);
            req.getRequestDispatcher("WEB-INF/views/admin_dashboard.jsp").forward(req, resp);

        }
    }
}
