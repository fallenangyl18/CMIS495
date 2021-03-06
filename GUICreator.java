package inventory;

/***************************** REVISION HISTORY****************************************
 *
 *
 * Version 1.0 created by Beth 01/16/18 Created all buttons, text fields, labels,
 * GUI components, action listener, timestamp function JPanels, MAIN, action
 * listener variables
 *
 * VERSION 1.1 Updated by Sumit Malhotra on 02/15/2018 removed combobox and
 * button for sort update add method to support InventoryTable.java update edit
 * method to support InventoryTable.java and to support dialog box for id update
 * delete method to support InventoryTable.java and dialog box for deleting
 * entry adding methods and gui elements for search
 *
 * Additions on add/edit/update/delete/search/sort by Sumit on 02/15/2018
 *
 * VERSION 1.2 on 2/22/18 Beth added "clearFields" function to clear the text
 * fields after the add and edit buttons are pressed. Sumit fixed "off by one"
 * error with Timestamp displaying in table when unneeded.
 *
 * VERSION 1.3 2/23/18 Beth added "Notes" section to Parameters so the notes the
 * user entered would be picked up and displayed in the table. Also added a
 * "Grains" category at the request of Fred as non-perishables were felt to not
 * be enough. toLowerCase all strings for fast searching to make sure we don't
 * miss stuff.
 *
 *VERSION 1.4
 *Update by Sumit Malhotra on 02/24/2018
 *removed 1.1 methods and pop ups for editing and deleting
 *updated editing method to support editing by selection and commiting edit with button text changes,
 *and code to commit changes to the data table
 *updated deleting method to support deleting by selected row and call to InventoryTable()
 *added code for Database startup entries to populate database on startup
 *added code for supporting database class and result set
 *
 * VERSION 1.5 2/25/18 added inventoryID textbox and ok button.  Added okbutton and removebutton to
 * actionlistener. Reworked if statement.  added and tested add item, update and delete item
 * using database. Populates textboxes on edit click, populates jtable on startup.
 * Soft deletes item from database on delete click
 *
 * VERSION 1.6 2/27/18 Beth worked on, took away erroneous notifications, we nixed
 * the search feature for the sake of time, cleaned up code a little, error checking
 * for invalid and null entries. Worked with Sharon on refreshing the JScrollPane when
 * items are deleted from or added to the inventory. Wrote a refresh function, handled
 * some unhandled exceptions.
 *
 * VERSION 1.7 2/28/18 Beth changed the display size of the JScrollPane to make the display
 * larger and easier to read. Added inventoryIDTxt to the reset the text function so that
 * when the "OK" button is cleared after an inventory edit, the ID number is cleared out
 * as well as the rest of the fields. Removed TimeStamp function, was no longer needed
 * *
 *VERSION 1.8 03/01/2018
 *Sumit added methods to support updating the DataPanel, added exceptions for edit and
 *OK buttons for ID, added JTable settings to support static columns and gridlines
 * Beth added condition to limit the user to Gross entries (144 maximum quantity);
 * Worked on exception handling in the buttons for if users press the add/edit/remove
 * buttons while no fields are filled out, making sure all fields are filled out when
 * the user wants to enter things. Also added a "Check Expiring Items" button for Fred's
 * notification class.
 *
 * VERSION 1.9 03/02/2018
 * Beth cleaned up unused code, added comments.
 *
 ****************************************************************************************
 */

import java.awt.*;
import java.awt.event.*;
import static java.lang.Integer.parseInt;
import java.sql.ResultSet;
import java.util.Vector;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

public class GUICreator extends JFrame implements ActionListener
{
    //Declaration of variables for options display text area (left side)
    //And inventory display (right side)
    //Declaration of Combo Box
    private JButton addButton;
    private JButton editButton;
    private JButton removeButton;
    private JButton checkExpiringButton;
    private JTextField itemNameTextField;
    private JTextField quantityTextField;
    private JTextField expirationDateTextField;
    private JTextField notesTextField;
    private JComboBox itemCategory;

