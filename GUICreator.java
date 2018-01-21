/**
 * Created by Beth on 1/16/2018.
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.util.*;
import java.io.*;

public class GUICreator extends JFrame implements ActionListener
{
    //Declaration of variables for options display text area (left side)
    //And inventory display (right side)
    private JTextArea inventoryDisplay;
    //Declaration of Combo Box
    private JButton addButton;
    private JButton editButton;
    private JButton removeButton;
    private JButton sortButton;

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

        //Setting the JComboBox options for the dropdown
        //Feel free to add more as we need

        //Creating a JLabel for the menu
        JLabel buttonMenuLabel = new JLabel("Menu: ");
        addButton = new JButton("Add");
        addButton.setToolTipText("Add items to database.");
        editButton = new JButton("Edit");
        editButton.setToolTipText("Edit existing items in the database.");
        removeButton = new JButton("Remove");
        removeButton.setToolTipText("Remove items from the databse.");
        sortButton = new JButton("Sort");
        sortButton.setToolTipText("Sort items by category.");

        //Creating the inventory display area where we will be shown what is in our inventory
        inventoryDisplay = new JTextArea(30, 50);
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

        //Creating the panel to hold the left hand side of our options
        //To put in the overall container panel
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBorder(new EmptyBorder(20, 10, 20, 10));

        //Creating a panel to sit inside of the left panel that holds the combobox
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
        buttonsPanel.add(buttonMenuLabel);
        buttonsPanel.add(addButton);
        buttonsPanel.add(editButton);
        buttonsPanel.add(removeButton);
        buttonsPanel.add(sortButton);
        leftPanel.add(buttonsPanel);

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

    //Coding action listener
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == addButton)
        {
            //dosomething here
        }
        else if (e.getSource() == editButton)
        {
            //do something else
        }
        else if (e.getSource() == removeButton)
        {
            //do remove stuff
        }
        else
        {
            //the source is the sort button.
        }
    }

    //MAIN FUNCTION, create new GUI object and set visible to true
    public static void main(String[] args)
    {
        GUICreator showTheGui = new GUICreator();
        showTheGui.setVisible(true);
    }

}
