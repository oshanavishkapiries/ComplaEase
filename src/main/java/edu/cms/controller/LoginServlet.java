package edu.cms.controller;

import edu.cms.dto.UserDTO;
import edu.cms.dao.DAOFactory;
import edu.cms.repository.UserRepository;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private UserRepository userRepository;

    @Override
    public void init() throws ServletException {
        ServletContext context = getServletContext();
        DataSource dataSource = (DataSource) context.getAttribute("ds");
        DAOFactory daoFactory = DAOFactory.getInstance(dataSource);
        userRepository = daoFactory.getUserRepository();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        Optional<UserDTO> userOptional = userRepository.findByUsernameAndPassword(username, password);

        if (userOptional.isPresent()) {
            UserDTO user = userOptional.get();
            HttpSession session = req.getSession();
            session.setAttribute("user", user.getUsername());
            session.setAttribute("role", user.getRole());
            session.setAttribute("userId", user.getId());
            session.setMaxInactiveInterval(30 * 60);

            if ("ADMIN".equalsIgnoreCase(user.getRole())) {
                resp.sendRedirect(req.getContextPath() + "/complaint");
            } else if ("EMPLOYEE".equalsIgnoreCase(user.getRole())) {
                resp.sendRedirect(req.getContextPath() + "/complaint");
            } else {
                req.setAttribute("errorMessage", "Unauthorized role.");
                req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
            }
        } else {
            req.setAttribute("errorMessage", "Invalid username or password.");
            resp.sendRedirect(req.getContextPath() + "/login");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
    }
}
