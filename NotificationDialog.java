package inventory;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

@SuppressWarnings("serial")
public class NotificationDialog extends JDialog implements ActionListener 
{
	private InventoryDatabase db;
	private ResultSet rs;
	private JTable table;
	private JPanel contentPanel;
	private JPanel summaryPanel;
	private JPanel westPanel;
	private JPanel eastPanel;
	private JLabel expiringSoon = new JLabel("Total items expiring in the next 3 days:");
	private JLabel expiringNow = new JLabel("Total items expiring today:");
	private JLabel expired = new JLabel("Total expired items: ");
	private JLabel countExpiringSoon = new JLabel();
	private JLabel countExpiringNow = new JLabel();
	private JLabel countExpired = new JLabel();
	private JButton close = new JButton("Close");
	
	NotificationDialog () throws SQLException {
		
		super();
		
	    Date currentDate = new Date(System.currentTimeMillis());
	    db = new InventoryDatabase();
		rs = db.getItemsByExpireDate(currentDate);
		
		table = new JTable(buildTableModel(rs));
		
		TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(table.getModel());
        table.setRowSorter(sorter);

        List<RowSorter.SortKey> sortKeys = new ArrayList<>(25);
        sortKeys.add(new RowSorter.SortKey(3, SortOrder.ASCENDING)); // Sort by Date as primary
        sortKeys.add(new RowSorter.SortKey(1, SortOrder.ASCENDING)); // Sort by Name as secondary
        sorter.setSortKeys(sortKeys);
		
		contentPanel = new JPanel();
		contentPanel.setLayout(new BorderLayout());
		JScrollPane scrollPane = new JScrollPane(table);
		contentPanel.add(scrollPane, BorderLayout.CENTER);
		
		summaryPanel = new JPanel();
		summaryPanel.setLayout(new BorderLayout());
		
		westPanel = new JPanel();
		westPanel.setLayout(new BoxLayout(westPanel, BoxLayout.Y_AXIS));
		westPanel.add(expired);
		westPanel.add(expiringNow);
		westPanel.add(expiringSoon);
		
		Date date;
		int cExpiringSoon = 0;
		int cExpiringNow = 0;
		int cExpired = 0;
		
		for (int i = 0; i < table.getRowCount(); i++) {
			try {
				date = new Date(new SimpleDateFormat("MM/dd/yyyy").parse((String) table.getValueAt(i, 3)).getTime());
				
				if (date.toString().matches(currentDate.toString())) {
					cExpiringNow++;
				} else if (date.before(currentDate)) {
					cExpired++;
				} else {
					cExpiringSoon++;
				}
				
				
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		countExpiringSoon.setText(Integer.toString(cExpiringSoon));
		countExpiringNow.setText(Integer.toString(cExpiringNow));
		countExpired.setText(Integer.toString(cExpired));
		
		eastPanel = new JPanel();
		eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));
		eastPanel.add(countExpired);
		eastPanel.add(countExpiringNow);
		eastPanel.add(countExpiringSoon);
		
		summaryPanel.add(westPanel, BorderLayout.WEST);
		summaryPanel.add(eastPanel, BorderLayout.EAST);
		
		close.addActionListener(this);
		summaryPanel.add(close, BorderLayout.SOUTH);
		
		contentPanel.add(summaryPanel, BorderLayout.SOUTH);
		add(contentPanel);
		
		setPreferredSize(new Dimension(600, 400));
		setTitle(getClass().getSimpleName());
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setModal(true);
        
        
		pack();
		setVisible(true);
		
	}
	
	public static DefaultTableModel buildTableModel(ResultSet rs) throws SQLException 
	{

        ResultSetMetaData metaData = rs.getMetaData();

        // names of columns
        Vector<String> columnNames = new Vector<String>();
        int columnCount = metaData.getColumnCount();
        for (int column = 1; column <= columnCount; column++) {
            columnNames.add(metaData.getColumnName(column));
        }

        // data of the table
        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        while (rs.next()) {
            Vector<Object> vector = new Vector<Object>();
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
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