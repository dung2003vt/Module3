<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 25/5/2023
  Time: 11:38 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<button><a href="Creat.jsp"> Creat</a></button>
<c:forEach var="i" items="${key}">
    <h5>${i}</h5>
</c:forEach>
</body>
</html>
