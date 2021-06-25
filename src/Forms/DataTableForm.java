package Forms;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import JPA.CJPA;
import JPA.Money;
import Utils.FinalValues;
import Utils.Helper;
import Utils.JMenuAutoCreator;

/**
 * Copyright Header
 * 
 * Project: ODB Manager
 * Created On: 13.07.2020
 * Last Edit: 07.06.2021
 * @author Riyufuchi
 * @version 1.4
 * @since 1.0
 */

@SuppressWarnings("serial")
public class DataTableForm extends JFrame
{
	private JLabel[] label;
	private String[] labelTexts = {"ID", "Money", "Date"};
	//private String inputFormName;
	private String getQuery;
	private JMenuAutoCreator mac;
	private CJPA odb;
	private Font mainFont;
	private List<?> data;
	private JPanel contentPane;
	private JScrollPane scrollPane;
    private GridBagConstraints gbc;
    private JTextField[][] textfield;
    private int lastMarkedLine;
    
    public DataTableForm(String inputFormName)
    {
    	//this.inputFormName = inputFormName;
        this.setMinimumSize(new Dimension(400, 300));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.gbc = new GridBagConstraints();
        this.gbc.fill = GridBagConstraints.HORIZONTAL;
        switch(inputFormName)
    	{
    		case "Neutral": getQuery = "SELECT c FROM Guest c"; break;
	    	case "Money": getQuery = "SELECT c FROM Money c"; break;
    	}
        connectDatabase();
        this.addWindowStateListener(new WindowAdapter()
        {
        	public void windowStateChanged(WindowEvent e)
        	{
        		borderCorrection();
        	}
        });
        this.addWindowListener(new WindowAdapter() 
        {
        	  public void windowClosing(WindowEvent we) 
        	  {
        		  odb.closeDB();
        	  }
        });
        this.pack();
        this.setVisible(true);
    }
    
    private void borderCorrection()
    {
    	if(textfield != null)
    	{
	    	for(int c = 0; c < 3; c++)
	    	{
	    		textfield[lastMarkedLine][c].setBorder(Helper.defaultTextFieldBorder());
	    	}
    	}
    }
    
    private void setUp()
    {
    	mainFont = new Font("Dialog.plain", Font.BOLD, 14);
        contentPane = new JPanel(null);
        contentPane.setBackground(FinalValues.DEFAULT_PANE_BACKGROUND);
        contentPane.setLayout(new GridBagLayout());
        generateMenu();
        contentPane.revalidate();
        scrollPane = new JScrollPane(contentPane);
        this.add(scrollPane);
    }
    
    private void connectDatabase()
    {
    	odb = CJPA.getCJPA();
    	odb.configFile();
    	if(!odb.getCurrDatabaseName().equals("null"))
    	{
    		odb.connectToDB(odb.getCurrDatabaseName());
    		this.setTitle("Table" + " - " + odb.getCurrDatabaseName());
    		setUp();
    	}
    	else
    	{
    		this.setTitle("No database is connected, please connect database.");
    		generateMenu();
    	}
    }
    
    /*
    private void connectDatabase(String databaseName)
    {
    	odb = CJPA.getCJPA();
    	if(!databaseName.equals("null"))
    	{
    		odb.connectToDB(databaseName);
    		this.setTitle("Table" + " - " + odb.getCurrDatabaseName());
    		setUp();
    	}
    	else
    	{
    		this.setTitle("Error, database wasn't connected, please check path/datbaseName and connect database.");
    	}
    }
    */
    
    private DataTableForm getDTF()
    {
    	return this;
    }
    
