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

	<form action="edit-tweet" method="post">
		<div>
			<c:out value="${message}"/>
		</div>
		<div>
			<label>Title</label>
			<input type="text" name="title" value="${tweet.title}">
		</div>
		<div>
			<label>Body</label>
			<textarea rows="5" cols="30" name="body"><c:out value="${tweet.body}"/></textarea>
		</div>
		<div>
			<input type="hidden" name="id" value="${tweet.id}">
			<button type="submit">Edit</button>
			<button type="submit" form="delete">Delete</button>
			<a href="/okatter">戻る</a>
		</div>
	</form>
	<form action="delete-tweet" method="post" id="delete">
		<input type="hidden" name="id" value="${tweet.id}">
	</form>

</body>
</html>