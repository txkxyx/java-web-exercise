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
import okatter.com.okatter.dao.UserDao;
import okatter.com.okatter.dto.UserDto;

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
