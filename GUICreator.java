
package Inventory;

/***************************** REVISION HISTORY ****************************************
 * 
 * Created by Beth on 1/16/2018.
 * 
 *
 *
 *
 * VERSION 1.1
 * Updated by Sumit Malhotra on 02/15/2018
 * removed combobox and button for sort
 * update add method to support InventoryTable.java
 * update edit method to support InventoryTable.java and to support dialog box for id
 * update delete method to support InventoryTable.java and dialog box for deleting entry
 * adding methods and gui elements for search 
 *
 * Additions on add/edit/update/delete/search/sort by Sumit on 02/15/2018
 *
 *VERSION 1.3
 *Update by Sumit Malhotra on 02/24/2018
 *removed 1.1 methods and pop ups for editing and deleting 
 *updated editing method to support editing by selection and commiting edit with button text changes,
 *and code to commit changes to the data table
 *updated deleting method to support deleting by selected row and call to InventoryTable()
 *added code for Database startup entries to populate database on startup
 *added code for supporting database class and result set
 *****************************************************************************************/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.JTable;

public class GUICreator extends JFrame implements ActionListener
{
    //Declaration of variables for options display text area (left side)
    //And inventory display (right side)
    //Declaration of Combo Box
    private JButton addButton;
    private JButton editButton;
    private JButton removeButton;
    private JTextField itemNameTextField;
    private JTextField quantityTextField;
    private JTextField expirationDateTextField;
    private JTextField notesTextField;
    private JComboBox itemCategory;
    
    private JButton searchBtn;
    private JTextField searchTF;
    private JLabel searchLbl;
    DataPanel data = new DataPanel();
    //InventoryDatabase database = new InventoryDatabase();
    ResultSet rs = null;
    InventoryTable jTable;
    //creating an enumeration for the sort types
    private enum sortEntries { NAME, CATEGORY, EXPIRATION };