    public void generateMenu()
    {
    	String[] menu = {"Database", "Operations", "Tools", "Help"};
    	String[] menuItems = {"Create", "Connect", "Export", "Import", "Exit", "", "Count", "Add", "Edit", "Delete", "Refresh/Load","", "Highest savings", "Lowest savings", "", "About"};
    	mac = new JMenuAutoCreator(menu, menuItems, 4);
    	JMenuItem[] jmi = mac.getMenuItem();
    	for (int i = 0; i < jmi.length; i++)
    	{
    		switch (jmi[i].getName())
    		{
    			case "Count": 
    				jmi[i].addActionListener(new ActionListener() 
    		    	{
    		            public void actionPerformed(ActionEvent evt) 
    		            {
    		            	nastavitOvladaciPrvky();
    		            	prekresli();
    		            	new Counter(false, getDTF());
    		            }
    		        });
    			break;
    			case "Add": 
    				jmi[i].addActionListener(new ActionListener() 
    		    	{
    		            public void actionPerformed(ActionEvent evt) 
    		            {
    		            	nastavitOvladaciPrvky();
    		            	prekresli();
    		            	new Counter(true, getDTF());
    		            }
    		        });
    			break;
    			case "Edit": 
    				jmi[i].addActionListener(new ActionListener() 
    		    	{
    		            public void actionPerformed(ActionEvent evt) 
    		            {
    		            	nastavitOvladaciPrvky();
    		            	prekresli();
    		            	new Edit(getDTF());
    		            }
    		        });
    			break;
    			case "Create": 
    				jmi[i].addActionListener(new ActionListener() 
    		    	{
    		            public void actionPerformed(ActionEvent evt) 
    		            {		     
    		            	new DataConnectorForm("Create Database", false);
    		            }
    		        });
    			break;
    			case "Connect": 
    				jmi[i].addActionListener(new ActionListener() 
    		    	{
    		            public void actionPerformed(ActionEvent evt) 
    		            {		 
    		            	new DataConnectorForm("Connect Database", true);
    		            }
    		        });
    			break;
    			case "Delete": 
    				jmi[i].addActionListener(new ActionListener() 
    		    	{
    		            public void actionPerformed(ActionEvent evt) 
    		            {
    		            	nastavitOvladaciPrvky();
    		            	prekresli();
    		            	int[] ID = new int[textfield.length];
    		            	for(int i = 0; i < ID.length; i++)
    		            	{
    		            		ID[i] = Integer.parseInt(textfield[i][0].getText());
    		            	}
    		            	new Operator("Delete", ID);
    		            }
    		        });
    			break;
    			case "Refresh/Load": 
    				jmi[i].addActionListener(new ActionListener() 
    		    	{
    		            public void actionPerformed(ActionEvent evt) 
    		            {
    		            	nastavitOvladaciPrvky();
    		            	prekresli();
    		            }
    		        });
    			break;
    			case "About": 
    				jmi[i].addActionListener(new ActionListener() 
    		    	{
    		            public void actionPerformed(ActionEvent evt) 
    		            {
    		            	new ErrorWindow("About", "This is ODB database manager.\n"
    		            			+ "Version: " + FinalValues.VERSION + "\n"
    		            			+ "Created by Riyufuchi.\n"
    		            			+ "Free libs under OpenSource lincention are used (I thnink), however my code is not under OpenSource licention.");
    		            }
    		        });
    			break;
    			case "Exit": 
    				jmi[i].addActionListener(new ActionListener() 
    		    	{
    		            public void actionPerformed(ActionEvent evt) 
    		            {
    		            	System.exit(0);
    		            }
    		        });
    			break;
    			case "Highest savings": 
    				jmi[i].addActionListener(new ActionListener() 
    		    	{
    		            public void actionPerformed(ActionEvent evt) 
    		            {
    		            	nastavitOvladaciPrvky();	
    		            	highestSavings();
        		            prekresli();
    		            }
    		        });
    			break;
    			case "Lowest savings": 
    				jmi[i].addActionListener(new ActionListener() 
    		    	{
    		            public void actionPerformed(ActionEvent evt) 
    		            {
    		            	nastavitOvladaciPrvky();
    		            	lowestSavings();
        		            prekresli();
    		            }
    		        });
    			break;
    			case "Export": 
    				jmi[i].addActionListener(new ActionListener() 
    		    	{
    		            public void actionPerformed(ActionEvent evt) 
    		            {
    		            	new DatabaseFileIO("Export", getData());
    		            }
    		        });
    			break;
    			case "Import": 
    				jmi[i].addActionListener(new ActionListener() 
    		    	{
    		            public void actionPerformed(ActionEvent evt) 
    		            {
    		            	importDialog();
    		            }
    		        });
    			break;
    		}
    	}
    	mac.setMenuItem(jmi);
    	this.setJMenuBar(mac.getJMenuBar());
    }
    
