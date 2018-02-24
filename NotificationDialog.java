package Inventory;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class NotificationDialog extends JDialog implements ActionListener 
{
	private InventoryDatabase db;
	private ResultSet rs;
	private JTable table;
	private JPanel contentPanel;
	private JPanel summaryPanel;
	private JLabel expiringSoon = new JLabel("Total items expiring in the next 3 days:");
	private JLabel expiringNow = new JLabel("Total items expiring today:");
	private JLabel expired = new JLabel("Total expired items: ");
	//TODO: add the totals objects
	private JButton close = new JButton("Close");
	
	NotificationDialog () throws SQLException {
		
		super();
		
	    Date currentDate = new Date(System.currentTimeMillis());
		rs = db.getItemsByExpireDate(currentDate);
		table = new JTable(buildTableModel(rs));
		
		//TODO: Add sort by expiration
		
		contentPanel = new JPanel();
		contentPanel.setLayout(new BorderLayout());
		contentPanel.add(table, BorderLayout.CENTER);
		
		//TODO: Optional (wish list), separate into three tables by category
		
		summaryPanel = new JPanel();
		summaryPanel.setLayout(new BorderLayout());
		summaryPanel.add(expiringSoon, BorderLayout.WEST);
		summaryPanel.add(expiringNow, BorderLayout.WEST);
		summaryPanel.add(expired, BorderLayout.WEST);
		
		//TODO: Add counts of data
		/*
		summaryPanel.add(, BorderLayout.EAST);
		summaryPanel.add(, BorderLayout.EAST);
		summaryPanel.add(, BorderLayout.EAST);
		 */
		
		close.addActionListener(this);
		summaryPanel.add(close, BorderLayout.SOUTH);
		
		contentPanel.add(summaryPanel, BorderLayout.SOUTH);
		add(contentPanel);
		pack();
	}
	
	public static DefaultTableModel buildTableModel(ResultSet rs) throws SQLException 
	{

		ResultSetMetaData metaData = rs.getMetaData();
		
		Vector<String> columnNames = new Vector<String>();
		int columnCount = metaData.getColumnCount();
		for (int column = 1; column <= columnCount; column++)
		{
			columnNames.add(metaData.getColumnName(column));
		}

		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		while (rs.next())
		{
			Vector<Object> vector = new Vector<Object>();
			for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++)
			{
				vector.add(rs.getObject(columnIndex));
			}
			data.add(vector);
		}
		
		return new DefaultTableModel(data, columnNames);
		
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		dispose();
	}
	
}