    //Create the GUI
    public GUICreator()
    {
        //setting GUI base information
        super("Pantry Inventory");
        setLayout(new BorderLayout());
        setBackground(Color.white);
        setSize(900, 650);

        //Creating a "container" for the panels to sit in
        JPanel containerPanel = new JPanel();
        //setting the layout and border information
        containerPanel.setLayout(new BoxLayout(containerPanel, BoxLayout.X_AXIS));
        add(containerPanel, BorderLayout.CENTER);
        containerPanel.setBorder(new EmptyBorder(10, 15, 10, 15));
        //set default to close when exited
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Setting the JComboBox options for the dropdown
        //Feel free to add more as we need
        
    /************************ DATABASE STARTUP ENTRIES ******************************
    	
    	Vector columnNames = new Vector();
    	columnNames.addElement("Item Name");
    	columnNames.addElement("ID");
    	columnNames.addElement("Quantity");
    	columnNames.addElement("Expiration Date");
    	columnNames.addElement("Category");
    	columnNames.addElement("Notes");
    	
    Vector<Vector> initialTableRows = new Vector<Vector>();
    	
    	try
    	{
        rs = db.getAllActiveItems();
    	
        	while( rs.next() )
       	{
    	        Vector<Object> rowData = new Vector<Object>();
           	rowData.addElement(rs.getString("ItemName"));
          	rowData.addElement(rs.getInt("InventoryID"));
         	rowData.addElement(rs.getInt("QTY"));
         	rowData.addElement((String)rs.getString("ExpireDate"));
         	rowData.addElement(rs.getString("category"));
         	rowData.addElement(rs.getString("notes"));
         	initialTableRows.addElement(rowData);
         	
         	if(rs.isLast())
         	{
         	    int newID = rs.getInt("InventoryID");
         	}
         	add(newID++);
         	
    	}
    	}
    	catch(SQLException e)
    	{
    		e.printStackTrace();
    	}
    	finally
    	{
    		
    	}

    	
        jTable = new InventoryTable( initialTableRows , columnNames );   

        ********************************* END DATABASE STARTUP ENTRIES ***************************************/
        
       Vector<Vector> initialTableRows = new Vector<Vector>();
    	   Vector columnNames = new Vector();
    	   columnNames.addElement("Item Name");
    	   columnNames.addElement("ID");
    	   columnNames.addElement("Quantity");
    	   columnNames.addElement("Expiration Date");
    	   columnNames.addElement("Category");
    	   columnNames.addElement("Notes");
    
        Vector<Object> rowData = new Vector<Object>();
       	rowData.addElement("Food");
      	rowData.addElement((int)26457);
     	rowData.addElement((int)80);
     	rowData.addElement("02-25-2018");
     	rowData.addElement("Meat");
     	rowData.addElement("Pantry");
     	
        Vector<Object> rowData1 = new Vector<Object>();
       	rowData1.addElement("carrots");
      	rowData1.addElement((int)56724);
     	rowData1.addElement((int)37);
     	rowData1.addElement("10-25-2018");
     	rowData1.addElement("Vegetables");
     	rowData1.addElement("Pantry");
     	
        Vector<Object> rowData2 = new Vector<Object>();
       	rowData2.addElement("Chicken");
      	rowData2.addElement((int)263427);
     	rowData2.addElement((int)31);
     	rowData2.addElement("11-25-2018");
     	rowData2.addElement("Meat");
     	rowData2.addElement("Pantry");
       
     	Vector<Object> rowData3 = new Vector<Object>();
       	rowData3.addElement("apples");
      	rowData3.addElement((int)90227);
     	rowData3.addElement((int)20);
     	rowData3.addElement("01-25-2018");
     	rowData3.addElement("Fruits");
     	rowData3.addElement("Pantry");
     	
     	
     	initialTableRows.addElement(rowData);
      	initialTableRows.addElement(rowData1);
        initialTableRows.addElement(rowData2);
     	initialTableRows.addElement(rowData3);
    	
    	    jTable = new InventoryTable( initialTableRows , columnNames );
    	
        //Creating a JLabel for the menu
        JLabel menuLabel = new JLabel("Menu: ");
        //creating the buttons and adding their action listeners
        addButton = new JButton("Add");
        addButton.setToolTipText("Add items to database.");
        addButton.addActionListener(this);
        editButton = new JButton("Edit");
        editButton.setToolTipText("Edit existing items in the database.");
        editButton.addActionListener(this);
        removeButton = new JButton("Remove");
        removeButton.setToolTipText("Remove items from the databse.");
        removeButton.addActionListener(this);
        
        searchTF = new JTextField("Clear text and press search to reset");
        searchLbl = new JLabel("Search");
        //Creating text field objects
        itemNameTextField = new JTextField();
        itemNameTextField.setMaximumSize(new Dimension(Integer.MAX_VALUE, itemNameTextField.getPreferredSize().height));
        itemNameTextField.setEditable(true);
        JLabel itemNameLabel = new JLabel("Item Name: ");

        notesTextField = new JTextField();
        notesTextField.setMaximumSize(new Dimension(Integer.MAX_VALUE, itemNameTextField.getPreferredSize().height));
        notesTextField.setEditable(true);
        JLabel notesLabel = new JLabel("Additional Notes: ");

        //quantity text field objects
        quantityTextField = new JTextField();
        quantityTextField.setMaximumSize(new Dimension(Integer.MAX_VALUE, quantityTextField.getPreferredSize().height));
        quantityTextField.setEditable(true);
        JLabel quantityNameLabel = new JLabel("Quantity: ");

        //creating expiration date text field objects
        expirationDateTextField = new JTextField();
        expirationDateTextField.setMaximumSize(new Dimension(Integer.MAX_VALUE, quantityTextField.getPreferredSize().height));
        expirationDateTextField.setEditable(true);
        expirationDateTextField.setText("MM-DD-YYYY");
        JLabel expirationDateLabel = new JLabel(" Expiration Date: ");


   

        String[] categories = {"Select Category", "Produce", "Meat", "Dairy", "Non-Perishable", "Liquids"};
        itemCategory = new JComboBox(categories);
        JLabel categoriesLabel = new JLabel("Item Category:   ");
        itemCategory.setSelectedIndex(0);

        //Putting the JTable into the Display Pane; Removed textArea, was no longer needed
        JScrollPane invDisplayPane = new JScrollPane(jTable);
        jTable.setFillsViewportHeight(true);
        invDisplayPane.getVerticalScrollBar().setValue(invDisplayPane.getVerticalScrollBar().getMinimum());
        invDisplayPane.getHorizontalScrollBar().setValue(invDisplayPane.getHorizontalScrollBar().getMinimum());

        //Creating a label for this display
        JLabel invDisplayLabel = new JLabel("Current Inventory: ");

        //Creating the panel to hold the left hand side of our options
        //To put in the overall container panel
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBorder(new EmptyBorder(20, 10, 20, 10));

        //creates a place to hold the menu label
        JPanel menuLabelHolder = new JPanel();
        menuLabelHolder.setLayout(new FlowLayout());
        menuLabelHolder.add(menuLabel);
        menuLabelHolder.setBorder(new EmptyBorder(10, 5, 15, 5));
        leftPanel.add(menuLabel);

        //creating a new JPanel for the item name text area and label
        JPanel itemTextInfoPanel = new JPanel();
        itemTextInfoPanel.setLayout(new BoxLayout(itemTextInfoPanel, BoxLayout.Y_AXIS));
        itemTextInfoPanel.add(itemNameLabel);
        itemTextInfoPanel.add(itemNameTextField);
        itemTextInfoPanel.setBorder(new EmptyBorder(10, 5, 10, 5));
        leftPanel.add(itemTextInfoPanel);

        //Panel to hold all of the quantity objects
        JPanel quantityTextInfoPanel = new JPanel();
        quantityTextInfoPanel.setLayout(new BoxLayout(quantityTextInfoPanel, BoxLayout.Y_AXIS));
        quantityTextInfoPanel.add(quantityNameLabel);
        quantityTextInfoPanel.add(quantityTextField);
        quantityTextInfoPanel.setBorder(new EmptyBorder(10, 5, 10, 5));
        leftPanel.add(quantityTextInfoPanel);

        //Panel to hold all of the Expiration Date objects
        JPanel expirationDateInfoPanel = new JPanel();
        expirationDateInfoPanel.setLayout(new BoxLayout(expirationDateInfoPanel, BoxLayout.Y_AXIS));
        expirationDateInfoPanel.add(expirationDateLabel);
        expirationDateInfoPanel.add(expirationDateTextField);
        expirationDateInfoPanel.setBorder(new EmptyBorder(10, 5, 10, 5));
        leftPanel.add(expirationDateInfoPanel);

        //Panel for comboBox for item category
        JPanel categoryPanel = new JPanel();
        categoryPanel.setLayout(new BoxLayout(categoryPanel, BoxLayout.X_AXIS));
        categoryPanel.setMaximumSize(new Dimension(350, 25));
        categoryPanel.add(categoriesLabel);
        categoryPanel.add(itemCategory);
        categoryPanel.setBorder(new EmptyBorder(20, 10, 20, 10));
        leftPanel.add(categoryPanel);

        JPanel notesTextPanel = new JPanel();
        notesTextPanel.setLayout(new BoxLayout(notesTextPanel, BoxLayout.Y_AXIS));
        notesTextPanel.add(notesLabel);
        notesTextPanel.add(notesTextField);
        notesTextPanel.setBorder(new EmptyBorder(10, 5, 10, 5));
        leftPanel.add(notesTextPanel);


        //JPanel for sort dropdown
        JPanel sortComboPanel = new JPanel();
        sortComboPanel.setLayout(new BoxLayout(sortComboPanel, BoxLayout.X_AXIS));
        sortComboPanel.setMaximumSize(new Dimension(250, 25));
        sortComboPanel.setBorder(new EmptyBorder(20, 10, 20, 10));
        leftPanel.add(sortComboPanel);

        //Creating a panel to sit inside of the left panel that holds the combobox
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBorder(new EmptyBorder(20, 10, 20, 10));
        buttonsPanel.add(addButton);
        buttonsPanel.add(editButton);
        buttonsPanel.add(removeButton);
        leftPanel.add(buttonsPanel);


        //Adding all of the components that are in the left panel into the overall container panel.
        containerPanel.add(leftPanel);

        //Creating the right panel that holds our inventory
        //display box
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.X_AXIS));
        rightPanel.setBorder(new EmptyBorder(20, 10, 20, 10));

       
        //Jpanel to hold the large display text area
        JPanel inventoryDisplayPanel = new JPanel();
        inventoryDisplayPanel.setLayout(new BoxLayout(inventoryDisplayPanel, BoxLayout.Y_AXIS));
        inventoryDisplayPanel.add(invDisplayLabel);
        inventoryDisplayPanel.add(invDisplayPane);
        JPanel searchPnl = new JPanel();
        
        searchBtn = new JButton("Search");
        searchBtn.addActionListener(this);
        searchPnl.add(searchLbl);
        searchTF.setPreferredSize(new Dimension(250,30));
        searchPnl.add(searchTF);
        searchPnl.add(searchBtn);
        
        inventoryDisplayPanel.add(searchPnl);
        rightPanel.add(inventoryDisplayPanel);
        containerPanel.add(rightPanel);
        
        add(data, BorderLayout.SOUTH);
    }

    private Timestamp createTimeStamp()
    {
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        return currentTime;
    }

    //Coding action listener
    @Override
    public void actionPerformed(ActionEvent e)
    {
    	
      if(e.getSource() == searchBtn) 
      {
    	     jTable.searchFilter(searchTF.getText());  		 
      }
      else if (e.getSource() == editButton) //edited by Sumit to support update row by selected row and commit function
      {
        
            //do something else
            // System.out.println("You hit the edit button.");
            Timestamp theTimeIs = createTimeStamp();
         	  //Dialog Box
            //String value = JOptionPane.showInputDialog("Enter the ID of the Item you would like to Update:");
            
            if(editButton.getText().equals("Edit")) //method updated by Sumit 02/24/2018
            {
                jTable.allowEditCells();
                editButton.setText("Commit");
            }
            else if(editButton.getText().equals("Commit"))
            {
            	  if(!jTable.getSelectionModel().isSelectionEmpty())
              { 
                    int value = JOptionPane.showConfirmDialog(this, "Are you done editing?");
                    if(value == JOptionPane.YES_OPTION)
                    {
              	       jTable.updateRowBySelection(theTimeIs);
              	       jTable.getSelectionModel().clearSelection();
                       editButton.setText("Edit");
                    }
               }
            }
           JOptionPane.showMessageDialog(null, "You have successfully edited item(s) in the database.");
          
      }
      else if (e.getSource() == removeButton)  //method updated by Sumit 02/24/2018
      {
          //elements to remove by
        	  Timestamp theTimeIs = createTimeStamp();
        	  try
        	  {
        		  
              int value = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete the selected item");
              if(value == JOptionPane.YES_OPTION)
              {
                 jTable.deleteRowBySelection();
              }
              else
              {
                  jTable.getSelectionModel().clearSelection();            	  
              }
            
        	  }
        	  catch(NullPointerException x)
        	  {
        		  JOptionPane.showMessageDialog(null, "There was an error deleting this entry. Please make sure all fields are filled.");
        	  }
     
        }
        else
        {
   
        try
        {
            //Getting the entries from the GUI's text fields, that the user entered.
            String itemEntry = itemNameTextField.getText();
            int quantityEntry = Integer.parseInt(quantityTextField.getText());
            String expirationEntry = expirationDateTextField.getText();
            String categoryEntry = itemCategory.getSelectedItem().toString();
            String notesEntry = notesTextField.getText();
            Object[] row = new Object[5];

            if ( e.getSource() == addButton )
            {
                if (itemEntry != null && quantityEntry != 0 && expirationEntry != null && !categoryEntry.equals("Select Category"))
                {
                	     Timestamp theTimeIs = createTimeStamp();
                	     //id generated from database, this will change 
                	     
                     jTable.addNewRow(itemEntry, 00000, quantityEntry, expirationEntry, categoryEntry, notesEntry, theTimeIs);
                     
                      //System.out.println("You added " + itemEntry + " on " + theTimeIs);
                      JOptionPane.showMessageDialog(null, "You have successfully added " + quantityEntry + " " + itemEntry + "(s) to the the database.");
                } else
                {
                    JOptionPane.showMessageDialog(null, "Please fill out all fields in this form.");
                }
            }   
            else
            {
            }
        }
        catch (NumberFormatException ex)
        {
            JOptionPane.showMessageDialog(null, "Error: Please make sure all fields are filled out before attempting to " +
                    "add an item to the database. Error message: " + ex.getMessage());
        }
        
      }
           
    }

    //MAIN FUNCTION, create new GUI object and set visible to true
    public static void main(String[] args)
    {
        GUICreator showTheGui = new GUICreator();
        showTheGui.setVisible(true);
    }

}
