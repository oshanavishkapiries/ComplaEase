package edu.cms.controller;

import edu.cms.dto.UserDTO;
import edu.cms.dao.DAOFactory;
import edu.cms.repository.UserRepository;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/signup")
public class SignUpServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String confirmPassword = req.getParameter("confirmPassword");
        String role = req.getParameter("role");

        if (username == null || username.isEmpty() ||
                password == null || password.isEmpty() ||
                confirmPassword == null || confirmPassword.isEmpty() ||
                role == null || role.isEmpty()) {

            req.setAttribute("error", "All fields are required.");
            req.getRequestDispatcher("/WEB-INF/views/signup.jsp").forward(req, resp);
            return;
        }

        if (!password.equals(confirmPassword)) {
            req.setAttribute("error", "Passwords do not match.");
            req.getRequestDispatcher("/WEB-INF/views/signup.jsp").forward(req, resp);
            return;
        }

        UserDTO userDTO = UserDTO.builder()
                .username(username)
                .password(password)
                .role(role)
                .build();

        System.out.println(userDTO.getRole());
        System.out.println(userDTO.getUsername());
        System.out.println(userDTO.getPassword());

        DataSource dataSource = (DataSource) getServletContext().getAttribute("ds");
        DAOFactory daoFactory = DAOFactory.getInstance(dataSource);
        UserRepository userRepository = daoFactory.getUserRepository();

        Optional<UserDTO> existingUser = userRepository.findByUsernameAndPassword(userDTO.getUsername(),
                userDTO.getPassword());

        if (existingUser.isPresent()) {
            req.setAttribute("error", "User is already registered.");
            req.getRequestDispatcher("/WEB-INF/views/signup.jsp").forward(req, resp);
            return;
        }

        Optional<UserDTO> savedUser = userRepository.save(userDTO);

        RequestDispatcher rd;
        if (savedUser.isPresent()) {
            rd = req.getRequestDispatcher("WEB-INF/views/login.jsp");
            rd.forward(req, resp);
        } else {
            req.setAttribute("error", "Registration failed. Try a different username.");
            req.getRequestDispatcher("/WEB-INF/views/signup.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/views/signup.jsp");
        rd.forward(req, resp);
    }
}