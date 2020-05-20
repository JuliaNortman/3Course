<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Low cost</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/res/styles/style.css">
</head>
<body>
<c:if test="${empty sessionScope.user}">
<div class="login-btns"><a href="${pageContext.request.contextPath}/views/login.jsp">Log in</a> | <a href="${pageContext.request.contextPath}/views/register.jsp">Registration</a></div>
</c:if>
<c:if test="${!empty sessionScope.user}">
<div class="login-btns" style="width: 7%; left: 90%">
    <a href="${pageContext.request.contextPath}/user?op=showCabinet">My cabinet</a>
    <button class="logout-btn" onclick="location.href = '${pageContext.request.contextPath}/account?op=logout'">Log out</button>
</div>
</c:if>
<div class="header">
    <a href="${pageContext.request.contextPath}/"><p class="header-title">Low cost airlines</p></a>
</div>



