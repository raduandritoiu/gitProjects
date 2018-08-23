package connections;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MyConnection {
	private String connectionDriver = "com.mysql.jdbc.Driver";
	private String dbUrl 			= "jdbc:mysql://localhost:3306/";
	private String dbName 			= "flights";
	private String userName 		= "radu"; 
	private String password 		= "radu";
	
	private Connection conn 		= null;
	
	
	public MyConnection() {
		System.out.println("MyConnection class created");
	}
	
	
	public void setConnection() {
		try {
			Class.forName(connectionDriver).newInstance();
			conn = DriverManager.getConnection(dbUrl + dbName, userName, password);
			System.out.println("Connected to the database");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void closeConnection() {
		try {
			conn.close();
			System.out.println("Disconnected from database");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void testSelect() {
		String query = "Select * FROM test_table";

		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			int x =3;
			int y = 4;
			x = y + 3;
			
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
}
