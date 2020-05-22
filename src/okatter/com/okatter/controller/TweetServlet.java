package okatter.com.okatter.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import okatter.com.okatter.DataSourceManager;
import okatter.com.okatter.dao.TweetDao;
import okatter.com.okatter.dto.TweetDto;

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
