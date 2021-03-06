<%--
  Created by IntelliJ IDEA.
  User: hklinux
  Date: 20. 11. 12.
  Time: 오후 5:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Musicblog home</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
    <style>
        a.no_linkdecoration {
            text-decoration:none;
            color:black;
        }
        a:hover {
            color:blue;
        }
    </style>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="/">MusicBlog</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/">Home</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/artist">Artist</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/member">Member</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/album">Album</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/track">Track</a>
            </li>
        </ul>
    </div>
</nav>
<div class="container" style="padding:50px;">
    <c:if test="${not empty user}">${user}님 환영합니다.&nbsp;&nbsp;
    <a class="no_linkdecoration" href="/logout">로그아웃</a></c:if>
    <c:if test="${empty user}"><a class="no_linkdecoration" href="/login">로그인</a>&nbsp;<a class="no_linkdecoration" href="/join">회원가입</a><br/>
        <c:if test="${loginError}">일치하는 회원이 없습니다.</c:if></c:if>
    <h3>Menu</h3>
    <ul>
        <li><a class='no_linkdecoration' href="${pageContext.request.contextPath}/artist">Artist List</a></li>
        <li><a class='no_linkdecoration' href="${pageContext.request.contextPath}/member">Member List</a></li>
        <li><a class='no_linkdecoration' href="${pageContext.request.contextPath}/album">Album List</a></li>
        <li><a class='no_linkdecoration' href="${pageContext.request.contextPath}/track">Track List</a></li>
    </ul>
    홈이다.
</div>
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
</body>
</html>
