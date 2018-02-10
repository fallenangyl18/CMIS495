package table;

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
	private JLabel pork = new JLabel("Pork:");
	private JLabel chicken = new JLabel("Chicken:");
	private JLabel vegetables = new JLabel("Vegetables:");
	private JLabel fruits = new JLabel("Fruits:");
	private JLabel beef = new JLabel("Beef:");
	private JLabel dairy = new JLabel("Dairy:");
	private JLabel fish = new JLabel("Fish:");
	private JLabel grains = new JLabel("Grains:");
	private JLabel oil = new JLabel("Oils:");
	private JLabel legumes = new JLabel("Legumes:");
	private JLabel other = new JLabel("Other:");
	JPanel foodGroupPanel = new JPanel();

	
	public DataPanel()
	{
		super();
		foodGroupPanel.setLayout(new GridLayout(2, 6));
		db = new InventoryDatabase();
		totalInventory = new JLabel("Total Inventory:" + countTotalInventory());
		pork = new JLabel("Pork:" + countFoodGroup("Pork") );
		chicken = new JLabel("Chicken:" + countFoodGroup("Chicken") );
		vegetables = new JLabel("Vegetables:" + countFoodGroup("Vegetables") );
		fruits = new JLabel("Fruits:" + countFoodGroup("Fruits") );
		beef = new JLabel("Beef:" + countFoodGroup("Beef") );
	    dairy = new JLabel("Dairy:" + countFoodGroup("Dairy") );
		fish = new JLabel("Fish:"  + countFoodGroup("Fish") );
	    grains = new JLabel("Grains:" + countFoodGroup("Grains") );
		oil = new JLabel("Oils:" + countFoodGroup("Oils") );
		legumes = new JLabel("Legumes:" + countFoodGroup("Legumes") );
		other = new JLabel("Other:" + countFoodGroup("Other") );
		
		foodGroupPanel.add(totalInventory);
		foodGroupPanel.add(pork);
		foodGroupPanel.add(chicken);
		foodGroupPanel.add(vegetables);
		foodGroupPanel.add(fruits);
		foodGroupPanel.add(beef);
		foodGroupPanel.add(dairy);
		foodGroupPanel.add(fish);
		foodGroupPanel.add(grains);
		foodGroupPanel.add(oil);
		foodGroupPanel.add(legumes);
		foodGroupPanel.add(other);
		this.add(foodGroupPanel);

	}
	
	public int countTotalInventory()
	{
		rs = db.customQuery("SELECT SUM(Quantity) FROM InventoryApp;");
        return rs.getInt(0);
	}
	
	public int countFoodGroup(String foodGroup)
	{
	    rs = db.customQuery("SELECT SUM(Quantity) FROM InventoryApp WHERE Category= \'"+ foodGroup +"\';");
	    return rs.getInt(0);
	}
	

}
