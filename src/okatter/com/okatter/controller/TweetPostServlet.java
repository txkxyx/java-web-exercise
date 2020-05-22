package okatter.com.okatter.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import okatter.com.okatter.DataSourceManager;
import okatter.com.okatter.dao.TweetDao;
import okatter.com.okatter.dto.TweetDto;
import okatter.com.okatter.dto.UserDto;

@WebServlet("/post-tweet")
public class TweetPostServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession(true);
		UserDto user = (UserDto) session.getAttribute("user");

		if(user == null) {
			resp.sendRedirect(req.getContextPath() + "/");
			return;
		}
		String  message = req.getParameter("message");
		req.setAttribute("message", message);
		req.getRequestDispatcher("/WEB-INF/post.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		HttpSession session = req.getSession(true);
		UserDto user = (UserDto) session.getAttribute("user");

		if(user == null) {
			resp.sendRedirect(req.getContextPath() + "/");
			return;
		}

		req.setCharacterEncoding("UTF-8");
		String title = req.getParameter("title");
		String body = req.getParameter("body");
		TweetDto dto = new TweetDto();
		dto.setTitle(title);
		dto.setBody(body);
		dto.setUserId(user.getId());
		dto.setUpdateAt(LocalDateTime.now());

		try(Connection connection = DataSourceManager.getConnection()){
			TweetDao dao = new TweetDao(connection);
			dao.insertTweet(dto);

			resp.sendRedirect(req.getContextPath() + "/");
		} catch (SQLException | NamingException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}
}
