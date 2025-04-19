<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>User Profile</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 20px;
        }
        h2 {
            color: #333;
        }
    </style>
</head>
<body>
    <h2>User Profile</h2>
    <p>Username: ${user.username}</p>
    <p>Email: ${user.email}</p>
    <p>Full Name: ${user.fullName}</p>
    <a href="ProductServlet">Back to Products</a> | <a href="LogoutServlet">Logout</a>
</body>
</html>