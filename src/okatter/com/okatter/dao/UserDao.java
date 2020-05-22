package okatter.com.okatter.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import okatter.com.okatter.dto.UserDto;

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
