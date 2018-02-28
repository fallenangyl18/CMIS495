package inventory;


/****************************** REVISION HISTORY **********************************************************
 * VERSION 1.0
 * Created by Sumit Malhotra on 02/10/2018, supports add/update/delete by ID and name and GUI customization
 * Supports adding, updating, and deleting in database
 *
 * VERSION 1.1
 * Updated by Sumit Malhotra on 02/15/2018 to support external row sorter,
 * Search filter, updated add, update, and delete by ID
 *
 *VERSION 1.2
 * Updated by Elizabeth Ruzich on 2/23 - removed "Date" from parameter (even though it was removed from
 * addNewRow parameter, it was not removed from model.addRow parameters. Also updated addNewRow and addRow
 * to include a string for notes since they were not in there and not being displayed in the table at all.
 *
 *
 * VERSION 1.3
 * Updated by Sumit Malhotra on 02/24/2018, created isCellEditable method for when table is being edited within 
 *table model, and support only selected cell to be edited, created selectRow() and getPreviousRow() 
 * as helper methods for editing selected cells, supports Cell Renderer for coloring rows based on expiration date
 * and also allowEditCell(), added methods deleteRowBySelection to support
 * deleted selected rows and updateRowBySelection() to support updating to database, added prepareRenderer() 
 * to support color for selected rows, Updated comparators to support comparing integers for edited cells
 *
 *****************************************************************************************************************/


import java.util.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import java.util.Vector;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.*;




public class InventoryTable extends JTable 
{
    private DefaultTableModel model; //table model
    private TableRowSorter<TableModel> sorter; //row sorter
    private boolean cellUpdate = false; //cell edit variable
    //private InventoryDatabase db;
    private ResultSet rs;  //database result sets
    private int currentRow = 00; //currentRow
    private int previous = 00;   //previous row 
    
    
    InventoryTable()
    {
    	
    }
    
    
    InventoryTable( Vector<Vector> rowData , Vector columnNames )
	{
		super();
		
		
		model = new DefaultTableModel(rowData, columnNames) {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			//sets the select row to editable based on if current table is being edited and ensures no other rows are editable
			@Override
			public boolean isCellEditable(int row, int column)  
			{
				if( currentRow == 00 && cellUpdate == true)
				{
					currentRow = row;
					selectRow(currentRow);
				}
				if( currentRow == getSelectedRow() && cellUpdate == true )
				{
					return true;
				}
				if( currentRow != getSelectedRow() && cellUpdate == true )
				{
					getPreviousRow();
                    JOptionPane.showMessageDialog(editorComp, this, "Please Commit before moving to another cell", column); 
                    cellUpdate = false;
				    return false;
				}
				
				return false;
			}
		};
		
		//db = new InventoryDatabase();
		this.setRowHeight(30);
		this.setRowSelectionAllowed(true);
		this.setFocusable(false);
        this.getTableHeader().setReorderingAllowed(false); //columns are stationary
		this.setModel(model); // set the table model
	    this.setSelectionForeground(Color.BLACK);
	    //sets column headers and rows
	    this.setShowGrid(true); //shows gridlines
	    this.setGridColor(Color.BLACK);  //set gridline colors
	    sorter = new TableRowSorter<TableModel>(model); //create row sorter
	   
	    sorter.setComparator(1, new Comparator<Object>() //comparator for sorter when comparing column 1
	    {
			@Override
			public int compare(Object o1, Object o2) 
			{
				return (Integer.valueOf(String.valueOf(o1))).compareTo(Integer.valueOf(String.valueOf(o2)));
			}
	    });
	 
	    sorter.setComparator(2, new Comparator<Object>() //comparator for sorter when comparing column 2
	    {
			@Override
			public int compare(Object o1, Object o2) 
			{
				return (Integer.valueOf(String.valueOf(o1))).compareTo(Integer.valueOf(String.valueOf(o2)));
			}
	    });
	    
	    this.setRowSorter(sorter); //sets the row sorter for the table
	    this.convertColumnIndexToModel(3); 
	    this.setDefaultRenderer(Object.class, new CellRenderer()); //sets the renderer for determining cell colors
	}
    
    //this method sets the row when editing
    public void selectRow(int row)
    {
    	   previous = row;
    }
    
    //this method gets the row selected when editing
    public void getPreviousRow()
    {
   	     this.getSelectionModel().setSelectionInterval(previous, previous);
    }
    
    //this method updates the data table with a new entry
    //and also inserts the entry into the database
    
	public void addNewRow(String name, int ID, int quantity, String expiration, String category, String notes, Timestamp lastUpdated)
	{
		model.addRow( new Object[] { name, ID, quantity, expiration, category, notes } );
		ArrayList<Object> addList = new ArrayList<Object>();
		addList.add(name);
		addList.add(quantity);
		addList.add(expiration);
		addList.add(lastUpdated);
		addList.add(lastUpdated);
		addList.add(0);
		addList.add(notes);
		addList.add(category);        
	    //db.insertItem(addList);	
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
	
	//this method is used to delete an entry from the database
	//it checks if a row is selected, and then deletes the selected row from
	//the database and the data table
	public void deleteRowBySelection()
	{
		if(!this.getSelectionModel().isSelectionEmpty())
		{
			//db.deleteItemByID( (int)this.getValueAt(this.getSelectedRow(), 1));
			model.removeRow(this.getSelectedRow());
			this.getSelectionModel().clearSelection(); 
			return;
		}
		JOptionPane.showMessageDialog(this, "Please select a row to delete");
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
                
     	//	db.updateItemByID(updateList, ID);
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
	
	//sets cellUpdate to be true when a cell is selected
	public void allowEditCells()
	{
		if(!this.getSelectionModel().isSelectionEmpty())
		{
		    cellUpdate = true;
		}
	}
	
	//updates the database with input entries for the selected row
	//
	public void updateRowBySelection(Timestamp lastUpdated)
	{
		ArrayList<Object> updateList = new ArrayList<Object>();

		if( cellUpdate = true )
		{
			cellUpdate = false;
		}
		    currentRow = 00;
			JTable table = this;
		    int ID = (int)this.getValueAt( previous, 1 );
			updateList.add( this.getValueAt( previous , 0 ) );
			updateList.add( this.getValueAt( previous , 2 ) );
			updateList.add( this.getValueAt( previous , 3 ) );
			updateList.add(lastUpdated);
			updateList.add(0);
			updateList.add( this.getValueAt( previous , 5 ) );
			updateList.add( this.getValueAt( previous , 4 ) );
			//db.updateItemByID(updateList, ID);
		
	}
	
	//Sets selected rows to have a background and foreground color
   public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
  	        Component c = super.prepareRenderer(renderer, row, col);
  	        if (this.isCellSelected(row, col)) {
  	            c.setBackground(Color.BLUE);
  	            c.setForeground(Color.WHITE);
  	        }
  	        else
  	        {
  	        	   c.setForeground(Color.BLACK);
  	        }
  	        return c;
    }
	
	//creates a search filter for searching the table
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
