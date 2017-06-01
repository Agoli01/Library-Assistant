package database;

import java.sql.*;

import javafx.scene.control.Alert;

public class AddBookModel {

	Connection connection;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	
	public AddBookModel()
	{
		connection = SqliteConnection.connector();
		if(connection == null ) System.exit(1);
	}
	
	public boolean isDbConnected() {
		try {
			return !connection.isClosed();
		} 
		catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		
	}
	public boolean isInserted(String bid, String btitle, String bauthor, String bpublisher) throws SQLException
	{
		
		String query= "INSERT INTO Book values(?,?,?,?,?)";
		try{
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, bid);
			preparedStatement.setString(2, btitle);
			preparedStatement.setString(3, bauthor);
			preparedStatement.setString(4, bpublisher);
			preparedStatement.setBoolean(5, true);
			
			preparedStatement.execute();
			return true;
			//System.out.println(resultSet.getString(2)+" "+resultSet.getString(3)+" " +resultSet.getString(4));
			
		}catch(Exception e){
			//e.printStackTrace();
			return false;
		}finally{

		}
		
	}
	
	
	public ResultSet execQuery(String query)
	{
		ResultSet result=null;
		try{
			preparedStatement = connection.prepareStatement(query);
			result = preparedStatement.executeQuery();
			/*while(result.next())
			System.out.println(result.getString(1));*/
			return result;
		}catch(SQLException e){
				//e.printStackTrace();
				return null;
		}finally
		{}
	}
	public boolean execAction(String query)
	{
		try{
			preparedStatement = connection.prepareStatement(query);
			 preparedStatement.execute();
			return true;
		}catch(SQLException e){
				//e.printStackTrace();
				return false;
		}finally
		{}
	}
	
	public boolean isMemberAdded(String mid, String mname, String mmobile, String memail) throws SQLException
	{
		
		String query= "INSERT INTO Member values(?,?,?,?)";
		try{
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1,mid);
			preparedStatement.setString(2, mname);
			preparedStatement.setString(3, mmobile);
			preparedStatement.setString(4, memail);
			
			preparedStatement.execute();
			return true;
			//System.out.println(resultSet.getString(2)+" "+resultSet.getString(3)+" " +resultSet.getString(4));
			
		}catch(Exception e){
			//e.printStackTrace();
			return false;
		}finally{

		}
		
	}
	
	public void closeDatabase()
	{
		if(preparedStatement!=null)
			try {
				preparedStatement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		if(resultSet!=null)
			try {
				resultSet.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		if(connection!=null)
			try {
				connection.close();
				//System.out.println("Connection Closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
}
