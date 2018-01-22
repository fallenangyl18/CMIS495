package database;

/**
 * version 1.0
 */

//Step 1. Import required packages
import java.sql.*;
import java.util.*;

public class InventoryDatabase 
{
    //JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    static final String DB_URL = "";
    
    //Database credentials
    static final String USER = "sqlDev";
    static final String PASS = "Passw0rd";
    
    //query 
    private Connection conn = null;
    private Statement stmt = null;
    private ResultSet rs;
    
	
	InventoryDatabase()
	{
		try
		{
	            Class.forName("JDBC_DRIVER");
	      	    conn = DriverManager.getConnection(DB_URL, USER, PASS);
	    	    stmt = conn.createStatement();
	    	    conn.setAutoCommit(false);
		}
		catch(SQLException se)
		{
		    se.printStackTrace();
		}
		catch(Exception e)
		{
		    e.printStackTrace();
		}
		finally
		{
			
		}
	}
	
    public void createTables()
    {
     	String sql = "";
     	try
     	{
    	    sql = "CREATE TABLE InventoryApp(InventoryID BIGINT, itemName VARCHAR(50), "
    				+ "QTY decimal(8,2),"
    				+ "ExpireDate DATETIME, DateEntered TIMESTAMP, LastUpdated TIMESTAMP," +
    			    	"isDeleted BIT" + ");";
            stmt.executeUpdate(sql);
    	    stmt.executeUpdate(sql);
    	    conn.commit();
    		
    	 }
    	 catch(SQLException se)
    	 {
    	     se.printStackTrace();
    	 }
    	 catch(Exception e )
    	 {
             e.printStackTrace();
         }
    	 finally
     	 {
     		
         }
    }
    
    public ResultSet select(int ID)
    {
     	String sql = "";
     	ResultSet rs = null;
      	sql = "SELECT * FROM InventoryApp WHERE InventoryID = '" + ID + "';";
        try
        {
	    rs = stmt.executeQuery(sql);
    	    conn.commit();
    	}
    	catch(SQLException se)
        {
	    se.printStackTrace();
    	}
    	catch(Exception e)
	{
            e.printStackTrace();
    	}
    	finally
     	{
    		
      	}
        return rs;
    }
 
    public void create(ArrayList<String> table)
    {
      	String sql = "CREATE TABLE " + table.get(0) + "( " + table.get(1) + ",";
    	try
      	{
            for( int i = 2; i < table.size(); i++ )
    	    {
                if( i == 2 )
                {
    	            sql += table.get(i);
    		}
    		else
                {
    		    sql += "," + table.get(i);
    		}
             }
    	     sql += ");";
             stmt.executeUpdate(sql);
    	     conn.commit();
       	}
        catch(SQLException se)
        {
   	    se.printStackTrace();
    	}
    	catch(Exception e)
    	{
            e.printStackTrace();
     	}
     	finally
     	{
    		
	}
    }
    
    
    public void insert(ArrayList<String> data, String sqldb)
    {
     	
      	 String sql = "INSERT INTO InventoryApp"  + "(" + data.get(1) + ")";
         try
         {
             stmt.executeUpdate(sqldb);
             conn.commit();
         }
         catch(SQLException se)
         {
             se.printStackTrace();
         }
         catch(Exception e)
         {
             e.printStackTrace();
         }
         finally
         {
        	 
         }
    }
    
    public void update(String column, int ID)
    {
        String sql = "UPDATE InventoryApp SET" + column + "WHERE InventoryID = \'" + ID + "\';";
    	try
        {
            stmt.executeUpdate(sql);
            conn.commit();
        }
    	catch(SQLException se)
    	{
            se.printStackTrace();
       	}
    	catch(Exception e)
    	{
            e.printStackTrace();	
        }
    	finally
       	{
    	
    	}
    }
    
    public void delete( String table, int ID )
    {
     	String sql = "DELETE FROM InventoryApp" + " WHERE InventoryID = \'" + ID + "\';";
        try
        {
            stmt.executeUpdate(sql);
            conn.commit();
    	}
    	catch(SQLException se)
    	{
    	    se.printStackTrace();
       	}
    	catch(Exception e)
    	{
    	    e.printStackTrace();	
    	}
    	finally
       	{
    	
    	}
    }
    
    public void closeDB()
    {
        try
    	{
    	    stmt.close();
    	}
    	catch(SQLException se)
      	{
        }
    	catch(Exception e)
    	{
    	}
    }
}