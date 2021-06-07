package Forms;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
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
 * Created On: 13.07.2020
 * Last Edit: 07.06.2021
 * @author Riyufuchi
 * @version 1.3
 * @since 1.0 
 */

@SuppressWarnings("serial")
public class DataConnectorForm extends JFrame implements KeyListener
{
    private JButton button1, button2;
    private JPanel contentPane;
    private JLabel[] label;
    private String[] labelTexts = {"Akce:", "Nazev natabaze:"};
    private GridBagConstraints gbc;
    private JComboBox<String> comboBox;
    private JTextField textfield;
    
    public DataConnectorForm(String nazevOkna, boolean allowConnection)
    {
        this.setTitle(nazevOkna);
        this.setSize(400,300);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.gbc = new GridBagConstraints();
        this.gbc.fill = GridBagConstraints.HORIZONTAL;
        nastavitOvladaciPrvky(allowConnection);
        vytvoritUdalosti();
        this.setAlwaysOnTop(true);
        this.add(contentPane);
        this.pack();
        this.setVisible(true);
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	private void nastavitOvladaciPrvky(boolean allowConnection)
    {
         contentPane = new JPanel(null);
         contentPane.setBackground(FinalValues.DEFAULT_PANE_BACKGROUND);
         contentPane.setLayout(new GridBagLayout());
    	 button1 = new JButton();
         button1.setBackground(FinalValues.DEFAULT_BUTTON_BACKGROUND);
         button1.setText("Vytvoøit Databázi");
         button2 = new JButton();
         button2.setBackground(FinalValues.DEFAULT_BUTTON_BACKGROUND);
         button2.setText("Zavøít");
         comboBox = new JComboBox();
         comboBox.setBackground(FinalValues.DEFAULT_BUTTON_BACKGROUND);
         if(allowConnection)
         {
        	 comboBox.addItem("Vytvoøit novou Databázi");
        	 comboBox.addItem("Pøipojit ke stávající Databázi");
        	 button1.setText("Pøipojit Databázi");
        	 comboBox.setSelectedIndex(1);
         }
         else
         {
        	 comboBox.addItem("Vytvoøit novou Databázi");
        	 comboBox.setEnabled(false);
         }
         textfield = new JTextField();
         textfield.addKeyListener(this);
         vytvorLabely();
         contentPane.add(textfield, Helper.getGBC(gbc, 1, 1));
         contentPane.add(comboBox, Helper.getGBC(gbc, 1, 0));
         contentPane.add(button1, Helper.getGBC(gbc, 1, 2));
         contentPane.add(button2, Helper.getGBC(gbc, 0, 2));
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
    
    public void keyTyped(KeyEvent e) 
    {
    	//setTextInTextField();
    }
    
    public void keyPressed(KeyEvent e) 
    {
    	//setTextInTextField(e);
    }
     
    public void keyReleased(KeyEvent e) 
    {
    	setTextInTextField(e);
    }
    
    
    private void setTextInTextField(KeyEvent e)
    {
    	if(!textfield.getText().equals(null))
    	{    		                                                                                                                         
    		if(!(e.getKeyCode() == 37 || e.getKeyCode() == 38 || e.getKeyCode() == 39 || e.getKeyCode() == 40 || e.getKeyCode() == 10 || e.getKeyCode() == 8))
    		{
    			if(textfield.getText().contains(".odb"))
    			{
    				textfield.setText(textfield.getText().substring(0, textfield.getText().length() - 5) + textfield.getText().substring(textfield.getText().length() - 1, textfield.getText().length()) + ".odb");
    			}
    			else
    			{
    				textfield.setText(textfield.getText() + ".odb");
    			}
    		}
    	}
    }
    
    private void vytvoritUdalosti()
    {
    	button1.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent evt) 
            {
            	if(comboBox.getSelectedIndex() == 0)
            	{
            		CJPA.getCJPA().createDB(textfield.getText());
            		CJPA.getCJPA().writeToConfigFile(textfield.getText());
            		new ErrorWindow("Vytvoreni databaze", "Databaze " + textfield.getText() + " byla uspesne vytvorena a pripojena.");
            	}
            	else
            	{
            		CJPA.getCJPA().connectToDB(textfield.getText());
            		CJPA.getCJPA().writeToConfigFile(textfield.getText());
            		new ErrorWindow("Pripojeni databaze", "Databaze " + textfield.getText() + " byla uspesne pripojena.");
            	}
            }
        });
    	button2.addActionListener(new ActionListener() 
    	{
            public void actionPerformed(ActionEvent evt) 
            {
            	System.exit(0);
            }
        });
    	comboBox.addActionListener(new ActionListener() 
    	{
            public void actionPerformed(ActionEvent evt) 
            {
            	if(comboBox.getSelectedIndex() == 0)
            	{
            		button1.setText("Vytvoøit Databázi");
            	}
            	else
            	{
            		button1.setText("Pøipojit Databázi");
            	}
            }
        });
    }
}