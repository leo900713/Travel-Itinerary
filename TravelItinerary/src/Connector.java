import java.sql.*;

public class Connector {
	
	private static String url;
	private static String username;
	private static String password;

	public static Connection getConnection() throws SQLException {
		String server = "jdbc:mysql://140.119.19.73:9306/";
		String database = "MG01";
		String config= "?useUnicode=true&characterEncoding=utf8";
		url = server + database + config;
		username = "MG01"; 
		password = "42za67";
		return DriverManager.getConnection(url, username, password);
	}
	
}
