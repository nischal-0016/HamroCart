<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Products</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 20px;
        }
        h2 {
            color: #333;
        }
        .product-list {
            display: flex;
            flex-wrap: wrap;
        }
        .product {
            border: 1px solid #ddd;
            margin: 10px;
            padding: 10px;
            width: 200px;
            text-align: center;
        }
        .product img {
            max-width: 100%;
            height: auto;
        }
        form {
            margin-top: 10px;
        }
        input[type="number"] {
            width: 50px;
            padding: 5px;
            margin: 5px 0;
        }
        input[type="submit"] {
            background-color: #4CAF50;
            color: white;
            padding: 10px;
            border: none;
            cursor: pointer;
        }
        input[type="submit"]:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>
    <h2>Tech Products</h2>
    <div class="product-list">
        <c:forEach var="product" items="${products}">
            <div class="product">
                <img src="${product.imageUrl}" alt="${product.name}">
                <h3>${product.name}</h3>
                <p>${product.description}</p>
                <p>Price: $${product.price}</p>
                <a href="ProductDetailsServlet?id=${product.id}">View Details</a>
                <form action="AddToCartServlet" method="post">
                    <input type="hidden" name="productId" value="${product.id}">
                    <input type="number" name="quantity" value="1" min="1">
                    <input type="submit" value="Add to Cart">
                </form>
            </div>
        </c:forEach>
    </div>
    <a href="CartServlet">View Cart</a> | <a href="ProfileServlet">Profile</a> | <a href="LogoutServlet">Logout</a>
</body>
</html>