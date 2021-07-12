package Forms;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import JPA.CJPA;
import Utils.FinalValues;
import Utils.Helper;

/**
 * Copyright Header
 * 
 * Project: ODB Manager
 * Created On: 21.07.2020
 * Last Edit: 12.06.2021
 * @author Riyufuchi
 * @version 1.3
 * @since 1.0
 */

@SuppressWarnings("serial")
public class Counter extends JFrame 
{
    private JButton button1, button2;
    private JPanel contentPane;
    private DataTableForm dtf;
    private JLabel[] label;
    private String[] labelTexts = {"Bank accout:", "Paypal:", "Cash:", "Depths:", "Owns:", "Date:"};
    private GridBagConstraints gbc;
    private boolean justAdd, saveToDB;
    private CJPA odb;
    private JTextField[] textfield;
    
    public Counter(boolean justAdd, DataTableForm dtf)
    {
        this.setTitle("Counter");
        this.justAdd = justAdd;
        this.dtf = dtf;
        this.setSize(400,300);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        setUp();
        this.setAlwaysOnTop(true);
        this.add(contentPane);
        this.pack();
        this.setVisible(true);
    }
    
    private void setUp()
    {
    	pripojitDatabazi();
    	nastavitOvladaciPrvky();
        vytvoritUdalosti();
    	saveToDB = true;
    }
    
    private void pripojitDatabazi()
    {
    	odb = CJPA.getCJPA();
    	if(!odb.getCurrDatabaseName().equals("null"))
    	{
    		odb.connectToDB(odb.getCurrDatabaseName());
    		this.setTitle(odb.getCurrDatabaseName());
    	}
    	else
    	{
    		this.setTitle("No database is connected");
    	}
    }
    
	private void nastavitOvladaciPrvky()
    {
    	contentPane = new JPanel(null);
        contentPane.setBackground(FinalValues.DEFAULT_PANE_BACKGROUND);
        contentPane.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
    	button1 = new JButton();
        button1.setBackground(FinalValues.DEFAULT_BUTTON_BACKGROUND);
        button1.setText("Save to Database");
        button2 = new JButton();
        button2.setBackground(FinalValues.DEFAULT_BUTTON_BACKGROUND);
        button2.setText("Close");
        if(justAdd)
        {
        	textfield = new JTextField[2];
        	textfield[0] = new JTextField();
        	textfield[0].setName("Sum");
        	textfield[1] = new JTextField();
        	textfield[1].setName("Date");
        	label = new JLabel[2];
        	label[0] = new JLabel("Sum:");
        	label[1] = new JLabel("Date:");
	        for(int i = 0; i < label.length; i++)
	        {
	        	contentPane.add(label[i], Helper.getGBC(gbc, 0, i + 1));
	        }
	        for(int i = 0; i < textfield.length; i++)
	        {
	        	contentPane.add(textfield[i], Helper.getGBC(gbc, 1, i + 1));
	        }
        }
        else
        {
	        vytvorLabely();
	        textfield = new JTextField[labelTexts.length];
	        for(int i = 0; i < textfield.length; i++)
	        {
	        	textfield[i] = new JTextField();
	        	textfield[i].setName(labelTexts[i]);
	        	contentPane.add(textfield[i], Helper.getGBC(gbc, 1, i + 1));
	        }
	        textfield[5].setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        }
        contentPane.add(button1, Helper.getGBC(gbc, 1, 7));
        contentPane.add(button2, Helper.getGBC(gbc, 0, 7));
    }
    
    private void vytvorLabely()
    {
    	label = new JLabel[labelTexts.length];
    	for(int i = 0; i < labelTexts.length; i++)
    	{
    		label[i] = new JLabel();
    		label[i].setText(labelTexts[i]);
    		contentPane.add(label[i], Helper.getGBC(gbc, 0, i + 1));
    	}
    }
   
    private void check(String text, int id, JComponent control)
    {
    	if(!text.equals(""))
    	{
    		if(text.contains(","))
    		{
    			text.replace(",", ".");
    		}
    		String number = "";
    		boolean dot = false;
    		for(int i = 0; i < text.length(); i++)
        	{
    			if(Character.isDigit(text.charAt(i)))
	        	{
    				number = number + text.charAt(i);
	        	}	
	        	if(!dot)
	    		{
	        		if(text.charAt(i) == '.')
		        	{
	        			number = number + text.charAt(i);
		        		dot = true;
		        	}
	    		}
    		}
    		textfield[id].setText(number);
    	}
    	else
    	{
    		new ErrorWindow("Wrong value entered", "Error at: " + textfield[id].getName() + " Invalid or Missing value");
    		makeRedBorder(control);
    	}
    }
    
    private void makeRedBorder(JComponent control)
    {
    	saveToDB = false;
    	control.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
    }
    
    private void undoneRedBorder()
    { 
    	for(int i = 0; i < textfield.length; i++)
    	{
    		Helper.makeBorder(textfield[i], Helper.defaultTextFieldBorder());
    	}
    	saveToDB = true;
    }
    
    private void checkDataPersistance()
    {
    	if(justAdd)
    	{
    		for(int i = 0; i < textfield.length; i++)
	    	{
	    		check(textfield[i].getText(), i, textfield[i]);
	    	}
    		if(saveToDB)
	    	{
	    		odb.addMoney(Double.parseDouble(textfield[0].getText()), textfield[1].getText());
	    		new ErrorWindow("Zapis", "Uspesne zapsano do databaze.");
	    	}
    	}
    	else
    	{
	    	for(int i = 0; i < textfield.length; i++)
	    	{
	    		check(textfield[i].getText(), i, textfield[i]);
	    	}
	    	double money = 0;
	    	for(int i = 0; i < textfield.length - 1; i++)
	    	{
	    		money = money + Double.parseDouble(textfield[i].getText());
	    	}
	    	if(saveToDB)
	    	{
	    		odb.addMoney(money, textfield[textfield.length-1].getText());
	    		new ErrorWindow("Zapis", "Uspesne zapsano do databaze.");
	    	}
    	}
    	dtf.nastavitOvladaciPrvky();
    }
    
    private void vytvoritUdalosti()
    {
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