    private void importDialog()
    {
    	new DatabaseFileIO("Import", this);
    }
    
    private void highestSavings()
    {
    	int index = 0;
    	double value = 0;
    	for(int i = 0; i < textfield.length; i++)
    	{
    		if((Double.parseDouble(textfield[i][1].getText())) > value)
    		{
    			index = i;
    			value = Double.parseDouble(textfield[i][1].getText());
    		}
    	}
    	this.lastMarkedLine = index;
    	for(int c = 0; c < 3; c++)
    	{
    		textfield[index][c].setBorder(BorderFactory.createLineBorder(Color.GREEN, 2));
    	}
    }
    
    private void lowestSavings()
    {
    	int index = 0;
    	double value = Double.parseDouble(textfield[0][1].getText());
    	for(int i = 0; i < textfield.length; i++)
    	{
    		if((Double.parseDouble(textfield[i][1].getText())) < value)
    		{
    			index = i;
    			value = Double.parseDouble(textfield[i][1].getText());
    		}
    	}
    	this.lastMarkedLine = index;
    	for(int c = 0; c < 3; c++)
    	{
    		textfield[index][c].setBorder(BorderFactory.createLineBorder(Color.ORANGE, 2));
    	}
    }
    
    private void prekresli()
    {
    	this.repaint();
    	this.pack();
    }
    
    public void nastavitOvladaciPrvky()
    {
        setUpLabels();
        setUpTextfield();
    }
    
    private void setUpLabels()
    {
    	label = new JLabel[labelTexts.length];
    	for(int i = 0; i < labelTexts.length; i++)
    	{
    		label[i] = new JLabel();
    		label[i].setFont(mainFont);
    		label[i].setText(labelTexts[i]);
    		contentPane.add(label[i], Helper.getGBC(gbc, i, 0));
    	}
    }
    
    private void setUpTextfield()
    {
    	data = odb.getList(getQuery);
    	if(data != null)
    	{
	    	textfield = new JTextField[data.size()][labelTexts.length];
	    	String[] listData;
	    	for(int x = 0; x < textfield.length; x++)
	        {
	    		listData = ((Money)data.get(x)).getDataArray();
		        for(int i = 0; i < textfield[0].length; i++)
			    {
		        	textfield[x][i] = new JTextField();
		        	textfield[x][i].setEnabled(false);
		        	textfield[x][i].setText(listData[i]);
		        	textfield[x][i].setFont(mainFont);
		        	contentPane.add(textfield[x][i], Helper.getGBC(gbc, i, x + 1));
			    }
	        }
    	}
    }
    
    public void loadData(List<Money> dataList)
    {
    	data = dataList;
    	if(data != null)
    	{
	    	textfield = new JTextField[data.size()][labelTexts.length];
	    	String[] listData;
	    	for(int x = 0; x < textfield.length; x++)
	        {
	    		listData = ((Money)data.get(x)).getDataArray();
		        for(int i = 0; i < textfield[0].length; i++)
			    {
		        	textfield[x][i] = new JTextField();
		        	textfield[x][i].setEnabled(false);
		        	textfield[x][i].setText(listData[i]);
		        	textfield[x][i].setFont(mainFont);
		        	contentPane.add(textfield[x][i], Helper.getGBC(gbc, i, x + 1));
			    }
	        }
    	}
    }
    
    @SuppressWarnings("unchecked")
	public List<Money> getData()
    {
    	return ((List<Money>)data);
    }
}