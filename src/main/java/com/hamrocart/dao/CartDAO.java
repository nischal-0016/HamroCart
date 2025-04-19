package com.hamrocart.dao;

import com.hamrocart.model.Cart;
import com.hamrocart.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CartDAO {
    public void addToCart(Cart cart) throws SQLException {
        String sql = "INSERT INTO cart (user_id, product_id, quantity) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, cart.getUserId());
            stmt.setInt(2, cart.getProductId());
            stmt.setInt(3, cart.getQuantity());
            stmt.executeUpdate();
        }
    }

    public List<Cart> getCartByUserId(int userId) throws SQLException {
        List<Cart> cartItems = new ArrayList<>();
        String sql = "SELECT c.*, p.name, p.price, p.image_url FROM cart c JOIN products p ON c.product_id = p.id WHERE c.user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Cart cart = new Cart(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getInt("product_id"),
                        rs.getInt("quantity"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getString("image_url")
                    );
                    cartItems.add(cart);
                }
            }
        }
        return cartItems;
    }
}