
/**
 * Created by Sumit on 02/10/2018, supports add/update/delete by ID and name and GUI customization, and supports database
 *add/update/delete connections 
 * Updated by Sumit on 02/15/2018 to support external row sorter, 
 * Search filter, updated add, update, and delete
 */


import java.util.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import java.awt.*;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.*;

//add
//delete
//sort
//search


public class InventoryTable extends JTable 
{
    private DefaultTableModel model;
    private TableRowSorter<TableModel> sorter;
    //private InventoryDatabase db;
    private ResultSet rs;
    private JTextField searchInput = new JTextField();
    
    
    InventoryTable(Vector<Object> rowData, Vector<Object> columnNames)
    {
    	
    	
    }
    
    
    InventoryTable( Object[][] rowData , Object[] columnNames )
	{
		super();
		model = new DefaultTableModel(rowData, columnNames);
		this.setRowHeight(30);
		
        this.setCellSelectionEnabled(true);  //no cell selection
        this.getTableHeader().setReorderingAllowed(false); //columns are stationary
        this.setEnabled(false); //cells are not editable
		this.setModel(model); //sets column headers and rows
	    this.setShowGrid(true); //shows gridlines
	    this.setGridColor(Color.BLACK);
	    
	    sorter = new TableRowSorter<TableModel>(model);
	   
	    sorter.setComparator(1, new Comparator<Integer>() 
	    {
			@Override
			public int compare(Integer o1, Integer o2) 
			{
				return o1.compareTo(o2);
			}
	    });
	    
	    sorter.setComparator(2, new Comparator<Integer>() 
	    {
			@Override
			public int compare(Integer o1, Integer o2) 
			{
				return o1.compareTo(o2);
			}
	    });
	    
	    this.setRowSorter(sorter);
	    //db = new InventoryDatabase()
	    
	    Timestamp currentDate = new Timestamp(System.currentTimeMillis());
	    String date = String.valueOf(currentDate);
	    
        
	    DefaultTableCellRenderer colorRenderer = new DefaultTableCellRenderer() {
	     
	    	 public Component getTableCellRendererComponent(JTable table,Object value, boolean isSelected, 
	    	    		boolean hasFocus, int row, int column)
	    	    {
	    	    	    Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	    	    	    
	 	        	   for(int i = 0; i < model.getRowCount(); i++)
	 		       	    {
	 		       	    	    int val = date.compareTo( String.valueOf( cell ) );
	 		       	    	    
	 		       	    	    if( val == -1)
	 		       	    	    {
	 		       	    	     
	 		       	    	      	cell.setBackground(Color.YELLOW);
	 		   	               
	 		       	    	    }
	 		       	    	    else if(val == 1) 
	 		       	    	    {
	 		       	    	    cell.setBackground(Color.YELLOW);
	 		       	    	 
	 		       	    	    }
	 		       	    	  
	 		       	    }
	    	      	return cell;
	    	    }
	    	
	    	  /**
	    	    public void setValue(Object value) { 
	        	
	        	   for(int i = 0; i < model.getRowCount(); i++)
	       	    {
	       	    	    int val = date.compareTo( String.valueOf( value ) );
	       	    	    if( val == -1)
	       	    	    {
	       	    	     
	       	    	    	this.setBackground(Color.YELLOW);
	   	               
	       	    	    }
	       	    	    else if(val == 1) 
	       	    	    {
	       	    	     	
	       	    	  
	       	    	    }
	       	    	    setText(value.toString());
	       	    }
	 
	        }**/
	     };
	 	    
	     this.setDefaultRenderer(Object.class, colorRenderer);
	     
	}
    
   
    
    
    	 
	public void addNewRow(String name, int ID, int quantity, Timestamp date, String expiration, String category)
	{
		model.addRow( new Object[] { name, ID, quantity, date, expiration, category } );
		ArrayList<Object> addList = new ArrayList<Object>();
		addList.add(name);
		addList.add(ID);
		addList.add(quantity);
		addList.add(date);
		addList.add(expiration);
		addList.add(category);
                
		db.insertItem(addList);
		
		
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
		db.deleteByID(ID);
		
	}
	
	public void updateRowByID(Object[] value, int ID)
	{
		for( int i = 0; i < model.getRowCount(); i++ )
		{
			if( model.getValueAt(i, 1).equals(ID) )
			{
		        model.setValueAt(value[0], i, 0);
				model.setValueAt(value[1], i, 1);
		        model.setValueAt(value[2], i, 2);
			    model.setValueAt(value[3], i, 3);
			    model.setValueAt(value[4], i, 4);
			}
			break;
		}	
		
		ArrayList<Object> updateList = new ArrayList<Object>();
		updateList.add(value);
                
		db.updateItemByID(updateList, ID);
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
		if(!filter.equals(""))
		{
		    sorter.setRowFilter(RowFilter.regexFilter(filter));
		}
		else if( filter.equals("") )
		{
			sorter.setRowFilter(null);
		}
	}
	    
}
