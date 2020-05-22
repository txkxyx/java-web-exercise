package okatter.com.okatter;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DataSourceManager {

	public static Connection getConnection() throws SQLException, NamingException {
		Context context = new InitialContext();
		DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc/mysql");
		return dataSource.getConnection();
	}
}
