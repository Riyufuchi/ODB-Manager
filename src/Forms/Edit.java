package Forms;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

import JPA.CJPA;
import JPA.Money;

/*
 * Copyright Header
 * 
 * Projetct: ODB Manager
 * Created On: 23.07.2020
 * Last Edit: 03.05.2021
 * Created By: Riyufuchi
 * 
 */

@SuppressWarnings("serial")
public class Edit extends JFrame 
{
    private JButton button1;
    private JButton button2;
    private JPanel contentPane;
    private JLabel[] label;
    private String[] labelTexts = {"Item ID:", "Amout of money:", "Date:"};
    private DataTableForm dtf;
    private JComboBox comboBox;
    private JTextField[] textfield;
    private GridBagConstraints gbc;
    private boolean saveToDB;
    private Border borderTextfield;
    private CJPA odb;
    
    public Edit(DataTableForm dtf)
    {
        this.setTitle("Edit");
        this.setSize(400,300);
        this.dtf = dtf;
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        setUp();
        this.add(contentPane);
        this.pack();
        this.setVisible(true);
    }
    
    
    private void setUp()
    {
    	odb = CJPA.getCJPA();
    	nastavitOvladaciPrvky();
    	vytvoritUdalosti();
    	saveToDB = true;
    }
    
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void nastavitOvladaciPrvky()
    {
         contentPane = new JPanel(null);
         contentPane.setBackground(new Color(192,192,192));
         contentPane.setLayout(new GridBagLayout());
    	 button1 = new JButton();
         button1.setBackground(new Color(214,217,223));
         button1.setForeground(new Color(0,0,0));
         button1.setText("Save to Database");
         button2 = new JButton();
         button2.setBackground(new Color(214,217,223));
         button2.setForeground(new Color(0,0,0));
         button2.setText("Close");
         vytvorLabely();
         comboBox = new JComboBox();
         comboBox.setBackground(new Color(214,217,223));
         for(int i = 0; i < dtf.getData().size(); i++)
         {
        	 comboBox.addItem(Integer.toString(((Money)dtf.getData().get(i)).getID()));
         }
	     textfield = new JTextField[labelTexts.length - 1];
	     for(int i = 0; i < textfield.length; i++)
	     {
	    	 textfield[i] = new JTextField();
	    	 textfield[i].setName(labelTexts[i]);
	     }
	     borderTextfield = textfield[0].getBorder(); 
         gbc = new GridBagConstraints();
         contentPane.add(comboBox, getGBC(1, 0));
         for(int i = 0; i < labelTexts.length; i++)
         {
         	contentPane.add(getLabely(i), getGBC(0, i));
         }
         for(int i = 0; i < textfield.length; i++)
         {
        	 contentPane.add(textfield[i], getGBC(1, i + 1));
         }
         contentPane.add(button1, getGBC(1, 7));
         contentPane.add(button2, getGBC(0, 7));
    }
    
    private JLabel getLabely(int i)
    {
    	return label[i];
    }
    
    private void vytvorLabely()
    {
    	label = new JLabel[labelTexts.length];
    	for(int i = 0; i < labelTexts.length; i++)
    	{
    		label[i] = new JLabel();
    		label[i].setText(labelTexts[i]);
    	}
    }
    
    private void checkFormat(String text, int id, JComponent control)
    {
    	if(!text.equals(""))
    	{
    		String number = "";
    		for(int i = 0; i < text.length(); i++)
        	{
    			if(Character.isDigit(text.charAt(i)))
	        	{
    				number = number + text.charAt(i);
	        	}
    			else if(text.charAt(i) == ',')
		        {
	        		number = number + '.';
		        }
	    	}
    		textfield[id].setText(number);
    	}
    	else
    	{
    		new ErrorWindow("Chybnì zadaná hodnota", "Error at: " + textfield[id].getName() + " Invalid or Missing value");
    		makeRedBorder(control);
    	}
    }
    
    private void makeRedBorder(JComponent control)
    {
    	saveToDB = false;
    	control.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
    }
    
    private void makeDefaultBorder(JComponent control, Border border)
    {
    	control.setBorder(border);
    }
    
    private void undoneRedBorder()
    { 
    	for(int i = 0; i < textfield.length; i++)
    	{
    		if(i < textfield.length)
    		{
    			makeDefaultBorder(textfield[i], borderTextfield);
    		}
    	}
    	saveToDB = true;
    }
    
    private GridBagConstraints getGBC(int x, int y)
    {
    	gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = x;
        gbc.gridy = y;
		return gbc;
    }
    
    private void checkIfNull(String text, JComponent control)
    {
    	if(text.equals(""))
    	{
    		makeRedBorder(control);
    	}
    }
    
    private void checkDataPersistance()
    {
    	for(int i = 0; i < textfield.length; i++)
	    {
    		if(i == 0)
    		{
    			checkFormat(textfield[i].getText(), i, textfield[i]);
    		}
    		else
    		{
    			checkIfNull(textfield[i].getText(), textfield[i]);
    		}
	    }
    	if(saveToDB)
	    {
    		odb.editData(comboBox.getSelectedIndex() + 1, Double.parseDouble(textfield[0].getText()), textfield[1].getText());
	    	new ErrorWindow("Edit", "Uspesne aktualizovano.");
	    }
    	dtf.nastavitOvladaciPrvky();
    }
    
    private void vytvoritUdalosti()
    {
    	comboBox.addActionListener(new ActionListener() 
    	{
            public void actionPerformed(ActionEvent evt) 
            {
            	textfield[0].setText(Double.toString(((Money)dtf.getData().get(comboBox.getSelectedIndex())).getMoneySum()));
            	textfield[1].setText(((Money)dtf.getData().get(comboBox.getSelectedIndex())).getDate());
            }
        });
    	button1.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent evt) 
            {
            	undoneRedBorder();
            	checkDataPersistance();
            }
        });
    	button2.addActionListener(new ActionListener() 
    	{
            public void actionPerformed(ActionEvent evt) 
            {
            	System.exit(0);
            }
        });
    }
}