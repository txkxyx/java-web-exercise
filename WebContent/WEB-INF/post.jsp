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

	<form action="post-tweet" method="post">
		<div>
			<c:out value="${message}"/>
		</div>
		<div>
			<label>Title</label>
			<input type="text" name="title">
		</div>
		<div>
			<label>Body</label>
			<textarea rows="5" cols="30" name="body"></textarea>
		</div>
		<div>
			<button type="submit">Post</button>
			<a href="/okatter">戻る</a>
		</div>
	</form>

</body>
</html>