<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Tech Products - HamroCart</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: #f5f7fa;
            margin: 0;
            padding: 0;
        }

        /* Navbar styling */
        .navbar {
            background: linear-gradient(to right, #4CAF50, #45a049);
            color: white;
            padding: 15px 30px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .navbar h1 {
            margin: 0;
            font-size: 24px;
        }

        .navbar .nav-links a {
            color: white;
            text-decoration: none;
            margin-left: 20px;
            font-weight: bold;
            transition: color 0.3s ease;
        }

        .navbar .nav-links a:hover {
            text-decoration: underline;
        }

        h2 {
            padding: 20px 30px 10px;
            color: #333;
        }

        .product-list {
            display: flex;
            flex-wrap: wrap;
            padding: 0 30px 30px;
            justify-content: flex-start;
        }

        .product {
            background: white;
            border: 1px solid #ddd;
            border-radius: 8px;
            margin: 15px;
            padding: 15px;
            width: 220px;
            text-align: center;
            box-shadow: 0 4px 10px rgba(0,0,0,0.08);
            transition: transform 0.2s ease;
        }

        .product:hover {
            transform: translateY(-5px);
        }

        .product img {
            max-width: 100%;
            height: auto;
            margin-bottom: 10px;
        }

        .product h3 {
            font-size: 18px;
            margin: 10px 0 5px;
        }

        .product p {
            font-size: 14px;
            color: #555;
        }

        form {
            margin-top: 10px;
        }

        input[type="number"] {
            width: 60px;
            padding: 6px;
            margin: 5px 0;
            border-radius: 4px;
            border: 1px solid #ccc;
        }

        input[type="submit"] {
            background-color: #4CAF50;
            color: white;
            padding: 10px;
            width: 100%;
            border: none;
            border-radius: 6px;
            font-weight: bold;
            cursor: pointer;
        }

        input[type="submit"]:hover {
            background-color: #388e3c;
        }

        .footer-links {
            text-align: center;
            margin: 30px 0;
            font-size: 14px;
        }

        .footer-links a {
            margin: 0 10px;
            color: #4CAF50;
            text-decoration: none;
        }

        .footer-links a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>

    <!-- Navbar -->
    <div class="navbar">
        <h1>HamroCart</h1>
        <div class="nav-links">
            <a href="CartServlet">🛒 Cart</a>
            <a href="ProfileServlet">👤 Profile</a>
            <!-- Show Add Product link only for admin -->
            <c:if test="${sessionScope.role == 'admin'}">
                <a href="pages/addProduct.jsp">➕ Add Product</a>
            </c:if>
            <c:choose>
                <c:when test="${not empty sessionScope.user}">
                    <a href="LogoutServlet">🚪 Logout</a>
                </c:when>
                <c:otherwise>
                    <a href="pages/login.jsp">🔐 Login</a>
                </c:otherwise>
            </c:choose>
        </div>
    </div>

    <!-- Product Section -->
    <h2>Tech Products</h2>
    <div class="product-list">
        <c:forEach var="product" items="${products}">
            <div class="product">
                <img src="${product.imageUrl}" alt="${product.name}">
                <h3>${product.name}</h3>
                <p>${product.description}</p>
                <p><strong>Price:</strong> Rs <fmt:formatNumber value="${product.price}" type="number" maxFractionDigits="2" groupingUsed="true"/></p>
                <a href="ProductDetailsServlet?id=${product.id}">View Details</a>
                <form action="AddToCartServlet" method="post">
                    <input type="hidden" name="productId" value="${product.id}">
                    <input type="number" name="quantity" value="1" min="1">
                    <input type="submit" value="Add to Cart">
                </form>
            </div>
        </c:forEach>
    </div>

</body>
</html>