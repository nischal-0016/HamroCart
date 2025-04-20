<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Product Details</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f7f7f7;
        }

        .navbar {
            background-color: #4CAF50;
            padding: 15px 20px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .navbar a {
            color: white;
            text-decoration: none;
            margin-left: 20px;
            font-weight: bold;
        }

        .navbar a:hover {
            text-decoration: underline;
        }

        .container {
            padding: 20px;
        }

        h2 {
            color: #333;
        }

        img {
            max-width: 300px;
            height: auto;
            border-radius: 8px;
            margin-top: 10px;
        }

        p {
            font-size: 16px;
            margin: 5px 0;
        }

        form {
            margin-top: 15px;
        }

        input[type="number"] {
            width: 60px;
            padding: 6px;
            margin: 5px 0;
            font-size: 14px;
        }

        input[type="submit"] {
            background-color: #4CAF50;
            color: white;
            padding: 10px 15px;
            border: none;
            cursor: pointer;
            font-size: 14px;
            margin-left: 10px;
        }

        input[type="submit"]:hover {
            background-color: #45a049;
        }

        .back-link {
            display: inline-block;
            margin-top: 20px;
            text-decoration: none;
            color: #4CAF50;
            font-weight: bold;
        }

        .back-link:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
    <div class="navbar">
        <div>
            <a href="ProductServlet">HamroCart</a>
        </div>
        <div>
            <a href="CartServlet">Cart</a>
            <c:choose>
                <c:when test="${not empty sessionScope.username}">
                    <a href="ProfileServlet">Profile</a>
                    <a href="LogoutServlet">Logout</a>
                </c:when>
                <c:otherwise>
                    <a href="pages/login.jsp">Login</a>
                </c:otherwise>
            </c:choose>
        </div>
    </div>

    <div class="container">
        <h2>${product.name}</h2>
        <img src="${product.imageUrl}" alt="${product.name}">
        <p><strong>Description:</strong> ${product.description}</p>
        <p><strong>Price:</strong> Rs.${product.price}</p>
        <p><strong>Category:</strong> ${product.category}</p>
        <p><strong>Stock:</strong> ${product.stock}</p>
        
        <form action="AddToCartServlet" method="post">
            <input type="hidden" name="productId" value="Rs.{product.id}">
            <label>Quantity:</label>
            <input type="number" name="quantity" value="1" min="1">
            <input type="submit" value="Add to Cart">
        </form>

        <a class="back-link" href="ProductServlet">← Back to Products</a>
    </div>
</body>
</html>
