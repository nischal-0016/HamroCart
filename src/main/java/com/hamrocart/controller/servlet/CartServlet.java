package com.hamrocart.controller.servlet;

import com.hamrocart.dao.CartDAO;
import com.hamrocart.model.Cart;
import com.hamrocart.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/CartServlet")
public class CartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect("pages/login.jsp");
            return;
        }

        CartDAO cartDAO = new CartDAO();
        try {
            List<Cart> cartItems = cartDAO.getCartByUserId(user.getId());
            request.setAttribute("cartItems", cartItems);
            request.getRequestDispatcher("pages/cart.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("pages/error.jsp");
        }
    }
}