package com.libman.database;
import java.sql.DriverManager;
import java.sql.Connection;

public class DatabaseConnection {
	public static Connection createConnection(){
		String url= "jdbc:postgresql://localhost:5432/librarymanagement";
		String username="libadmin";
		String password="libadmin";
		Connection connection = null;
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(url, username, password);
			System.out.println("Done");
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
		return connection;
	}
}
