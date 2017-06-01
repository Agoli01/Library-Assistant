package database;

import java.sql.Connection;
import java.sql.DriverManager;

public class SqliteConnection {

	public static Connection connector()
	{
		 try
		 {
			 Class.forName("org.sqlite.JDBC");
			 Connection conn = DriverManager.getConnection("jdbc:sqlite:BookDataBase.sqlite");
			 conn.setAutoCommit(true);
			 return conn;
		 }
		 catch(Exception e)
		 {
			 System.out.println(e);
			 return null;
		 }
	}
}
