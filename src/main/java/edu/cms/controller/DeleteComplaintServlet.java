package edu.cms.controller;

import edu.cms.dao.DAOFactory;
import edu.cms.repository.ComplaintRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.sql.DataSource;
import java.io.IOException;

@WebServlet("/complaintAction")
public class DeleteComplaintServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idParam = req.getParameter("id");

        if (idParam == null || idParam.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing complaint ID");
            return;
        }

        int id;
        try {
            id = Integer.parseInt(idParam);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid complaint ID format");
            return;
        }

        DataSource dataSource = (DataSource) getServletContext().getAttribute("ds");
        DAOFactory daoFactory = DAOFactory.getInstance(dataSource);
        ComplaintRepository complaintRepository = daoFactory.getComplaintRepository();

        boolean deleted = complaintRepository.deleteById(id);

        if (deleted) {
            resp.sendRedirect(req.getContextPath() + "/complaint");
        } else {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to delete complaint");
        }
    }
}
