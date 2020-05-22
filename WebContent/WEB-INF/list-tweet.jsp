<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Okatter</title>
</head>
<body>

	<%@ include file="header.jsp" %>
	<ul>
		<c:forEach items="${allTweet}" var="tweet">
			<li>
				<p>
					<c:choose>
						<c:when test="${user.id == tweet.userId}">
							<a href="edit-tweet?id=${tweet.id}"><c:out value="${tweet.title}"/></a>
						</c:when>
						<c:otherwise>
							<c:out value="${tweet.title}"/>
						</c:otherwise>
					</c:choose>
					 / <c:out value="${tweet.postName}"/> / <c:out value="${tweet.updateAt}"/>
				</p>
				<p><c:out value="${tweet.body}" /></p>
			</li>
		</c:forEach>
	</ul>
</body>
</html>