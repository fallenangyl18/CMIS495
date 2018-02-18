package Inventory;

/*************************** REVISION HISTORY ****************************************
* 
*
* VERSION 1.0
* Created by Sumit Malhotra on 02/10/2018 creates the panel with inventory totals by category, 
* categories are represented by swing elements
*
* VERSION 1.1
* Updated by Sumit Malhotra on 02/15/2018 changed categories, database calls to new
* methods in database, updated GUI output with progress bars
*
*
*
*
*
*
*
*
**************************************************************************************/

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.sql.*;
import java.util.*;

public class DataPanel extends JPanel
{
	private ResultSet rs;
	private InventoryDatabase db;

	private JLabel totalInventory = new JLabel("Total Inventory:");
	private JLabel produce = new JLabel("Produce:      0/100");
	private JLabel meat = new JLabel("Meat:      0/100");
	private JLabel dairy = new JLabel("Dairy:     0/100");
	private JLabel nonperishable = new JLabel("Non-perishable:    0/100");
	private JLabel liquids = new JLabel("Liquids:      0/100");
	private JProgressBar produceJPB;
	private JProgressBar meatJPB;
	private JProgressBar dairyJPB;
	private JProgressBar nonperishableJPB;
	private JProgressBar liquidsJPB;
	
	JPanel foodGroupPanel = new JPanel();

	public DataPanel()
	{
		super();
		
		UIManager.put("ProgressBar.selectionForeground", Color.GREEN);
		produceJPB = new JProgressBar(0 , 100);
		meatJPB = new JProgressBar(0 , 100);
		dairyJPB = new JProgressBar(0 , 100);
		nonperishableJPB = new JProgressBar(0 , 100);
		liquidsJPB = new JProgressBar(0 , 100);
		
		this.setBorder(BorderFactory.createLoweredBevelBorder());
		foodGroupPanel.setLayout(new GridLayout(6, 2));
		//db = new InventoryDatabase();
		
		totalInventory.setFont(new Font("default", Font.BOLD, 18));
		totalInventory.setHorizontalAlignment(SwingConstants.LEFT);
		
		produceJPB.setValue(45);
		produceJPB.setForeground(Color.GREEN);
		
		meatJPB.setValue(100);
		meatJPB.setForeground(Color.GREEN);
		
		dairyJPB.setValue(100);
		dairyJPB.setForeground( Color.GREEN );
		
		nonperishableJPB.setValue(100);
		nonperishableJPB.setForeground( Color.GREEN );
		
		liquidsJPB.setValue(75);
		liquidsJPB.setForeground( Color.getHSBColor(179, 64, 95) );
		
		/**
		totalInventory = new JLabel("Total Inventory:" + countTotalInventory());
		**/
	   
		this.add(totalInventory);
		foodGroupPanel.add(produce);
	    foodGroupPanel.add(produceJPB);
	   
	    foodGroupPanel.add(meat);
	    foodGroupPanel.add(meatJPB);
	    
	    foodGroupPanel.add(dairy);
	    foodGroupPanel.add(dairyJPB);
	    
	    foodGroupPanel.add(nonperishable);
	    foodGroupPanel.add(nonperishableJPB);
	
	    this.add(foodGroupPanel);

	}
	
	public int countTotalInventory()
	{
	    db.tableQuantityByTotal();
		return 0;
	}
	
	public int countFoodGroup(String foodGroup)
	{
	    db.tableQuantityByCategory();
		return 0;
	}
	

}
