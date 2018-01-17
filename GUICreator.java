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

public class GUICreator extends JFrame
{
    //Declaration of variables for options display text area (left side)
    //And inventory display (right side)
    private JTextArea optionsDisplay;
    private JTextArea inventoryDisplay;
    //Declaration of Combo Box
    private JComboBox comboBoxMenu;

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
        String[] menuOptions = {"Add", "Remove", "Edit", "View"};
        //Creating a JLabel for the menu
        JLabel comboMenuLabel = new JLabel("Menu: ");

        //Create new menu object for combobox menu
        comboBoxMenu = new JComboBox(menuOptions);
        //sets the first option as the default set option.
        comboBoxMenu.setSelectedIndex(0);

        //Create options display object, 200 x 200 height to start with
        optionsDisplay = new JTextArea(20, 15);
        JScrollPane optionsScrollPane = new JScrollPane(inventoryDisplay);
        optionsScrollPane.getVerticalScrollBar().setValue(optionsScrollPane.getVerticalScrollBar().getMinimum());
        optionsScrollPane.getHorizontalScrollBar().setValue(optionsScrollPane.getHorizontalScrollBar().getMinimum());
        //Not sure what we're going to put here, for now I am setting this to false
        //So that we don't have to worry about it being messed with?
        optionsDisplay.setEditable(false);
        optionsDisplay.setText("Add Options Here!");
        //Creating a label to go with this options display area
        JLabel optionsLabel = new JLabel("Select Options: ");

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
        JPanel comboBoxPanel = new JPanel();
        comboBoxPanel.setLayout(new FlowLayout());
        comboBoxPanel.add(comboMenuLabel);
        comboBoxPanel.add(comboBoxMenu);
        leftPanel.add(comboBoxPanel);

        //Creating a panel to sit inside the left panel that holds the options text area
        JPanel optionsTextPanel = new JPanel();
        optionsTextPanel.setLayout(new FlowLayout());
        optionsTextPanel.add(optionsLabel);
        optionsTextPanel.add(optionsDisplay);
        leftPanel.add(optionsTextPanel);

        //Adding the left panel to the larger container panel.
        containerPanel.add(leftPanel);

        //Creating the right panel that holds our inventory
        //display box
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.X_AXIS));
        rightPanel.setBorder(new EmptyBorder(20, 10, 20, 10));

        //Jpanel to hold the large display text area
        JPanel inventoryDisplayPanel = new JPanel();
        inventoryDisplayPanel.setLayout(new FlowLayout());
        inventoryDisplayPanel.add(invDisplayLabel);
        inventoryDisplayPanel.add(invDisplayPane);
        rightPanel.add(inventoryDisplayPanel);
        containerPanel.add(rightPanel);
    }


    //MAIN FUNCTION, create new GUI object and set visible to true
    public static void main(String[] args)
    {
        GUICreator showTheGui = new GUICreator();
        showTheGui.setVisible(true);
    }

}
