package inventory;

/** ***************** REVISION HISTORY ****************************************************
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
 * version 1.2
 * Edited 02/26/18 by Elizabeth Ruzich, cleaned up some of the code, cleaned
 * up duplicate code. Moved the database to Amazon AWS for SQL Server, made the connections
 * so everyone could access, including the professor.
 *
 **version 1.2 
 *Edited 02/26/2018 by Sumit Malhotra, revised and edited methods for tableQuantityByCategory()
 *and tableQuantityByTotal(), getAllActiveItems()
 *
 **************************************************************************************** */

import java.sql.*;
import java.util.*;
import java.sql.Date;

public class InventoryDatabase {

    private Connection conn = null;
    private Statement stmt = null;
    //private ResultSet rs;

    public void init() {
        try {
            if (conn == null) {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                conn = DriverManager.getConnection("jdbc:sqlserver://cmis495.cwhfglfbbsfj.us-east-2.rds.amazonaws.com:1433;database=InventoryApp;user=Caladain;password=rQPphzZloFcDl4tCFW68;");
            } else {
                getMyConnection();
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        }
   }
 
 /*    public void init(){
      try
      {
       if(conn == null)
       {
       Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
      conn=DriverManager.getConnection(
              "jdbc:sqlserver://localhost\\sqlexpress2017;databaseName=InventoryApp","sqldev", "Passw0rd"
               );
        }
      else
      {
              getMyConnection();
      }
      }
      catch(ClassNotFoundException | SQLException e){
         System.out.println(e);
       }
  } */
    public Connection getMyConnection() {

        return conn;
    }

    // 1-23-18 fix sql code updated function to use prepared statements -Sharon
    public ResultSet getItemByID(int ID) throws SQLException // updated function to use prepared statements and replaced the * with actual columns 1-23-18 Sharon
    {
        init();
        PreparedStatement prepareStatement = null;
        String sql;
        ResultSet rs = null;
        sql = "SELECT InventoryID, ItemName, QTY, convert(varchar,ExpireDate,101) as ExpireDate, notes, category FROM Inventory WHERE InventoryID = ?";
        try {
            prepareStatement = conn.prepareStatement(sql);
            prepareStatement.setInt(1, ID);
            rs = prepareStatement.executeQuery();
            return rs;

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
        } catch (SQLException se) {
        } catch (Exception e) {
        } finally {

        }
        return null;
    }

    //works 2-25/18 sw
    public ResultSet getAllActiveItems() throws SQLException // updated function to use prepared statements and replaced the * with actual columns 1-23-18 Sharon
    {
        try {
            init();
            String sql;
            Statement stmt = conn.createStatement();
            sql = "SELECT InventoryID, ItemName, QTY, convert(varchar,ExpireDate,101) as ExpireDate, notes, category FROM Inventory WHERE Isdeleted = 0";
            ResultSet rs = stmt.executeQuery(sql);
            return rs;
        } catch (SQLException se) {

        } catch (Exception e) {
        } finally {

        }
        return null;
    }

    public ResultSet getAllActiveProduceItems() throws SQLException // updated function to use prepared statements and replaced the * with actual columns 1-23-18 Sharon
    {
        init();
        Statement stmt = conn.createStatement();
        String sql;
        sql = "SELECT InventoryID, ItemName, QTY, convert(varchar,ExpireDate,101) as ExpireDate, notes, category FROM Inventory WHERE Isdeleted = 0 and category = 'Produce'";
        try {
            ResultSet rs = stmt.executeQuery(sql);
            return rs;

        } catch (SQLException se) {
        } catch (Exception e) {
        } finally {

        }
        return null;
    }

    public ResultSet getAllActiveMeatItems() throws SQLException // updated function to use prepared statements and replaced the * with actual columns 1-23-18 Sharon
    {
        init();
        Statement stmt = conn.createStatement();
        String sql;
        sql = "SELECT InventoryID, ItemName, QTY, convert(varchar,ExpireDate,101) as ExpireDate, notes, category FROM Inventory WHERE Isdeleted = 0 and category = 'Meat'";
        try {
            ResultSet rs = stmt.executeQuery(sql);
            conn.commit();
            return rs;
        } catch (SQLException se) {
        } catch (Exception e) {
        } finally {

        }
        return null;
    }

    public ResultSet getAllActiveDairyItems() throws SQLException // updated function to use prepared statements and replaced the * with actual columns 1-23-18 Sharon
    {
        init();
        Statement stmt = conn.createStatement();
        String sql;
        sql = "SELECT InventoryID, ItemName, QTY, convert(varchar,ExpireDate,101) as ExpireDate, notes, category FROM Inventory WHERE Isdeleted = 0 and category = 'Dairy'";
        try {
            ResultSet rs = stmt.executeQuery(sql);
            return rs;
        } catch (SQLException se) {
        } catch (Exception e) {
        } finally {
        }
        return null;
    }

    public ResultSet getAllActiveNonParishablesItems() throws SQLException // updated function to use prepared statements and replaced the * with actual columns 1-23-18 Sharon
    {
        init();
        Statement stmt = conn.createStatement();
        String sql;
        sql = "SELECT InventoryID, ItemName, QTY, convert(varchar,ExpireDate,101) as ExpireDate, notes, category FROM Inventory WHERE Isdeleted = 0 and category like 'non%'";
        try {
            ResultSet rs = stmt.executeQuery(sql);
            return rs;
        } catch (SQLException se) {
        } catch (Exception e) {
        } finally {

        }
        return null;
    }

    public ResultSet getAllActiveLiquidItems() throws SQLException // updated function to use prepared statements and replaced the * with actual columns 1-23-18 Sharon
    {
        init();
        Statement stmt = conn.createStatement();
        String sql;
        sql = "SELECT InventoryID, ItemName, QTY, convert(varchar,ExpireDate,101) as ExpireDate, notes, category FROM Inventory WHERE Isdeleted = 0 and category = 'Liquids'";
        try {
            ResultSet rs = stmt.executeQuery(sql);
            return rs;
        } catch (SQLException se) {
        } catch (Exception e) {
        } finally {

        }
        return null;
    }

    public ResultSet getItemsByExpireDate(Date currentdate) throws SQLException // updated function to use prepared statements and replaced the * with actual columns 1-23-18 Sharon
    {
        init();
        Statement stmt = conn.createStatement();
        String sql;
        ResultSet rs = null;
        sql = "SELECT InventoryID, ItemName, QTY, convert(varchar,ExpireDate,101) as ExpireDate, notes, category FROM Inventory WHERE ExpireDate <= DATEADD(day,3,GETDate()) and isDeleted = 0";
        try {
           
            //prepareStatement.setDate(1, currentdate);
            rs = stmt.executeQuery(sql);           
            return rs;
        } catch (SQLException se) {
        } catch (Exception e) {
        } finally {
            
        }
        return null;
    }

    // 1-23-18 fix sql code updated function to use prepared statements -Sharon - works sw 2/25/18
    public void insertItem(String iname, int qty, String expire, String category, String notes) {
        try {
            init();
            String sql;
            sql = "INSERT INTO Inventory(ItemName, QTY, ExpireDate, DateEntered, LastUpdated, IsDeleted, notes, category) values (?,?,?,GetDate(),Getdate(),0,?,?)";
            try (PreparedStatement prepStmt = conn.prepareStatement(sql) // Array array = conn.createArrayOf("VARCHAR", data.toArray());
            ) {
                prepStmt.setString(1, iname);
                prepStmt.setInt(2, qty);
                prepStmt.setString(3, expire);
                prepStmt.setString(4, notes);
                prepStmt.setString(5, category);
                prepStmt.executeUpdate();
            }

        } catch (SQLException se) {
        } catch (Exception e) {
        } finally {

        }
    }

    // 1-23-18 fix sql code updated function to use prepared statements -Sharon
    public void updateItemByID(String iname, int qty, String expire, String category, String notes, int ID) {
        try {
            init();
            PreparedStatement prepStmt;
            String sql = "UPDATE Inventory SET ItemName = ?, QTY =?, ExpireDate =?, LastUpdated = getdate(), IsDeleted = 0 , notes = ?, category = ? WHERE InventoryID = ?";
            prepStmt = conn.prepareStatement(sql);
            //Array array = conn.createArrayOf("VARCHAR", data.toArray());

            prepStmt.setString(1, iname);
            prepStmt.setInt(2, qty);
            prepStmt.setString(3, expire);
            prepStmt.setString(4, notes);
            prepStmt.setString(5, category);
            prepStmt.setInt(6, ID);
            prepStmt.executeQuery();

        } catch (SQLException se) {
        } catch (Exception e) {
        } finally {

        }
    }

    // 1-23-18  Updated SQL code and instituted prepared statements - Sharon
    public void deleteByID(int ID) {
        PreparedStatement prepStmt;
        try {
            init();
            String sql = "update inventory set isdeleted = 1 WHERE InventoryID = ?";
            prepStmt = conn.prepareStatement(sql);
            prepStmt.setInt(1, ID);
            prepStmt.executeQuery();

        } catch (SQLException se) {
        } catch (Exception e) {
        } finally {

        }
    }

    public ResultSet tableQuantityByCategory() //additions by Sumit 02/16/2018 -- This needs to be revised
    //revised by Sharon Walker 2//19/2018
    {
        init();

        ResultSet resultSet = null;
        String sql = "SELECT category, SUM(QTY) FROM Inventory WHERE IsDeleted = 0 GROUP BY category;";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            resultSet = stmt.executeQuery();
            return resultSet;
        } catch (SQLException e) {

            e.printStackTrace();
        }

        return resultSet;
    }
    
    
    public ResultSet tableQuantityByTotal() //additions by Sumit 02/16/2018 --This needs to be revised
    //revised by Sharon Walker 2//19/2018
    {
       init();      
  
        ResultSet resultSet = null;
        String sql = "SELECT SUM(QTY) AS total FROM Inventory WHERE IsDeleted = 0;";

        try 
        {
            PreparedStatement stmt = conn.prepareStatement(sql);
            resultSet = stmt.executeQuery();
            return resultSet;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e1) {
        		e1 .printStackTrace();
        } finally {
        	return resultSet;
        }
     
    }

}
