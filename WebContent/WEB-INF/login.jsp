<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Okatter</title>
</head>
<body>

	<%@ include file="./header.jsp" %>

	<form action="login" method="post">
		<div>
			<c:out value="${message}"/>
		</div>
		<div>
			<label>E-mail</label>
			<input type="text" name="email">
		</div>
		<div>
			<label>Password</label>
			<input type="password" name="password">
		</div>
		<div>
			<button type="submit">Login</button>
			<a href="/okatter">戻る</a>
		</div>
	</form>
</body>
</html>