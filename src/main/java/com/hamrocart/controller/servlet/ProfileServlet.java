package com.hamrocart.controller.servlet;

import com.hamrocart.dao.UserDAO;
import com.hamrocart.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/ProfileServlet")
public class ProfileServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect("pages/login.jsp");
            return;
        }

        UserDAO userDAO = new UserDAO();
        try {
            User updatedUser = userDAO.getUserById(user.getId());
            request.setAttribute("user", updatedUser);
            request.getRequestDispatcher("pages/profile.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("pages/error.jsp");
        }
    }
}