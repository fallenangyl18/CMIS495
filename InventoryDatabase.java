package Inventory;

/******************* REVISION HISTORY ****************************************************
 *  version 1.0
 *  Created by Sumit Malhotra 1/22/2018
 *  Class supports JDBC Drivers and connection elements to support connection to database
 *  Contains methods to create tables, add, update, delete, and select data in the database
 *
 * version 1.1
 *  edited by Sharon Walker 1/23/2018
 *  removed create table functions and renamed functions to be clearer on their purpose
 * 
 *  version 1.1
 *  edited 02/16/2018 Sumit Malhotra
 *  created tableQuantityByCategory(), tableQuanityByTotal() to support
 *  getting quantity totals from database, methods need to be revised
 *
 * edited by Sharon Walker 2/19/2018 
 * revised methods  tableQuantityByCategory(), tableQuanityByTotal()
 *
 *****************************************************************************************/

import java.sql.*;
import java.util.*;
import java.sql.Date;

public class InventoryDatabase 
{
	public static String getDbUrl() {
		return DB_URL;
	}

	public static String getJdbcDriver() {
		return JDBC_DRIVER;
	}
	
    public static String getUser() {
		return USER;
	}

	public static String getPass() {
		return PASS;
	}

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
      	sql = "SELECT InventoryID, ItemName, QTY, ExpireDate, DateEntered, LastUpdated, IsDeleted, , notes, category FROM InventoryApp WHERE InventoryID = ?";
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
        sql = "SELECT InventoryID, ItemName, QTY, ExpireDate, DateEntered, LastUpdated, IsDeleted, notes, category FROM InventoryApp WHERE Isdeleted = 0";
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
     
