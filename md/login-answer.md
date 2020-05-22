# 解答例

## UserDto.java

```java
public class UserDto {

	private int id;

	private String email;

	private String password;

	private String name;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
```

## UserDao.java

```java
public class UserDao {

	protected Connection connection;

	public UserDao(Connection connection) {
		this.connection = connection;
	}

	public UserDto login(String email, String password) throws SQLException {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT");
		sb.append(" ID");
		sb.append(" ,EMAIL");
		sb.append(" ,NAME");
		sb.append(" FROM USERS");
		sb.append(" WHERE EMAIL = ?");
		sb.append(" AND PASSWORD = ?");

		try(PreparedStatement ps = connection.prepareStatement(sb.toString())){
			ps.setString(1, email);
			ps.setString(2, password);

			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				UserDto user = new UserDto();
				user.setId(rs.getInt("ID"));
				user.setEmail(rs.getString("EMAIL"));
				user.setName(rs.getString("NAME"));
				return user;
			}
			return null;
		}
	}
}
```

## LoginServlet.java

```java
@WebServlet("/login")
public class LoginServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/login.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession(true);
		String email = req.getParameter("email");
		String password = req.getParameter("password");

		try(Connection connection = DataSourceManager.getConnection()){
			UserDao dao = new UserDao(connection);
			UserDto user = dao.login(email, password);

			session.setAttribute("user", user);
			if(user == null) {
				resp.sendRedirect(req.getContentType() + "/login");
				return;
			}

			resp.sendRedirect(req.getContentType() + "/");
		} catch (SQLException | NamingException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}
}
```

## header.jsp

```java
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
```

## loginj.jsp

```java
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
```

## LogoutServlet.java

```java
@WebServlet("/logout")
public class LogoutServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		session.removeAttribute("user");
		session.invalidate();

		resp.sendRedirect(req.getContextPath() + "/");
	}
}
```

## list-tweet.jsp

```java
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
```