    private JTable jtable;
    DataPanel data = new DataPanel();
    private final JTextField inventoryIDTxt;
    private JButton okButton;

    InventoryDatabase dbConn = new InventoryDatabase();


    //Create the GUI
    public GUICreator() throws SQLException
    {
        //setting GUI base information
        super("Pantry Inventory");
        setLayout(new BorderLayout());
        setBackground(Color.white);
        setSize(1024, 768);

        //Creating a "container" for the panels to sit in
        JPanel containerPanel = new JPanel();
        //setting the layout and border information
        containerPanel.setLayout(new BoxLayout(containerPanel, BoxLayout.X_AXIS));
        add(containerPanel, BorderLayout.CENTER);
        containerPanel.setBorder(new EmptyBorder(10, 15, 10, 15));
        //set default to close when exited
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        dbConn.init();
        ResultSet rs = dbConn.getAllActiveItems();

        jtable = new JTable(buildTableModel(rs));
        jtable.setRowSelectionAllowed(true);
        jtable.getTableHeader().setReorderingAllowed(false);
        jtable.setShowGrid(true);
        jtable.setGridColor(Color.BLACK);

        //Creating a JLabel for the menu
        JLabel menuLabel = new JLabel("Inventory: ");
        //creating the buttons and adding their action listeners
        addButton = new JButton("Add");
        addButton.setToolTipText("Add items to database.");
        addButton.addActionListener(this);
        editButton = new JButton("Edit");
        editButton.setToolTipText("Edit existing items in the database.");
        editButton.addActionListener(this);
        okButton = new JButton("OK");
        okButton.addActionListener(this);
        removeButton = new JButton("Remove");
        removeButton.setToolTipText("Remove items from the databse.");
        removeButton.addActionListener(this);
        checkExpiringButton = new JButton("Check Expiring Items");
        checkExpiringButton.setToolTipText("Check what items are expiring soon or already expired.");
        checkExpiringButton.addActionListener(this);

        inventoryIDTxt = new JTextField();
        inventoryIDTxt.setMaximumSize(new Dimension(Integer.MAX_VALUE, inventoryIDTxt.getPreferredSize().height));
        inventoryIDTxt.setEditable(true);
        JLabel inventoryIDLabel = new JLabel("InventoryID: ");

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

        String[] categories = {"Select Category", "Produce", "Meat", "Dairy", "Non-Perishable", "Grains", "Liquids"};
        itemCategory = new JComboBox(categories);
        JLabel categoriesLabel = new JLabel("Item Category:   ");
        itemCategory.setSelectedIndex(0);

        //Putting the JTable into the Display Pane; Removed textArea, was no longer needed
        JScrollPane invDisplayPane = new JScrollPane(jtable);
        jtable.setFillsViewportHeight(true);
        invDisplayPane.setPreferredSize(new Dimension(700, 450));
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
        JPanel itemIDInfoPanel = new JPanel();
        itemIDInfoPanel.setLayout(new BoxLayout(itemIDInfoPanel, BoxLayout.Y_AXIS));
        itemIDInfoPanel.add(inventoryIDLabel);
        itemIDInfoPanel.add(inventoryIDTxt);
        itemIDInfoPanel.setBorder(new EmptyBorder(10, 5, 10, 5));
        leftPanel.add(itemIDInfoPanel);

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
        notesTextPanel.setBorder(new EmptyBorder(10, 5, 5, 5));
        leftPanel.add(notesTextPanel);

        //JPanel for sort dropdown
        JPanel sortComboPanel = new JPanel();
        sortComboPanel.setLayout(new BoxLayout(sortComboPanel, BoxLayout.X_AXIS));
        sortComboPanel.setMaximumSize(new Dimension(250, 25));
        sortComboPanel.setBorder(new EmptyBorder(20, 10, 20, 10));
        leftPanel.add(sortComboPanel);

        //Creating a panel to sit inside of the left panel that holds the combobox
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        buttonsPanel.add(addButton);
        buttonsPanel.add(editButton);
        buttonsPanel.add(okButton);
        buttonsPanel.add(removeButton);
        leftPanel.add(buttonsPanel);

        JPanel checkExpPanel = new JPanel();
        checkExpPanel.setLayout(new FlowLayout());
        checkExpPanel.add(checkExpiringButton);
        leftPanel.add(checkExpPanel);
        //Adding all of the components that are in the left panel into the overall container panel.
        containerPanel.add(leftPanel);

        //Creating the right panel that holds our inventory
        //display box
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.X_AXIS));
        rightPanel.setBorder(new EmptyBorder(20, 10, 20, 10));

        //Jpanel to hold the large display text area
        //that houses the Inventory.
        JPanel inventoryDisplayPanel = new JPanel();
        inventoryDisplayPanel.setLayout(new BoxLayout(inventoryDisplayPanel, BoxLayout.Y_AXIS));
        inventoryDisplayPanel.add(invDisplayLabel);
        inventoryDisplayPanel.add(invDisplayPane);
        rightPanel.add(inventoryDisplayPanel);
        containerPanel.add(rightPanel);

        //adds Sumit's data panel
        add(data, BorderLayout.SOUTH);
    }

    //function that resets the text fields
    //and position of the combobox when buttons are pressed.
    private void clearFields()
    {
        itemNameTextField.setText("");
        quantityTextField.setText("");
        expirationDateTextField.setText("");
        itemCategory.setSelectedIndex(0);
        notesTextField.setText("");
        inventoryIDTxt.setText("");
    }


    //function to update the table when the user adds, edits or deletes
    //so that it reflects their changes.
    public void refreshTheTable() throws SQLException
    {
        ResultSet rs = dbConn.getAllActiveItems();
        DefaultTableModel model = buildTableModel(rs);
        jtable.setModel(model);
        model.fireTableDataChanged();
        jtable.repaint();
    }

    //Coding action listener
    @Override
    public void actionPerformed(ActionEvent e)
    {
        //if the action performed is the add button was pressed
        if (e.getSource() == addButton)
        {
            try
            {
                //creating variables from the user's input
                String itemEntry = itemNameTextField.getText();
                int quantityEntry = Integer.parseInt(quantityTextField.getText());
                String expirationEntry = expirationDateTextField.getText();
                String categoryEntry = itemCategory.getSelectedItem().toString();
                String notesEntry = notesTextField.getText();
                //if the item entry isn't blank, the quantity is not zero and is less than or equal to 144
                //and if the expiration entry is not default and the category is not default, do the thing.
                if ((itemEntry != "") && quantityEntry != 0 && quantityEntry <= 144 && expirationEntry != "" && !expirationEntry.equals("MM-DD-YYYY") && !categoryEntry.equals("Select Category"))
                {
                    try
                    {
                        //insert item to the database, clear the text fields, refresh the table and update it to reflect the changes.
                        dbConn.insertItem(itemEntry, quantityEntry, expirationEntry, categoryEntry, notesEntry);
                        clearFields();
                        refreshTheTable();
                    } catch (SQLException ex) {
                        Logger.getLogger(GUICreator.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                else
                {
                    //else if the things are not all filled out, tell the user to fill the things out.
                    JOptionPane.showMessageDialog(null, "Please make sure all fields are filled out completely. \n"
                            + "Note that 0 items or more than 144 items are invalid entries.");
                }
            } catch (NumberFormatException exc)
            {
                //catch it if the person tries to enter "dog" or a non-number into the quantity field.
                JOptionPane.showMessageDialog(null, "Please make sure all fields are filled out correctly.");
            }
            //if the button that was pressed was edit
        } else if (e.getSource() == editButton) {
            try {
                //get the result for that inventoryID from the database
                ResultSet rs = dbConn.getItemByID(parseInt(inventoryIDTxt.getText()));
                while (rs.next())
                {
                    //edit the fields with the user's new entries
                    itemNameTextField.setText(rs.getString("ItemName"));
                    quantityTextField.setText(rs.getString("QTY"));
                    expirationDateTextField.setText(rs.getString("ExpireDate"));
                    notesTextField.setText(rs.getString("notes"));
                    itemCategory.setSelectedItem(rs.getString("category"));
                }
            } catch (SQLException ex)
            {
                //otherwise log the errors and tell the user to make sure their fields have all been filled out.
                Logger.getLogger(GUICreator.class.getName()).log(Level.SEVERE, null, ex);
            }
            catch(NumberFormatException e1)
            {
                JOptionPane.showMessageDialog(null, "Please make sure that all necessary fields are filled out correctly.");
            }
            //else if the user clicks the remove button
        } else if (e.getSource() == removeButton)
        {
            try
            {
                try
                {
                    //first try to get the database and delete by the ID
                    dbConn.deleteByID(parseInt(inventoryIDTxt.getText()));
                    refreshTheTable();
                    clearFields();
                }
                //catch if they haven't entered in the ID correctly.
                catch (NumberFormatException numEx)
                {
                    JOptionPane.showMessageDialog(null, "Please enter the ID of the item you wish to remove.");
                }
                //then catch database errors
            } catch (Exception ex)
            {
                Logger.getLogger(GUICreator.class.getName()).log(Level.SEVERE, null, ex);
            }
            //if the source is the ok button
        } else if (e.getSource() == okButton)
        {
            try
            {
                //commit the changes
                String inventoryID = (inventoryIDTxt.getText());
                String editItemEntry = (itemNameTextField.getText());
                int editQty = parseInt(quantityTextField.getText());
                String editExpire = expirationDateTextField.getText();
                String editCat = itemCategory.getSelectedItem().toString();
                String editNotes = notesTextField.getText();
                //as long as the ID isn't null or zero....
                if (inventoryID != null || !inventoryID.equals("0"))
                {
                    //update the items in the database, and refresh teh table
                    int id = Integer.parseInt(inventoryID);
                    dbConn.updateItemByID(editItemEntry, editQty, editExpire, editCat, editNotes, id);
                    try {
                        refreshTheTable();
                    }
                    catch (SQLException exc)
                    {
                        exc.printStackTrace();
                    }
                    //then clear the fields
                    clearFields();
                } else {
                    //else tell the user there was an error and to fill out their form correctly.
                    JOptionPane.showMessageDialog(null, "There was an error updating this entry. Please make sure all necessary fields are filled out.");
                }
            }
            catch(NumberFormatException e1)
            {
                JOptionPane.showMessageDialog(null, "There was an error updating this entry. Please make sure all necessary fields are filled out.");
            }
        }
        else //else it's the check inventory button
        {
            try
            {
                @SuppressWarnings("unused")
                 //try to create the new dialog box.
                NotificationDialog nd= new NotificationDialog();
            } catch (SQLException e1)
            {
                e1.printStackTrace();
            }
        }
        //do the data panel inventory and foodgroup stuff at the bottom of the gui
        data.countTotalInventory();
        data.countFoodGroup();
    }


    //MAIN FUNCTION, create new GUI object and set visible to true
    public static void main(String[] args) throws SQLException {
        GUICreator showTheGui = new GUICreator();
        showTheGui.setVisible(true);
        NotificationDialog nd= new NotificationDialog();
    }

    public static DefaultTableModel buildTableModel(ResultSet rs)
            throws SQLException {

        ResultSetMetaData metaData = rs.getMetaData();

        // names of columns
        Vector<String> columnNames = new Vector<String>();
        int columnCount = metaData.getColumnCount();
        for (int column = 1; column <= columnCount; column++) {
            columnNames.add(metaData.getColumnName(column));
        }

        // data of the table
        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        while (rs.next()) {
            Vector<Object> vector = new Vector<Object>();
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                vector.add(rs.getObject(columnIndex));
            }
            data.add(vector);
        }

        return new DefaultTableModel(data, columnNames);
    }
}
