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
 * VERSION 1.2
 * Updated by Elizabeth Ruzich, 2/23/18. Added "Grains" progress bar per Fred's request
 * for a Grains category. Added progress bar, label, added to panel. Also noticed that
 * Liquids was not added onto the table at all, so I added that back in. I set all
 * values back to zero, as they will be populated and the progress bars will grow
 * as the database is updated.
 *
 *
 *version 1.3
 * Edited 02/28/2018 by Sumit Malhotra, creates a panel with a new format with borders, new grid
 * and layout arrangements. Updated to support result sets from database to update category
 * JLabels, JProgressBars, and to calculate percentages for inventory totals
 *
 **************************************************************************************/

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.util.*;
import java.sql.*;
import java.util.*;

public class DataPanel extends JPanel
{
    private ResultSet rs = null;
    private InventoryDatabase db = new InventoryDatabase();

    private JLabel totalInventory;
    private JLabel producelbl;
    private JLabel meatlbl;
    private JLabel dairylbl;
    private JLabel nonperishablelbl;
    private JLabel grainslbl;
    private JLabel liquidslbl;
    private JProgressBar produceJPB;
    private JProgressBar meatJPB;
    private JProgressBar dairyJPB;
    private JProgressBar nonperishableJPB;
    private JProgressBar liquidsJPB;
    private JProgressBar grainsJPB;

    private JPanel produce;
    private JPanel meat;
    private JPanel dairy;
    private JPanel nonperishable;
    private JPanel grains;
    private JPanel liquids;

    private int total;

    JPanel foodGroupPanel = new JPanel();

    public DataPanel()
    {
        super();

        UIManager.put("ProgressBar.selectionForeground", Color.GREEN);
        db.init();
        produceJPB = new JProgressBar(0 , 100);
        meatJPB = new JProgressBar(0 , 100);
        dairyJPB = new JProgressBar(0 , 100);
        nonperishableJPB = new JProgressBar(0 , 100);
        grainsJPB = new JProgressBar(0, 100);
        liquidsJPB = new JProgressBar(0 , 100);

        this.setBorder(BorderFactory.createLoweredBevelBorder());
        foodGroupPanel.setLayout(new GridLayout(3, 2));

        totalInventory = new JLabel("Total Inventory:");
        totalInventory.setFont(new Font("default", Font.BOLD, 18));
        totalInventory.setHorizontalAlignment(SwingConstants.LEFT);

        produce = new JPanel();
        produce.setLayout(new GridLayout(1,3));
        producelbl = new JLabel("Produce:  ");
        produceJPB.setValue(0);
        produceJPB.setForeground(Color.GREEN);
        produce.setBorder(new EmptyBorder(5, 5, 5, 5));
        produce.add(producelbl);
        produce.add(produceJPB);


        meat = new JPanel();
        meat.setLayout(new GridLayout(1,3));
        meatJPB.setValue(0);
        meatJPB.setForeground(Color.GREEN);
        meatlbl = new JLabel("Meat:   ");
        meat.setBorder(new EmptyBorder(5, 5, 5, 5));
        meat.add(meatlbl);
        meat.add(meatJPB);

        dairy = new JPanel();
        dairy.setLayout(new GridLayout(1,3));
        dairyJPB.setValue(0);
        dairyJPB.setForeground( Color.GREEN );
        dairylbl = new JLabel("Dairy:    ");
        dairy.setBorder(new EmptyBorder(5, 5, 5, 5));
        dairy.add(dairylbl);
        dairy.add(dairyJPB);

        nonperishable = new JPanel();
        nonperishable.setLayout(new GridLayout(1,3));
        nonperishableJPB.setValue(0);
        nonperishableJPB.setForeground( Color.GREEN );
        nonperishablelbl = new JLabel("Non-perishable:  ");
        nonperishable.setBorder(new EmptyBorder(5, 5, 5, 5));
        nonperishable.add(nonperishablelbl);
        nonperishable.add(nonperishableJPB);

        grains = new JPanel();
        grains.setLayout(new GridLayout(1,3));
        grainsJPB.setValue(0);
        grainsJPB.setForeground(Color.GREEN);
        grainslbl = new JLabel("Grains:  ");
        grains.setBorder(new EmptyBorder(5, 5, 5, 5));
        grains.add(grainslbl);
        grains.add(grainsJPB);

        liquids = new JPanel();
        liquids.setLayout(new GridLayout(1,3));
        liquidsJPB.setValue(0);
        liquidsJPB.setForeground( Color.GREEN);
        liquidslbl = new JLabel("Liquids:    ");
        liquids.setBorder(new EmptyBorder(5, 5, 5, 5));
        liquids.add(liquidslbl);
        liquids.add(liquidsJPB);



        this.add(totalInventory);
        foodGroupPanel.add(produce);
        foodGroupPanel.add(meat);
        foodGroupPanel.add(dairy);
        foodGroupPanel.add(nonperishable);
        foodGroupPanel.add(grains);
        foodGroupPanel.add(liquids);

        //countTotalInventory();
        // countFoodGroup();

        this.add(foodGroupPanel);

    }

    public void countTotalInventory()
    {
        try
        {
            rs = db.tableQuantityByTotal();
            total = rs.getInt(0);
            totalInventory.setText("Total Inventory: " + total);
            this.revalidate();
            this.repaint();
        }
        catch (SQLException e)
        {
            // TODO Auto-generated catch block
            //   e.printStackTrace();
        }
        catch(NullPointerException e)
        {

        }
        finally
        {

        }
    }

    public void countFoodGroup()
    {

        int meatCount =0;
        int produceCount = 0;
        int dairyCount = 0;
        int nonperishableCount = 0;
        int grainsCount = 0;
        int liquidsCount = 0;

        try
        {
            rs = db.tableQuantityByCategory();
            meatCount = rs.getInt("Meat");
            produceCount = rs.getInt("Produce");
            dairyCount = rs.getInt("Dairy");
            nonperishableCount = rs.getInt("Nonperishable");
            grainsCount = rs.getInt("Grains");
            liquidsCount = rs.getInt("Liquids");

            produceJPB.setValue((int)(produceCount/total*100));
            producelbl.setText("Produce: " + (produceCount/total*100) );
            produce.revalidate();
            produce.repaint();

            meatJPB.setValue(meatCount/total);
            meatlbl.setText("Meat: " + meatCount/total);
            meat.revalidate();
            meat.repaint();

            dairyJPB.setValue((int)(dairyCount/total*100));
            dairylbl.setText("Dairy: " + (dairyCount/total*100) );
            dairy.revalidate();
            dairy.repaint();

            nonperishableJPB.setValue((int)(nonperishableCount/total*100));
            nonperishablelbl.setText("Nonperishable: " + (nonperishableCount/total*100) );
            nonperishable.revalidate();
            nonperishable.repaint();

            grainsJPB.setValue((int)(grainsCount/total*100));
            grainslbl.setText("Grains: " + (grainsCount/total*100) );
            grains.revalidate();
            grains.repaint();

            liquidsJPB.setValue((int)(liquidsCount/total*100));
            liquidslbl.setText("Liquids: " + (liquidsCount/total*100));
            liquids.revalidate();
            liquids.repaint();

        }
        catch (SQLException e)
        {
            // TODO Auto-generated catch block
            //e.printStackTrace();
        }
        catch(NullPointerException e)
        {

        }
        finally
        {

        }
    }

}
