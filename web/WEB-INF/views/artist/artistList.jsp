<%--
  Created by IntelliJ IDEA.
  User: hklinux
  Date: 20. 11. 12.
  Time: 오후 7:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>Artist list</title>
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
                    <a class="nav-link" href="/">Home</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/artist">Artist</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/">Member</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/">Album</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/">Track</a>
                </li>
            </ul>
        </div>
    </nav>
    <div class="container" style="padding:50px;">
        <h3>Artist List</h3>
        <c:if test="${not empty artistList}">
            <ul>
                <c:forEach var="artist" items="${artistList}">
                    <li><a class="no_linkdecoration" href="artist/detail?target=${fn:replace(artist, " ", "-")}">
                            ${artist}</a></li>
                </c:forEach>
            </ul>
        </c:if>
        <c:if test="${empty artistList}">
            등록된 아티스트가 없습니다.
        </c:if>
    </div>
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
</body>
</html>
