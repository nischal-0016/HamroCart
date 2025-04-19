<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Cart</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 20px;
        }
        h2 {
            color: #333;
        }
        table {
            width: 100%;
            border-collapse: collapse;
        }
        th, td {
            padding: 10px;
            text-align: left;
            border: 1px solid #ddd;
        }
        th {
            background-color: #f2f2f2;
        }
    </style>
</head>
<body>
    <h2>Your Cart</h2>
    <table>
        <tr>
            <th>Product ID</th>
            <th>Quantity</th>
        </tr>
        <c:forEach var="cartItem" items="${cartItems}">
            <tr>
                <td>${cartItem.productId}</td>
                <td>${cartItem.quantity}</td>
            </tr>
        </c:forEach>
    </table>
    <a href="ProductServlet">Continue Shopping</a>
</body>
</html>