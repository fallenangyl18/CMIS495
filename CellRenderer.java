package inventory;

/*******************************REVISION HISTORY******************************************
 * Version 1.0
 * Created by Sumit Malhotra 02/22/2018
 * This is a support class for the InventoryTable.java class
 * for utilizing the cell renderer to support coloring rows based on 
 * expiration date
 * Create getTAbleCellRenderComponent method and comparisons for dates within cells
 * 
 * 
 * 
 * 
 * 
 **************************************************************************************/


import java.awt.Color;
import java.awt.Component;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class CellRenderer extends DefaultTableCellRenderer
{
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, 
    	    		boolean hasFocus, int row, int column)
    	    {
    	    	    
    	    	        DateTimeFormatter dt = DateTimeFormatter.ofPattern("MM-dd-YYYY");
                LocalDateTime now = LocalDateTime.now();
                LocalDateTime fourDays = now.plusDays(4);
                String date = dt.format(now);
                String fourDaysDate = dt.format(fourDays);
               
    	    	        
                Component cell =  super.getTableCellRendererComponent( table, value, isSelected, hasFocus, row, column );
    	    	        
    	    	            int modelRow = table.getRowSorter().convertRowIndexToModel(row);
                    String s = table.getModel().getValueAt(modelRow, 3).toString();
    	    	           
                    
 		       	     if( (s.compareTo(fourDaysDate) < 0 || s.compareTo(fourDaysDate) == 0)  && s.compareTo(date) > 0  )
 		       	    	 {
 		       	    		 cell.setBackground(Color.YELLOW);
 		       	    	 }
 		       	    	 else if( s.compareTo(date) == 0 || s.compareTo(date) < 0 )
 		       	    	 {
 		       	    		 cell.setBackground(Color.RED);
 		       	    	 } 
 		       	    	 else
 		       	    	 {
 		       	         cell.setBackground(Color.WHITE);
 		       	    	 }
 		       	     
    	                 return cell;
    	    } 	
}
