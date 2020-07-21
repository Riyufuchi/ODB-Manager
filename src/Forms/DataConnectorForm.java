package Forms;

import java.awt.Color;
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
public class DataConnectorForm extends JFrame implements KeyListener
{
    private JButton button1;
    private JButton button2;
    private JPanel contentPane;
    private JLabel[] label;
    private String[] labelTexts = {"Akce:", "Nazev natabaze:"};
    private CJPA hotelDB;
    private GridBagConstraints gbc;
    private JComboBox comboBox;
    private JTextField textfield;
    
    public DataConnectorForm(String nazevOkna, boolean allowConnection)
    {
        this.setTitle(nazevOkna);
        this.setSize(400,300);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        setUp();
        nastavitOvladaciPrvky(allowConnection);
        vytvoritUdalosti();
        this.add(contentPane);
        this.pack();
        this.setVisible(true);
    }
    
    private void setUp()
    {
    	hotelDB = CJPA.getCJPA();
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	private void nastavitOvladaciPrvky(boolean allowConnection)
    {
         contentPane = new JPanel(null);
         contentPane.setBackground(new Color(192,192,192));
         contentPane.setLayout(new GridBagLayout());
    	 button1 = new JButton();
         button1.setBackground(new Color(214,217,223));
         button1.setForeground(new Color(0,0,0));
         button1.setText("Vytvoøit Databázi");
         button2 = new JButton();
         button2.setBackground(new Color(214,217,223));
         button2.setForeground(new Color(0,0,0));
         button2.setText("Zavøít");
         vytvorLabely();
         comboBox = new JComboBox();
         comboBox.setBackground(new Color(214,217,223));
         comboBox.addItem("Vytvoøit novou Databázi");
         if(allowConnection)
         {
        	 comboBox.addItem("Pøipojit ke stávající Databázi");
        	 button1.setText("Pøipojit Databázi");
        	 comboBox.setSelectedIndex(1);
         }
         textfield = new JTextField();
         textfield.addKeyListener(this);
         gbc = new GridBagConstraints();
         for(int i = 0; i < labelTexts.length; i++)
         {
         	contentPane.add(getLabely(i), getGBC(0, i));
         }
         contentPane.add(textfield, getGBC(1, 1));
         contentPane.add(comboBox, getGBC(1, 0));
         contentPane.add(button1, getGBC(1, 2));
         contentPane.add(button2, getGBC(0, 2));
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
    
    private GridBagConstraints getGBC(int x, int y)
    {
    	gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = x;
        gbc.gridy = y;
		return gbc;
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
            		ErrorWindow ew = new ErrorWindow("Vytvoreni databaze", "Databaze " + textfield.getText() + " byla uspesne vytvorena a pripojena.");
            	}
            	else
            	{
            		CJPA.getCJPA().connectToDB(textfield.getText());
            		CJPA.getCJPA().writeToConfigFile(textfield.getText());
            		ErrorWindow ew = new ErrorWindow("Pripojeni databaze", "Databaze " + textfield.getText() + " byla uspesne pripojena.");
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