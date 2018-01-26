package ntdky.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
	private static final String DATABASE = "localhost:3306/ntdky";
	private static final String USER_NAME = "root";
	private static final String PASSWORD = "root";	

	private static Connection connection;

	public static void open() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://" + DATABASE + "?useSSL=false", USER_NAME, PASSWORD);
			System.out.println("Konekcija sa bazom uspesna.");
		} catch (Exception ex) {
			System.out.println("Greska pri konekciji sa bazom.");
		}
	}

	public static Connection getConnection() {
		return connection;
	}

	public static void close() {
		try {
			connection.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
}
