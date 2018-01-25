package inventoryapp;

/**
 * version 1.1
 *  edited by Sharon Walker 1/23/2018
 *  removed create table functions and renamed functions to be clearer on their purpose
 * 
 */

import java.sql.*;
import java.util.*;

public class InventoryDatabase 
{
    //JDBC driver name and database URL
    private static final String JDBC_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static final String DB_URL = "jdbc:sqlserver://localhost\\MDAC50008w10\\sqlexpress2017;databaseName=InventoryApp";  // This will change for everyone's computer - SW
    
    //Database credentials
    private static final String USER = "sqlDev";
    private static final String PASS = "Passw0rd";
    
    //query 
    private Connection conn = null;
    private Statement stmt = null;
    //private ResultSet rs;
    
	
    /*	InventoryDatabase()
    {
    try
    {
    Class.forName(JDBC_DRIVER);
    conn = DriverManager.getConnection(DB_URL, USER, PASS);
    stmt = conn.createStatement();
    conn.setAutoCommit(false);
    }
    catch(SQLException | ClassNotFoundException se)
    {
    }
    finally
    {
    
    }
    }*/
public void init(){
      try{
       
       Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
      conn=DriverManager.getConnection(
              "jdbc:sqlserver://localhost\\MDAC50008w10\\sqlexpress2017;databaseName=InventoryApp","sqldev", "Passw0rd"
               );
      }
      catch(ClassNotFoundException | SQLException e){
         System.out.println(e);
       }
   }
 
  public Connection getMyConnection(){
      
       return conn;
   }
  
     // 1-23-18 fix sql code updated function to use prepared statements -Sharon  
    public ResultSet getItemByID(int ID) throws SQLException // updated function to use prepared statements and replaced the * with actual columns 1-23-18 Sharon
    {
        PreparedStatement prepareStatement = null;
     	String sql;
     	ResultSet rs = null;
      	sql = "SELECT InventoryID, ItemName, QTY, ExpireDate, DateEntered, LastUpdated, IsDeleted FROM InventoryApp WHERE InventoryID = ?";
        try
        {
            prepareStatement = conn.prepareStatement(sql);
            prepareStatement.setInt(1,ID);
	    rs = prepareStatement.executeQuery(sql);
    	    conn.commit();
            
           // while (rs.next())
           // {
           //     int inventoryID = rs.getInt("InventoryID");
            //    String itemName = rs.getString("ItemName");
            //    int qty = rs.getInt("QTY");
            //    java.util.Date expDate = rs.getDate("ExpireDate");
            //    java.util.Date dateEntered = rs.getDate("DateEntered");
            //    java.util.Date lastUpdated = rs.getDate("LastUpdated");
             //   boolean isDeleted = rs.getBoolean("IsDeleted");
                
           // }
           return rs;
    	}
    	catch(SQLException se)
        {
    	}
    	catch(Exception e)
	{
    	}
    	finally
     	{
    		if (prepareStatement != null)
                {
		prepareStatement.close();
		}
      	}
        return rs;
    }   
    
     public ResultSet getAllActiveItems() throws SQLException // updated function to use prepared statements and replaced the * with actual columns 1-23-18 Sharon
    { 
        PreparedStatement prepareStatement = null;
     	String sql;
     	ResultSet rs = null;
        sql = "SELECT InventoryID, ItemName, QTY, ExpireDate, DateEntered, LastUpdated, IsDeleted FROM InventoryApp WHERE Isdeleted = 0";
          try 
          {
            prepareStatement = conn.prepareStatement(sql);           
	    rs = prepareStatement.executeQuery(sql);
    	    conn.commit();          
          return rs;
    	}
    	catch(SQLException se)
        {
    	}
    	catch(Exception e)
	{
    	}
    	finally
     	{
    		
      	}
       return rs;
    }   
    
     // 1-23-18 fix sql code updated function to use prepared statements -Sharon
    public void insertItem(ArrayList<String> data)
    { try
        {
     	PreparedStatement prepStmt;
      	 String sql = "INSERT INTO InventoryApp(ItemName, QTY, ExpireDate, EnteredDate, LastUpdated, IsDeleted) values (?,?,?,?,?,?)";
	 prepStmt = conn.prepareStatement(sql);
         Array array = conn.createArrayOf("VARCHAR", data.toArray());
            	 
         prepStmt.setArray(1,array);
         prepStmt.setArray(2,array);
         prepStmt.setArray(3,array);
         prepStmt.setArray(4,array);
         prepStmt.setArray(5,array);
         prepStmt.setArray(6,array);
         prepStmt.executeUpdate(sql);
         conn.commit();
         }
         catch(SQLException se)
         {
         }
         catch(Exception e)
         {
         }
         finally
         {
        	 
         }
    }
    
    // 1-23-18 fix sql code updated function to use prepared statements -Sharon
    public void updateItemByID(ArrayList<String> data, int ID)
    {
        try
        {
       
         PreparedStatement prepStmt;
      	 String sql = "UPDATE InventoryApp SET ItemName = ?, QTY =?, ExpireDate =?, EnteredDate = ?, LastUpdated = ?, IsDeleted = ? WHERE InventoryID = ?";
	 prepStmt = conn.prepareStatement(sql);
         Array array = conn.createArrayOf("VARCHAR", data.toArray());
            	 
         prepStmt.setArray(1,array);
         prepStmt.setArray(2,array);
         prepStmt.setArray(3,array);
         prepStmt.setArray(4,array);
         prepStmt.setArray(5,array);
         prepStmt.setArray(6,array);
         prepStmt.setInt(7, ID);
         prepStmt.executeUpdate(sql);
         conn.commit();
         }
         catch(SQLException se)
         {
         }
         catch(Exception e)
         {
         }
         finally
         {
        	 
         }
    }
    
    // 1-23-18  Updated SQL code and instituted preparred statements - Sharon
    public void deleteByID(int ID ) 
    {PreparedStatement prepStmt;
        try
        {           
     	String sql = "DELETE FROM InventoryApp WHERE InventoryID = ?";
         prepStmt = conn.prepareStatement(sql);
         prepStmt.setInt(1, ID);
         prepStmt.executeUpdate(sql);
         conn.commit();
            prepStmt.executeUpdate(sql);
            conn.commit();
    	}
    	catch(SQLException se)
    	{
       	}
    	catch(Exception e)
    	{
    	}
    	finally
       	{
           
    	}
    }
    
    private void closeConnection()
    {
        try
    	{
    	    conn.close();
    	}
    	catch(SQLException se)
      	{
        }
    	catch(Exception e)
    	{
    	}
    }
}
