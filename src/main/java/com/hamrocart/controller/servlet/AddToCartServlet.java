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

@WebServlet("/AddToCartServlet")
public class AddToCartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect("pages/login.jsp");
            return;
        }

        int productId = Integer.parseInt(request.getParameter("productId"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        Cart cart = new Cart(0, user.getId(), productId, quantity);
        CartDAO cartDAO = new CartDAO();
        try {
            cartDAO.addToCart(cart);
            response.sendRedirect("CartServlet");
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("pages/error.jsp");
        }
    }
}