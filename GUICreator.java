
/**
 * *************************** REVISION HISTORY
 * ****************************************
 *
 * Version 1.0 created by Beth 2/10/18 Created all buttons, text fields, labels,
 * GUI components, action listener, timestamp function JPanels, MAIN, action
 * listener variables
 *
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
 * VERSION 1.4 2/25/18 Sharon revamped, hooked Database to the thing and yay it worked
 * SHARON WRITE HERE
 *
 * VERSION 1.5 2/27/18 Beth worked on, took away erroneous notifications, we nixed
 * the search feature for the sake of time, cleaned up code a little, error checking
 * for invalid and null entries. Worked with Sharon on refreshing the JScrollPane when
 * items are deleted from or added to the inventory.
 *
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

public class GUICreator extends JFrame implements ActionListener {

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

    JTable jtable = new JTable();
    DataPanel data = new DataPanel();
    private final JTextField inventoryIDTxt;
    private JButton okButton;

    InventoryDatabase dbConn = new InventoryDatabase();

    //Create the GUI
    public GUICreator() throws SQLException {
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

        dbConn.init();
        ResultSet rs = dbConn.getAllActiveItems();

        jtable = new JTable(buildTableModel(rs));

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
        buttonsPanel.add(okButton);
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

        inventoryDisplayPanel.add(searchPnl);
        rightPanel.add(inventoryDisplayPanel);
        containerPanel.add(rightPanel);

        add(data, BorderLayout.SOUTH);
    }

    private void clearFields() {
        itemNameTextField.setText("");
        quantityTextField.setText("");
        expirationDateTextField.setText("");
        itemCategory.setSelectedIndex(0);
        notesTextField.setText("");

    }

    //Coding action listener
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {

            // ArrayList<String> row = new ArrayList<>();
            // row.add(itemNameTextField.getText());
            //row.add(quantityTextField.getText());
            //row.add(expirationDateTextField.getText());
            // row.add(itemCategory.getSelectedItem().toString());
            // row.add(notesTextField.getText());
            //Getting the entries from the GUI's text fields, that the user entered.
            String itemEntry = itemNameTextField.getText().toLowerCase();
            int quantityEntry = Integer.parseInt(quantityTextField.getText());
            String expirationEntry = expirationDateTextField.getText();
            String categoryEntry = itemCategory.getSelectedItem().toString();
            String notesEntry = notesTextField.getText();

            if ((itemEntry != "") && quantityEntry != 0 && expirationEntry != "") {
                dbConn.insertItem(itemEntry, quantityEntry, expirationEntry, categoryEntry, notesEntry);
                clearFields();
                ResultSet refresh;
                try {
                    refresh = dbConn.getAllActiveItems();
                    jtable = new JTable(buildTableModel(refresh));
                } catch (SQLException ex) {
                    Logger.getLogger(GUICreator.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else {
                JOptionPane.showMessageDialog(null, "Please fill out all fields in this form.");
            }
        } else if (e.getSource() == editButton) {
            try {
                ResultSet rs = dbConn.getItemByID(parseInt(inventoryIDTxt.getText()));
                while (rs.next()) {
                    int inventoryID = rs.getInt("InventoryID");
                    itemNameTextField.setText(rs.getString("ItemName"));
                    quantityTextField.setText(rs.getString("QTY"));
                    expirationDateTextField.setText(rs.getString("ExpireDate"));
                    notesTextField.setText(rs.getString("notes"));
                    itemCategory.setSelectedItem(rs.getString("category"));

                }
            } catch (SQLException ex) {
                Logger.getLogger(GUICreator.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (e.getSource() == removeButton) {
            try {
                dbConn.deleteByID(parseInt(inventoryIDTxt.getText()));
            } catch (Exception ex) {
                Logger.getLogger(GUICreator.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else //else it's the OK button cuz there are no other buttons.
        {
            String inventoryID = (inventoryIDTxt.getText());
            String editItemEntry = (itemNameTextField.getText());
            int editQty = parseInt(quantityTextField.getText());
            String editExpire = expirationDateTextField.getText();
            String editCat = itemCategory.getSelectedItem().toString();
            String editNotes = notesTextField.getText();
            if (inventoryID != null || !inventoryID.equals("0")) {

                //Dialog Box
                //JOptionPane.showInputDialog("Enter the ID of the Item you would like to Update:");
                int id = Integer.parseInt(inventoryID);
                dbConn.updateItemByID(editItemEntry, editQty, editExpire, editCat, editNotes, id);

                clearFields();
            } else {
                JOptionPane.showMessageDialog(null, "There was an error updating this entry. Please make sure all fields all filled.");
            }
            //System.out.println("You edited " + itemEntry + " on " + theTimeIs);
            //JOptionPane.showMessageDialog(null, "You have successfully edited item(s) in the database.");
        }
    }

    //MAIN FUNCTION, create new GUI object and set visible to true
    public static void main(String[] args) throws SQLException {
        GUICreator showTheGui = new GUICreator();
        showTheGui.setVisible(true);
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