     public ResultSet getAllActiveProduceItems() throws SQLException // updated function to use prepared statements and replaced the * with actual columns 1-23-18 Sharon
     { 
         PreparedStatement prepareStatement = null;
      	String sql;
      	ResultSet rs = null;
         sql = "SELECT InventoryID, ItemName, QTY, ExpireDate, DateEntered, LastUpdated, IsDeleted, notes, category FROM InventoryApp WHERE Isdeleted = 0 and category = 'Produce'";
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
     
     public ResultSet getAllActiveMeatItems() throws SQLException // updated function to use prepared statements and replaced the * with actual columns 1-23-18 Sharon
     { 
         PreparedStatement prepareStatement = null;
      	String sql;
      	ResultSet rs = null;
         sql = "SELECT InventoryID, ItemName, QTY, ExpireDate, DateEntered, LastUpdated, IsDeleted, notes, category FROM InventoryApp WHERE Isdeleted = 0 and category = 'Meat'";
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
     
     public ResultSet getAllActiveDairyItems() throws SQLException // updated function to use prepared statements and replaced the * with actual columns 1-23-18 Sharon
     { 
         PreparedStatement prepareStatement = null;
      	String sql;
      	ResultSet rs = null;
         sql = "SELECT InventoryID, ItemName, QTY, ExpireDate, DateEntered, LastUpdated, IsDeleted, notes, category FROM InventoryApp WHERE Isdeleted = 0 and category = 'Dairy'";
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
     
     public ResultSet getAllActiveNonParishablesItems() throws SQLException // updated function to use prepared statements and replaced the * with actual columns 1-23-18 Sharon
     { 
         PreparedStatement prepareStatement = null;
      	String sql;
      	ResultSet rs = null;
         sql = "SELECT InventoryID, ItemName, QTY, ExpireDate, DateEntered, LastUpdated, IsDeleted, notes, category FROM InventoryApp WHERE Isdeleted = 0 and category like 'non%'";
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
     
     public ResultSet getAllActiveLiquidItems() throws SQLException // updated function to use prepared statements and replaced the * with actual columns 1-23-18 Sharon
     { 
         PreparedStatement prepareStatement = null;
      	String sql;
      	ResultSet rs = null;
         sql = "SELECT InventoryID, ItemName, QTY, ExpireDate, DateEntered, LastUpdated, IsDeleted, notes, category FROM InventoryApp WHERE Isdeleted = 0 and category = 'Liquids'";
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
     
     public ResultSet getItemsByExpireDate(Date currentdate ) throws SQLException // updated function to use prepared statements and replaced the * with actual columns 1-23-18 Sharon
     {
         PreparedStatement prepareStatement = null;
      	String sql;
      	ResultSet rs = null;
       	sql = "SELECT InventoryID, ItemName, QTY, ExpireDate, DateEntered, LastUpdated, notes, category FROM InventoryApp WHERE ExpireDate >= DATEADD(day,3,?) and isDeleted = 0";
         try
         {
             prepareStatement = conn.prepareStatement(sql);
             prepareStatement.setDate(1, currentdate);             
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
     
 
     // 1-23-18 fix sql code updated function to use prepared statements -Sharon
    public void insertItem(ArrayList<Object> data)
    { try
        {
     	PreparedStatement prepStmt;
      	 String sql = "INSERT INTO InventoryApp(ItemName, QTY, ExpireDate, EnteredDate, LastUpdated, IsDeleted, notes, category) values (?,?,?,?,?,?,?,?)";
	 prepStmt = conn.prepareStatement(sql);
         Array array = conn.createArrayOf("VARCHAR", data.toArray());
            	 
         prepStmt.setArray(1,array);
         prepStmt.setArray(2,array);
         prepStmt.setArray(3,array);
         prepStmt.setArray(4,array);
         prepStmt.setArray(5,array);
         prepStmt.setArray(6,array);
         prepStmt.setArray(7,array);
         prepStmt.setArray(8,array);
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
    public void updateItemByID(ArrayList<Object> data, int ID)
    {
        try
        {
       
         PreparedStatement prepStmt;
      	 String sql = "UPDATE InventoryApp SET ItemName = ?, QTY =?, ExpireDate =?, EnteredDate = ?, LastUpdated = ?, IsDeleted = ? , notes = ?, category = ? WHERE InventoryID = ?";
	 prepStmt = conn.prepareStatement(sql);
         Array array = conn.createArrayOf("VARCHAR", data.toArray());
            	 
         prepStmt.setArray(1,array);
         prepStmt.setArray(2,array);
         prepStmt.setArray(3,array);
         prepStmt.setArray(4,array);
         prepStmt.setArray(5,array);
         prepStmt.setArray(6,array);
         prepStmt.setArray(7,array);
         prepStmt.setArray(8,array);
         prepStmt.setInt(9, ID);
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
    
    // 1-23-18  Updated SQL code and instituted prepared statements - Sharon
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
	
	
	
    public ResultSet tableQuantityByCategory() //additions by Sumit 02/16/2018 -- This needs to be revised
	      //revised by Sharon Walker 2//19/2018
    {
    	    PreparedStatement preparedStatement = null;
    	    ResultSet resultSet = null;
    	    String sql = "SELECT Category , SUM(QTY) FROM InventoryApp where isDeleted = 0 GROUP BY Category;";
    	    
    	    try 
    	    {
    	        preparedStatement = conn.prepareStatement(sql);
	        resultSet = preparedStatement.executeQuery(sql);
    	    	conn.commit();
    	    	preparedStatement.close();
            } 
    	    catch (SQLException e) 
    	    {
    	    	
     		e.printStackTrace();
             }
    	  
    	    return resultSet;
    }
   
    public ResultSet tableQuantityByTotal() //additions by Sumit 02/16/2018 --This needs to be revised
	    //revised by Sharon Walker 2//19/2018
    {
    	  PreparedStatement preparedStatement = null;
    	  ResultSet resultSet = null;
    	  String sql = "SELECT SUM(QTY) FROM InventoryApp where isDeleted = 0;";
    	 
    	   try
    	   {
               preparedStatement = conn.prepareStatement(sql);
    	       resultSet = preparedStatement.executeQuery(sql);
    	       conn.commit();
    	       preparedStatement.close();
    	   }
    	   catch (SQLException e )
    	   {
               e.printStackTrace();
    	   }
    	   catch(Exception e1)
    	   {
    		   
    	   }
    	   finally
    	   {
    		  
    	   }
    	   return rs;
    }


}
