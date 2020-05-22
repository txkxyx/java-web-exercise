<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<header>
	<h1>Okatter</h1>
	<c:choose>
		<c:when test="${user == null}">
			<p><a href="login">Login</a></p>
		</c:when>
		<c:otherwise>
			<p><c:out value="${user.name}"/>さんでログイン</p>
			<p><a href="post-tweet">つぶやく</a></p>
			<p><a href="logout">ログアウト</a></p>
		</c:otherwise>
	</c:choose>
</header>