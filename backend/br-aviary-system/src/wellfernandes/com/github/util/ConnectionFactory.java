package wellfernandes.com.github.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// Mysql connection
public class ConnectionFactory {

	// jdbc connection
	private static final String DRIVER = "com.mysql.jdbc.Driver";
	private static final String URL = "jdbc:mysql://localhost:3306/arduinojava?useSSL=false";
	private static final String USER = "root";
	private static final String PASS = "password";

	public static Connection getConnection() {

		try {
			Class.forName(DRIVER);
			return DriverManager.getConnection(URL, USER, PASS);
		} catch (ClassNotFoundException | SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public static void closeConnection(Connection con) {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void closeConnection(Connection con, PreparedStatement stmt) {
		if (stmt != null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		closeConnection(con);
	}

	public static void closeConnection(Connection con, PreparedStatement stmt, ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		closeConnection(con, stmt);
	}
}
