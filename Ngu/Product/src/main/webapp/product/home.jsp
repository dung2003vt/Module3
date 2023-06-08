<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 29/5/2023
  Time: 8:53 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Title</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
            crossorigin="anonymous"></script>
</head>
<body>
<div class="container">
    <h1 style="text-align: center">List Products</h1>
    <div class="row">
        <div class="col-lg-2">
            <a class="btn btn-primary" style="text-decoration: none; color: white" href="/products?action=create">Create
                new product</a>
        </div>
        <div class="col-lg-7">
            <a class="btn btn-primary" style="text-decoration: none; color: white" href="/category">Home Category</a>
        </div>
        <div class="col-lg-3">
            <form action="/products?action=search" method="post">
                <input type="text" name="search">
                <button class="btn btn-info" type="submit">Search</button>
            </form>
        </div>
    </div>
    <table class="table table-striped">
        <thead>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Price</th>
            <th>Date</th>
            <th>Category</th>
            <th colspan="2" style="text-align: center">Action</th>
        </tr>
        </thead>

        <tbody>
        <c:forEach items="${products}" var="s">
            <tr>
                <td>${s.id}</td>
                <td>${s.name}</td>
                <td>${s.price}</td>
                <td>${s.date}</td>
                <td>${s.category.name}</td>
                <td>
                    <a class="btn btn-warning" href="/products?action=update&&id=${s.id}">Update</a>
                </td>
                <td>
                    <button class="btn btn-danger" onclick="deleteS(${s.id})">Delete</button>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
<script>
    function deleteS(id) {
        if (confirm("Are you sure?")) {
            window.location.href = `http://localhost:8080/products?action=delete&&id=` + id
        }
    }
</script>
</html>