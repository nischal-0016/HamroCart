package com.hamrocart.dao;

import com.hamrocart.model.Order;
import com.hamrocart.model.OrderDetail;
import com.hamrocart.model.User;
import com.hamrocart.util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {
    
    private String generateOrderNumber(int orderId, String userRole) {
        if ("admin".equals(userRole)) {
            return String.format("ADM-%06d", orderId);
        } else {
            return String.format("ORD-%06d", orderId);
        }
    }

    public List<Order> findByUserId(int userId) {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM orders WHERE user_id = ? ORDER BY order_date DESC";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Order order = new Order();
                order.setId(rs.getInt("id"));
                order.setUserId(rs.getInt("user_id"));
                order.setOrderDate(rs.getTimestamp("order_date"));
                order.setTotalAmount(rs.getDouble("total_amount"));
                order.setStatus(rs.getString("status"));
                order.setOrderDetails(findOrderDetails(order.getId()));
                order.setShippingAddress(rs.getString("shipping_address"));
                order.setUser(findUserById(order.getUserId()));
                order.setOrderNumber(generateOrderNumber(order.getId(), "user"));
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }
    
    public List<Order> findAll() {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM orders ORDER BY order_date DESC";
        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            System.out.println("Retrieving all orders...");
            int count = 0;
            while (rs.next()) {
                count++;
                Order order = new Order();
                order.setId(rs.getInt("id"));
                order.setUserId(rs.getInt("user_id"));
                order.setOrderDate(rs.getTimestamp("order_date"));
                order.setTotalAmount(rs.getDouble("total_amount"));
                order.setStatus(rs.getString("status"));
                order.setOrderDetails(findOrderDetails(order.getId()));
                order.setShippingAddress(rs.getString("shipping_address"));
                order.setOrderNumber(generateOrderNumber(order.getId(), "admin"));
                
                System.out.println("Processing order #" + order.getId() + 
                                 " for user #" + order.getUserId() + 
                                 " with status: " + order.getStatus());
                
                User user = findUserById(order.getUserId());
                order.setUser(user);
                if (user == null) {
                    System.out.println("Warning: No user found for order #" + order.getId());
                }
                
                orders.add(order);
            }
            System.out.println("Total orders retrieved: " + count);
        } catch (SQLException e) {
            System.out.println("Error retrieving orders: " + e.getMessage());
            e.printStackTrace();
        }
        return orders;
    }
    
    public List<OrderDetail> findOrderDetails(int orderId) {
        List<OrderDetail> details = new ArrayList<>();
        String query = "SELECT od.*, p.* FROM order_details od " +
                      "JOIN products p ON od.product_id = p.id " +
                      "WHERE od.order_id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, orderId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                OrderDetail detail = new OrderDetail();
                detail.setId(rs.getInt("od.id"));
                detail.setOrderId(rs.getInt("od.order_id"));
                detail.setProductId(rs.getInt("od.product_id"));
                detail.setQuantity(rs.getInt("od.quantity"));
                detail.setPrice(rs.getDouble("od.price"));
                
                // Set product details
                detail.setProduct(new com.hamrocart.model.Product());
                detail.getProduct().setId(rs.getInt("p.id"));
                detail.getProduct().setName(rs.getString("p.name"));
                detail.getProduct().setDescription(rs.getString("p.description"));
                detail.getProduct().setPrice(rs.getDouble("p.price"));
                detail.getProduct().setStock(rs.getInt("p.stock"));
                detail.getProduct().setImageUrl(rs.getString("p.image_url"));
                
                details.add(detail);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return details;
    }
    
    public int createOrder(Order order, List<OrderDetail> details) {
        String orderQuery = "INSERT INTO orders (user_id, order_date, total_amount, status, shipping_address) VALUES (?, ?, ?, ?, ?)";
        String detailQuery = "INSERT INTO order_details (order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseUtil.getConnection()) {
            conn.setAutoCommit(false);
            try {
                // Insert order
                int orderId;
                try (PreparedStatement pstmt = conn.prepareStatement(orderQuery, Statement.RETURN_GENERATED_KEYS)) {
                    pstmt.setInt(1, order.getUserId());
                    pstmt.setTimestamp(2, new Timestamp(order.getOrderDate().getTime()));
                    pstmt.setDouble(3, order.getTotalAmount());
                    pstmt.setString(4, order.getStatus());
                    pstmt.setString(5, order.getShippingAddress());
                    pstmt.executeUpdate();
                    
                    ResultSet rs = pstmt.getGeneratedKeys();
                    if (rs.next()) {
                        orderId = rs.getInt(1);
                    } else {
                        throw new SQLException("Failed to get generated order ID");
                    }
                }
                
                // Insert order details
                try (PreparedStatement pstmt = conn.prepareStatement(detailQuery)) {
                    for (OrderDetail detail : details) {
                        pstmt.setInt(1, orderId);
                        pstmt.setInt(2, detail.getProductId());
                        pstmt.setInt(3, detail.getQuantity());
                        pstmt.setDouble(4, detail.getPrice());
                        pstmt.addBatch();
                    }
                    pstmt.executeBatch();
                }
                
                conn.commit();
                return orderId;
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
    
    public boolean updateOrderStatus(int orderId, String status) {
        String query = "UPDATE orders SET status = ? WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, status);
            pstmt.setInt(2, orderId);
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    private User findUserById(int userId) {
        String query = "SELECT * FROM users WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setRole(rs.getString("role"));
                System.out.println("Found user: " + user.getUsername() + " for order with userId: " + userId);
                return user;
            } else {
                System.out.println("No user found for userId: " + userId);
            }
        } catch (SQLException e) {
            System.out.println("Error finding user for userId: " + userId);
            e.printStackTrace();
        }
        return null;
    }
} 