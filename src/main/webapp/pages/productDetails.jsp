<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Product Details</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 20px;
        }
        h2 {
            color: #333;
        }
        img {
            max-width: 300px;
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
    <h2>${product.name}</h2>
    <img src="${product.imageUrl}" alt="${product.name}">
    <p>${product.description}</p>
    <p>Price: $${product.price}</p>
    <p>Category: ${product.category}</p>
    <p>Stock: ${product.stock}</p>
    <form action="AddToCartServlet" method="post">
        <input type="hidden" name="productId" value="${product.id}">
        <input type="number" name="quantity" value="1" min="1">
        <input type="submit" value="Add to Cart">
    </form>
    <a href="ProductServlet">Back to Products</a>
</body>
</html>