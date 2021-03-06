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

import JPA.CJPA;
import JPA.Money;
import Utils.FinalValues;
import Utils.Helper;

/**
 * Copyright Header
 * 
 * Project: ODB Manager
 * Created On: 23.07.2020
 * Last Edit: 25.06.2021
 * @author Riyufuchi
 * @version 1.2
 * @since 1.0
 */

@SuppressWarnings("serial")
public class Edit extends JFrame 
{
    private JButton button1, button2;
    private JPanel contentPane;
    private JLabel[] label;
    private String[] labelTexts = {"Item ID:", "Amout of money:", "Date:"};
    private DataTableForm dtf;
    private JComboBox<String> comboBox;
    private JTextField[] textfield;
    private GridBagConstraints gbc;
    private boolean saveToDB;
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
        this.setAlwaysOnTop(true);
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
         vytvorLabely();
         comboBox = new JComboBox();
         comboBox.setBackground(FinalValues.DEFAULT_BUTTON_BACKGROUND);
         for(int i = 0; i < dtf.getData().size(); i++)
         {
        	 comboBox.addItem(Integer.toString(((Money)dtf.getData().get(i)).getID()));
         }
	     textfield = new JTextField[labelTexts.length - 1];
	     for(int i = 0; i < textfield.length; i++)
	     {
	    	 textfield[i] = new JTextField();
	    	 textfield[i].setName(labelTexts[i]);
	    	 contentPane.add(textfield[i], Helper.getGBC(gbc, 1, i + 1));
	     }
         contentPane.add(comboBox, Helper.getGBC(gbc, 1, 0));
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
    		contentPane.add(label[i], Helper.getGBC(gbc, 0, i));
    	}
    }
    
    private void checkFormat(String text, int id, JComponent control)
    {
    	if(!text.equals(""))
    	{
    		textfield[id].setText(text.replace(',', '.'));
    	}
    	else
    	{
    		new ErrorWindow("Chybn� zadan� hodnota", "Error at: " + textfield[id].getName() + " Invalid or Missing value");
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
    		if(i < textfield.length)
    		{
    			Helper.makeBorder(textfield[i], Helper.defaultTextFieldBorder());
    		}
    	}
    	saveToDB = true;
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