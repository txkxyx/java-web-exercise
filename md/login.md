# ログイン・ログアウト機能

ログイン画面を作成し、認証機能を追加します。
画面にヘッダーを追加し、ログイン画面に移動できるようにします。
さらにログインしている場合はヘッダーに表示するメニューを切り替えます。
最後にツイート一覧で表示しているツイート内、ログインしているユーザーが投稿したツイートをリンクに切り替えます。

- ログイン画面

![ログイン](./login-3.png)

- ツイート一覧（ログアウト時)

![ログイン](./login-2.png)

- ツイート一覧(ログイン時)

![ログイン](./login-4.png)

## UserDto.javaの作成

以下のようなフィールドを持つ、`UserDto.java`を作成し、それぞれsetterメソッドとgetterメソッドを作成してください。

| フィールド名 | 概要 | 型 | アクセス修飾子 |
| --- | --- | --- | --- |
| id | ユーザーID | int | private |
| email | メールアドレス | String | private |
| password | パスワード | String | private |
| name | 名前 | String | private |

## UserDao.javaの作成

以下のような、フィールド、コンストラクタ、メソッドを持つ`UserDao.java`を作成してください。
`login`メソッドは指定されたメールアドレスとパスワードが一致するユーザーを取得します。

- フィールド

| フィールド名 | 概要 | 型 | アクセス修飾子 |
| --- | --- | --- | --- |
| connection | コネクションクラス | java.sql.Connection | protected |

- コンストラクタ

| 引数 | 概要 | アクセス修飾子 |
| --- | --- | --- |
| java.sql.Connection | フィールドにConnectionクラスを設定する | public |

- メソッド

| メソッド名 | 概要 | 引数 | 戻り値 |
| --- | --- | --- | --- |
| login | 指定されたメールアドレスとパスワードが一致するユーザーを取得する | UserDto | String email, String password |

`login`メソッドの実装は以下を参考にしてください。

```java
	public UserDto login(String email, String password) throws SQLException {
		StringBuilder sb = new StringBuilder();
		// SQLを組み立てる
		sb.append("SELECT");
		sb.append(" ???");
		sb.append(" ,???");
		sb.append(" ,???");
		sb.append(" FROM USERS");
		sb.append(" WHERE ??? = ?");
		sb.append(" AND ??? = ?");

		try(PreparedStatement ps = connection.prepareStatement(sb.toString())){
			ps.setString(1, ???);
			ps.setString(2, ???);

			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				UserDto user = new UserDto();
				user.setId(rs.getInt("???"));
				user.setEmail(rs.getString("???"));
				user.setName(rs.getString("???"));
				return user;
			}
			return null;
		}
	}
```

## LoginServlet.javaの作成

以下の要件を満たす、認証機能を実装する`LoginServlet.java`サーブレットを作成します。
ログインに成功した場合は認証情報をセッションに格納します。

| URL | メソッド | フォワード・リダイレクト先 |
| --- | --- | --- |
| /login | GET | login.jsp |
| /login | POST | ・ログインに成功した場合 → ツイート一覧表示のURLにリダイレクト <br>・ログインに失敗した場合 → ログイン画面表示のURLにリダイレクト |

以下を参考に作成して下さい。

```java
@WebServlet("/???")
public class LoginServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/???.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession(true);
		String email = req.getParameter("???");
		String password = req.getParameter("???");

		try(Connection connection = DataSourceManager.getConnection()){
			UserDao dao = new UserDao(connection);
			UserDto user = ???;

			session.setAttribute("???", user);
			if(user == ???) {
				resp.sendRedirect(req.getContentType() + "/???");
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

## login.jspの作成

ログイン画面を作成します。「WEB-INF」フォルダ直下に`login.jsp`を作成します。
メールアドレスとパスワードを入力できるようにし、ログインボタンを押すと、「`/login`」にPOSTメソッドでリクエストを送信するようにします。

以下を参考に実装してくだしさい。

```
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>"プロジェクト名"</title>
</head>
<body>

	<form action="???" method="???">
		<div>
			<label>E-mail</label>
			<input type="???" name="???">
		</div>
		<div>
			<label>Password</label>
			<input type="???" name="???">
		</div>
		<div>
			<button type="???">Login</button>
		</div>
	</form>
</body>
</html>
```

ここまで作成できたら、サーバーを再起動し、`http://localhost:8080/プロジェクト名/login`にアクセスします。
メールアドレスに「user1@okatter.com」をパスワードに「user1」を入力し、ツイート一覧画面に遷移することを確認してください。

## header.jspの作成

画面に共通のヘッダーを追加します。ヘッダーにはタイトルとメニューを表示します。
メニューはログイン時とログアウト時にそれぞれ以下のように表示します。

- ログイン時
	- 「`ログインしているユーザー名`さんでログイン」を表示する。
	- 「つぶやく」リンクを表示する。
	- 「ログアウト」リンクを表示する。

- ログアウト時
	- 「ログイン」リンクを表示する

リンクの「ログイン」を押下するとログイン画面に遷移し、「ログアウト」を押下するとログアウトのURLにアクセスしログアウト処理を行い、ツイート一覧画面に遷移します。

まずは`header.jsp`を「WEB-INF」は以下に以下を参考に作成します。

```
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<header>
	<h1>Okatter</h1>
	<c:choose>
		<!-- ユーザーがログインしている場合 -->
		<c:when test="${??? == null}">
			<p><a href="???">Login</a></p>
		</c:when>
		<!-- ユーザーがログインアウトしている場合 -->
		<c:otherwise>
			<p><c:out value="${???.???}"/>さんでログイン</p>
			<p><a href="post-tweet">つぶやく</a></p>
			<p><a href="logout">ログアウト</a></p>
		</c:otherwise>
	</c:choose>
</header>
```

作成した`header.jsp`を`login.jsp`と`list-tweet.jsp`で読み込みます。以下の`login.jsp`の例をもとに作成してください。

- login.jsp

```
~省略~
<body>

	<%@ include file="./???.jsp" %>

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
		</div>
	</form>
</body>
</html>
```

## LogoutServlet.javaの作成

以下の要件を満たす、ログアウト機能を実装する`LogoutServlet.java`サーブレットを作成します。
セッション情報を破棄し、ツイート一覧画面にリダイレクトします。

| URL | メソッド | フォワード・リダイレクト先 |
| --- | --- | --- |
| /logout | GET | ツイート一覧のURL |

以下を参考に作成して下さい。

```java
@WebServlet("/???")
public class LogoutServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		session.???("???");
		session.invalidate();

		resp.sendRedirect(req.getContextPath() + "/");
	}
}
```

## list-tweet.jspの修正

ログインしているユーザーが投稿したツイートのタイトルをリンクで表示するように、`list-tweet.jsp`を修正します。
以下を参考に修正してください。

```
~省略~
<body>
	<%@ include file="???.jsp" %>
	<ul>
		<c:forEach items="${allTweet}" var="tweet">
			<li>
				<p>
					<c:choose>
						<c:when test="${??? == ???}">
							<a href="#"><c:out value="${tweet.title}"/></a>
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

ここまで実装出来たら、サーバーを再起動しログインとログアウトすることにより、ヘッダーの表示が変わること、ログインしたユーザーのツイートがリンクで表示されていることを確認してください。