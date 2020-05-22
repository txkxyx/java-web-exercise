# 解答例

## TweetDto.java

```java
public class TweetDto {

	private int id;

	private String title;

	private String body;

	private int userId;

	private String postName;

	private LocalDateTime updateAt;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getPostName() {
		return postName;
	}

	public void setPostName(String postName) {
		this.postName = postName;
	}

	public LocalDateTime getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(LocalDateTime updateAt) {
		this.updateAt = updateAt;
	}


}
```

## TweetDao.java

```java
public class TweetDao {

	protected Connection connection;

	public TweetDao(Connection connection) {
		this.connection = connection;
	}

	public List<TweetDto> selectAll() throws SQLException{
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT");
		sb.append(" TWEET.ID");
		sb.append(" ,TITLE");
		sb.append(" ,BODY");
		sb.append(" ,USER_ID");
		sb.append(" ,USERS.NAME AS POST_NAME");
		sb.append(" ,UPDATE_AT");
		sb.append(" FROM TWEET");
		sb.append(" INNER JOIN USERS");
		sb.append(" ON USER_ID = USERS.ID");
		sb.append(" ORDER BY TWEET.UPDATE_AT DESC");

		List<TweetDto> allTweet = new ArrayList<>();

		try(PreparedStatement ps = connection.prepareStatement(sb.toString())){
			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
				TweetDto dto = new TweetDto();
				dto.setId(rs.getInt("ID"));
				dto.setTitle(rs.getString("TITLE"));
				dto.setBody(rs.getString("BODY"));
				dto.setUserId(rs.getInt("USER_ID"));
				dto.setPostName(rs.getString("POST_NAME"));
				dto.setUpdateAt(rs.getTimestamp("UPDATE_AT").toLocalDateTime());
				allTweet.add(dto);
			}
			return allTweet;
		}
	}
}
```

## TweetServlet.java

```
@WebServlet("/")
public class TweetServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try(Connection con = DataSourceManager.getConnection()){
			TweetDao dao = new TweetDao(con);
			List<TweetDto> allTweet = dao.selectAll();

			req.setAttribute("allTweet", allTweet);

			req.getRequestDispatcher("/WEB-INF/list-tweet.jsp").forward(req, resp);
		} catch (SQLException | NamingException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
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
	<ul>
		<c:forEach items="${allTweet}" var="tweet">
			<li>
				<p>
					<c:out value="${tweet.title}"/> / <c:out value="${tweet.postName}"/> / <c:out value="${tweet.updateAt}"/>
				</p>
				<p>
					<c:out value="${tweet.body}" />
				</p>
			</li>
		</c:forEach>
	</ul>
</body>
</html>
```