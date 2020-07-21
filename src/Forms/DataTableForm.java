package Forms;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import JPA.CJPA;
import JPA.Guest;

/*
 * Copyright Header
 * 
 * Projetct: ODB Manager
 * Created On: 13.07.2020
 * Last Edit: 21.07.2020
 * Created By: Riyufuchi
 * 
 */


@SuppressWarnings("serial")
public class DataTableForm extends JFrame
{
	private JLabel[] label;
	private String[] labelTexts = {"ID", "Name", "Surrname", "Email", "Bed count:", "From", "To"};
	private String inputFormName;
	
	private Font mainFont;
	private JPanel contentPane;
	private JScrollPane scrollPane;
	private JMenuBar menuBar;
    private JMenu file;
    private GridBagConstraints gbc;
    private JTextField[][] textfield;
    
    public DataTableForm(String inputFormName)
    {
    	this.inputFormName = inputFormName;
    	this.setTitle("Table" + " - " + CJPA.getCJPA().getCurrDatabaseName());
        this.setSize(400,300);
        this.setMinimumSize(new Dimension(400, 300));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        mainFont = new Font("Dialog.plain", Font.BOLD, 14);
        contentPane = new JPanel(null);
        contentPane.setBackground(new Color(192,192,192));
        contentPane.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        generujMenu();
        nastavitOvladaciPrvky();
        contentPane.revalidate();
        scrollPane = new JScrollPane(contentPane);
        this.setJMenuBar(menuBar);
        this.add(scrollPane);
        this.pack();
        this.setVisible(true);
    }
    
    public void generujMenu()
    {
        menuBar = new JMenuBar();
        file = new JMenu("Operace");
        JMenuItem menuItem = new JMenuItem("Odstranit zaznam");
        JMenuItem menuItem2 = new JMenuItem("Refresh");
        menuItem.addActionListener(new ActionListener() 
    	{
            public void actionPerformed(ActionEvent evt) 
            {
            	/*
            	int[] id = new int[ID.length];
            	for(int i = 0; i < ID.length; i++)
            	{
            		id[i] = Integer.parseInt(ID[i].getText());
            	}
            	Operator op = new Operator("Vyberte ID", id);
            	*/
            }
        });
        menuItem2.addActionListener(new ActionListener() 
    	{
            public void actionPerformed(ActionEvent evt) 
            {
            	nastavitOvladaciPrvky();
            	prekresli();
            }
        });
        file.add(menuItem);
        file.add(menuItem2);
        menuBar.add(file);
    }
    
    private void prekresli()
    {
    	DataTableForm dtf = new DataTableForm(inputFormName);
    	this.dispose();
    }
    
    private void nastavitOvladaciPrvky()
    {
    	/*
    	contentPane = new JPanel(null);
        contentPane.setBackground(new Color(192,192,192));
        contentPane.setLayout(new GridBagLayout());
        */
        gbc = new GridBagConstraints();
        setUpLabels();
        for(int i = 0; i < labelTexts.length; i++)
        {
        	contentPane.add(label[i], getGBC(i, 0));
        }
        setUpTextfield();
        for(int x = 0; x < textfield.length; x++)
        {
	        for(int i = 0; i < textfield[0].length; i++)
		    {
	        	contentPane.add(textfield[x][i], getGBC(i, x + 1));
		    }
        }
    }
    
    private void setUpLabels()
    {
    	label = new JLabel[labelTexts.length];
    	for(int i = 0; i < labelTexts.length; i++)
    	{
    		label[i] = new JLabel();
    		label[i].setFont(mainFont);
    		label[i].setText(labelTexts[i]);
    	}
    }
    
    private void setUpTextfield()
    {
    	List guests1 = CJPA.getCJPA().getList("SELECT c FROM Guests c");
    	List data = CJPA.getCJPA().getList("SELECT c FROM Guest c");
    	textfield = new JTextField[data.size()][labelTexts.length];
    	String[] listData;
    	for(int x = 0; x < textfield.length; x++)
        {
    		listData = ((Guest)data.get(x)).getDataArray();
	        for(int i = 0; i < textfield[0].length; i++)
		    {
	        	textfield[x][i] = new JTextField();
	        	textfield[x][i].setEnabled(false);
	        	textfield[x][i].setText(listData[i]);
	        	textfield[x][i].setFont(mainFont);
		    }
        }
    }
    
	private GridBagConstraints getGBC(int x, int y)
    {
    	gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = x;
        gbc.gridy = y;
		return gbc;
    }
}