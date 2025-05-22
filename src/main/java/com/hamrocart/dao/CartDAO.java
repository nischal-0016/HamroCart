package com.hamrocart.dao;

import com.hamrocart.model.Cart;
import com.hamrocart.model.Product;
import com.hamrocart.util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartDAO {
    
    public List<Cart> findByUserId(int userId) {
        List<Cart> cartItems = new ArrayList<>();
        String query = "SELECT c.*, p.* FROM cart c " +
                      "JOIN products p ON c.product_id = p.id " +
                      "WHERE c.user_id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Cart cart = new Cart();
                cart.setId(rs.getInt("c.id"));
                cart.setUserId(rs.getInt("c.user_id"));
                cart.setProductId(rs.getInt("c.product_id"));
                cart.setQuantity(rs.getInt("c.quantity"));
                
                Product product = new Product();
                product.setId(rs.getInt("p.id"));
                product.setName(rs.getString("p.name"));
                product.setDescription(rs.getString("p.description"));
                product.setPrice(rs.getDouble("p.price"));
                product.setStock(rs.getInt("p.stock"));
                product.setImageUrl(rs.getString("p.image_url"));
                
                cart.setProduct(product);
                cartItems.add(cart);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cartItems;
    }
    
    public boolean addToCart(Cart cart) {
        String query = "INSERT INTO cart (user_id, product_id, quantity) VALUES (?, ?, ?) " +
                      "ON DUPLICATE KEY UPDATE quantity = quantity + ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, cart.getUserId());
            pstmt.setInt(2, cart.getProductId());
            pstmt.setInt(3, cart.getQuantity());
            pstmt.setInt(4, cart.getQuantity());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean updateQuantity(int cartId, int quantity) {
        String query = "UPDATE cart SET quantity = ? WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, quantity);
            pstmt.setInt(2, cartId);
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean removeFromCart(int cartId) {
        String query = "DELETE FROM cart WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, cartId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean clearCart(int userId) {
        String query = "DELETE FROM cart WHERE user_id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, userId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
} 