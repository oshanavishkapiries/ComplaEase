package edu.cms.controller;

import edu.cms.dto.ComplaintDTO;
import edu.cms.dao.DAOFactory;
import edu.cms.repository.ComplaintRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import javax.sql.DataSource;
import java.io.IOException;

@WebServlet("/updateComplaint")
public class UpdateComplaintServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String idParam = req.getParameter("id");
            String title = req.getParameter("title");
            String description = req.getParameter("description");

            int id = Integer.parseInt(idParam.trim());

            if (id <= 0) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid complaint ID.");
                return;
            }

            ComplaintDTO complaintDTO = ComplaintDTO.builder()
                    .id(id)
                    .title(title.trim())
                    .description(description.trim())
                    .build();

            DataSource dataSource = (DataSource) getServletContext().getAttribute("ds");
            DAOFactory daoFactory = DAOFactory.getInstance(dataSource);
            ComplaintRepository complaintRepository = daoFactory.getComplaintRepository();

            boolean updated = complaintRepository.update(complaintDTO);

            if (updated) {
                resp.sendRedirect(req.getContextPath() + "/complaint");
            } else {
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                        "Failed to update complaint. Please try again.");
            }

        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid complaint ID format.");
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "An unexpected error occurred while updating the complaint.");
        }
    }
}