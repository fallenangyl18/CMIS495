/**
 * Created by Beth on 1/16/2018.
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;;
import java.sql.Timestamp;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;

public class GUICreator extends JFrame implements ActionListener
{
    //Declaration of variables for options display text area (left side)
    //And inventory display (right side)
    //Declaration of Combo Box
    private JTextArea inventoryDisplay;
    private JButton addButton;
    private JButton editButton;
    private JButton removeButton;
    private JTextField itemNameTextField;
    private JTextField quantityTextField;
    private JTextField expirationDateTextField;
    private JTextField notesTextField;
    private JButton sortButton;
    private JComboBox itemCategory;

   //InventoryTable jTable = new InventoryTable();

    //Create the GUI
    public GUICreator()
    {
        //setting GUI base information
        super("Pantry Inventory");
        setLayout(new BorderLayout());
        setBackground(Color.white);
        setSize(850, 550);

        //Creating a "container" for the panels to sit in
        JPanel containerPanel = new JPanel();
        //setting the layout and border information
        containerPanel.setLayout(new BoxLayout(containerPanel, BoxLayout.X_AXIS));
        add(containerPanel, BorderLayout.CENTER);
        containerPanel.setBorder(new EmptyBorder(10, 15, 10, 15));
        //set default to close when exited
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


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

        //Creating the inventory display area where we will be shown what is in our inventory
        inventoryDisplay = new JTextArea(20, 40);
        //set a preferred size of the display text area
        inventoryDisplay.setPreferredSize(new Dimension(100, 100));
        //create a scroll pane so that you can have a scroll bar inside.
        JScrollPane invDisplayPane = new JScrollPane(inventoryDisplay);
        invDisplayPane.getVerticalScrollBar().setValue(invDisplayPane.getVerticalScrollBar().getMinimum());
        invDisplayPane.getHorizontalScrollBar().setValue(invDisplayPane.getHorizontalScrollBar().getMinimum());
        //I don't know that we want to mess with this yet so setting editable to false
        inventoryDisplay.setEditable(false);
        //Creating a label for this display
        JLabel invDisplayLabel = new JLabel("Current Inventory: ");


        //Creating a label for this display

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


        //Creating a panel to sit inside of the left panel that holds the combobox
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBorder(new EmptyBorder(20, 10, 20, 10));
        buttonsPanel.add(addButton);
        buttonsPanel.add(editButton);
        buttonsPanel.add(removeButton);
        leftPanel.add(buttonsPanel);


        //Adding all of hte components that are in the left panel into the overall container panel.
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
        rightPanel.add(inventoryDisplayPanel);
        containerPanel.add(rightPanel);
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
        try
        {
            //Getting the entries from the GUI's text fields, that the user entered.
            String itemEntry = itemNameTextField.getText();
            int quantityEntry = Integer.parseInt(quantityTextField.getText());
            String expirationEntry = expirationDateTextField.getText();
            String categoryEntry = itemCategory.getSelectedItem().toString();
            String notesEntry = notesTextField.getText();
            Object[] row = new Object[5];

            if (e.getSource() == addButton)
            {
                if (itemEntry != null && quantityEntry != 0 && expirationEntry != null && !categoryEntry.equals("Select Category"))
                {
                    Timestamp theTimeIs = createTimeStamp();
                    System.out.println("You added " + itemEntry + " on " + theTimeIs);
                    JOptionPane.showMessageDialog(null, "You have successfully added " + quantityEntry + " " + itemEntry + "(s) to the the database.");
                } else
                {
                    JOptionPane.showMessageDialog(null, "Please fill out all fields in this form.");
                }
            } else if (e.getSource() == editButton) {
                if (itemEntry != null && quantityEntry != 0 && expirationEntry != null)
                {
                    //do something else
                    System.out.println("You hit the edit button.");
                    Timestamp theTimeIs = createTimeStamp();
                    System.out.println("You edited " + itemEntry + " on " + theTimeIs);
                    JOptionPane.showMessageDialog(null, "You have successfully edited item(s) in the database.");
                } else
                {
                    JOptionPane.showMessageDialog(null, "Please fill out all of the information in this form!");
                }

            } else if (e.getSource() == removeButton)
            {
            //do remove stuff
                JOptionPane.showMessageDialog(null, "You have successfully removed item(s) from the database.");
                Timestamp theTimeIs = createTimeStamp();
                System.out.println("You removed an item from the table on " + theTimeIs);

            }
        }
        catch (NumberFormatException ex)
        {
            JOptionPane.showMessageDialog(null, "Error: Please make sure all fields are filled out before attempting to " +
                    "add an item to the database. Error message: " + ex.getMessage());
        }
    }



    //MAIN FUNCTION, create new GUI object and set visible to true
    public static void main(String[] args)
    {
        GUICreator showTheGui = new GUICreator();
        showTheGui.setVisible(true);
    }

}
