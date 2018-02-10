package table;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import java.awt.*;
import java.sql.ResultSet;
import java.util.*;

//add
//delete
//sort
//search


public class InventoryTable extends JTable
{
    private DefaultTableModel model;
    private TableRowSorter<TableModel> sorter;
    private InventoryDatabase db;
    private ResultSet rs;
    private JTextField searchInput = new JTextField();
	
    InventoryTable( Object[][] rowData, Object[] columnNames)
	{
		super();
		model = new DefaultTableModel(rowData, columnNames);
		this.setModel(model);
	    this.setShowGrid(true);
	    this.setGridColor(Color.BLACK);
	    db = new InventoryDatabase();
	    
	/**	model.addColumn("Name");
		model.addColumn("Inventory ID");
		model.addColumn("Quantity");
		model.addColumn("Expiration Date");
		model.addColumn("Date Entered");
		model.addColumn("Category"); **/
		this.setAutoCreateRowSorter(true);	
	}
    
  
	
	public void addNewRow(String name, int ID, int quantity, String date, String expiration, String category)
	{
		model.addRow(new Object[] {name, ID, quantity, date, expiration, category});
	}
	
	public void deleteRowByName( String name )
	{
		
		for( int i = 0; i < model.getRowCount(); i++ )
		{
			if(model.getValueAt(i, 0).equals(name))
			{
				model.removeRow(i);
			}
		}	
	}
	
	public void deleteRowByID( int ID )
	{
	
		for( int i = 0; i < model.getRowCount(); i++ )
		{
			if(model.getValueAt( i, 1 ).equals(ID) )
			{
				model.removeRow(i);
				break;
			}
		}	
	}
	
	public void updateRowByID(Object value, int ID, String column)
	{
		for(int i = 0; i < model.getRowCount(); i++)
		{
			if( model.getValueAt(i, 1).equals(ID) )
			{
				switch(column)
				{
				    case "Name":
				        model.setValueAt(value, i, 0);
					    break;
				    case "ID":
					    model.setValueAt(value, i, 1);
					    break;
				    case "Quantity":
					    model.setValueAt(value, i, 2);
					    break;
				    case "Expiration":
					    model.setValueAt(value, i, 3);
					    break;
				    case "Category":
				     	model.setValueAt(value, i, 4);
				     	break;
				    default:
				    	    break;
				}
				break;
			}
		}	
	}
	
	
	public void updateRowByName(Object value, String name, String column)
	{
		for(int i = 0; i < model.getRowCount(); i++)
		{
			if( model.getValueAt(i, 0).equals(name) )
			{
				switch(column)
				{
				    case "Name":
				        model.setValueAt(value, i, 0);
					    break;
				    case "ID":
					    model.setValueAt(value, i, 1);
					    break;
				    case "Quantity":
					    model.setValueAt(value, i, 2);
					    break;
				    case "Expiration":
					    model.setValueAt(value, i, 3);
					    break;
				    case "Category":
				     	model.setValueAt(value, i, 4);
				     	break;
				    default:
				    	    break;
				}
				break;
			}
		}	
	}
	
	
	public void searchFilter(String filter)
	{
	 	
		
	}
	    
}
