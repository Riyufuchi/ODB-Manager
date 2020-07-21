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
        for(int i = 0; i < ID.length; i++)
        {
        	contentPane.add(ID[i], getGBC(0, i + 1));
        	contentPane.add(Name[i], getGBC(1, i + 1));
        	contentPane.add(Surrname[i], getGBC(2, i + 1));
        	contentPane.add(Email[i], getGBC(3, i + 1));
        	contentPane.add(BedsInRoom[i], getGBC(4, i + 1));
        	contentPane.add(From[i], getGBC(5, i + 1));
        	contentPane.add(To[i], getGBC(6, i + 1));
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
    	List guests1 = CJPA.getCJPA().getGuestList();
    	List guests = CJPA.getCJPA().getGuestList();
    	ID = new JTextField[guests.size()];
    	Name = new JTextField[guests.size()];
    	Surrname = new JTextField[guests.size()];
    	Email = new JTextField[guests.size()];
    	BedsInRoom = new JTextField[guests.size()];
    	From = new JTextField[guests.size()];
        To = new JTextField[guests.size()];
    	for(int i = 0; i < ID.length; i++)
    	{
    		ID[i] = new JTextField();
    		ID[i].setEnabled(false);
    		ID[i].setText(Integer.toString(((Guest)guests.get(i)).getID()));
    		ID[i].setFont(mainFont);
    		Name[i] = new JTextField();
    		Name[i].setEnabled(false);
    		Name[i].setText(((Guest)guests.get(i)).getName());
    		Name[i].setFont(mainFont);
    		Surrname[i] = new JTextField();
    		Surrname[i].setEnabled(false);
    		Surrname[i].setText(((Guest)guests.get(i)).getSurrname());
    		Surrname[i].setFont(f);
    		Email[i] = new JTextField();
    		Email[i].setEnabled(false);
    		Email[i].setText(((Guest)guests.get(i)).getGuestEmail());
    		Email[i].setFont(f);
    		BedsInRoom[i] = new JTextField();
    		BedsInRoom[i].setEnabled(false);
    		BedsInRoom[i].setText(Integer.toString(((Guest)guests.get(i)).getBedsInRoom()));
    		BedsInRoom[i].setFont(mainFont);
    		From[i] = new JTextField();
    		From[i].setEnabled(false);
    		From[i].setText(((Guest)guests.get(i)).getDatumPrijezdu());
    		From[i].setFont(mainFont);
    		To[i] = new JTextField();
            To[i].setEnabled(false);
            To[i].setText(((Guest)guests.get(i)).getDatumOdjezdu());
            To[i].setFont(mainFont);
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