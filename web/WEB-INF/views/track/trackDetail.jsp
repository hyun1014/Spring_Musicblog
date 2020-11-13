<%--
  Created by IntelliJ IDEA.
  User: hklinux
  Date: 20. 11. 14.
  Time: 오전 1:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>Track detail</title>
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
    <a class="navbar-brand" href="${pageContext.request.contextPath}/">MusicBlog</a>
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
    <h3>Track Detail</h3>
    <h4>${target.title}</h4>
    <div width="700px" style="float:left; margin:0px 50px 0px 0px;">
        <ul>
            <li>Artist -
                <a class="no_linkdecoration" href="${pageContext.request.contextPath}/artist/detail?target=${fn:replace(target.artist, " ", "-")}">
                    ${target.artist}
                </a>
            </li>
            <li>Album -
                <c:if test="${not empty target.album}">
                <a class="no_linkdecoration" href="${pageContext.request.contextPath}/album/detail?target=${fn:replace(target.album, " ", "-")}">
                    ${target.album}
                </a>
                </c:if>
                <c:if test="${empty target.album}">
                알 수 없음
                </c:if>
            </li>
        </ul>
        <c:if test="${not empty target.youtubeId}">
        <iframe width="640px" height="360px" src="https://www.youtube.com/embed/${target.youtubeId}" allowfullscreen></iframe>
        </c:if>
    </div>
    <div width="300px" style="float:right; word-wrap:break-word;">
        <c:if test="${not empty target.lyrics}">
        <pre style="overflow:auto; white-space:pre-wrap; width:300px;">${target.lyrics}</pre>
        </c:if>
        <c:if test="${empty target.lyrics}">
        <pre>가사 정보가 없습니다.</pre>
        </c:if>
    </div>
</div>
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
</body>
</html>