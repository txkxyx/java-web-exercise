package okatter.com.okatter.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

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

@WebServlet("/delete-tweet")
public class TweetDeleteServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.sendRedirect(req.getContextPath() + "/");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession(true);
		UserDto user = (UserDto) session.getAttribute("user");

		if(user == null) {
			resp.sendRedirect(req.getContextPath() + "/");
			return;
		}

		int id = Integer.parseInt(req.getParameter("id"));
		TweetDto dto = new TweetDto();
		dto.setId(id);

		try(Connection connection = DataSourceManager.getConnection()){
			TweetDao dao = new TweetDao(connection);

			dao.deleteTweet(dto);

			resp.sendRedirect(req.getContextPath() + "/");
		} catch (SQLException | NamingException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}
}
