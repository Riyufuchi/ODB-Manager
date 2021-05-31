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

/**
 * Copyright Header
 * 
 * Projetct: ODB Manager
 * Created On: 13.07.2020
 * Last Edit: 31.05.2021
 * @author Riyufuchi
 * @version 1.1
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
         contentPane.setBackground(new Color(192,192,192));
         contentPane.setLayout(new GridBagLayout());
    	 button1 = new JButton();
         button1.setBackground(new Color(214,217,223));
         button1.setForeground(new Color(0,0,0));
         button1.setText("Vytvo�it Datab�zi");
         button2 = new JButton();
         button2.setBackground(new Color(214,217,223));
         button2.setForeground(new Color(0,0,0));
         button2.setText("Zav��t");
         comboBox = new JComboBox();
         comboBox.setBackground(new Color(214,217,223));
         if(allowConnection)
         {
        	 comboBox.addItem("Vytvo�it novou Datab�zi");
        	 comboBox.addItem("P�ipojit ke st�vaj�c� Datab�zi");
        	 button1.setText("P�ipojit Datab�zi");
        	 comboBox.setSelectedIndex(1);
         }
         else
         {
        	 comboBox.addItem("Vytvo�it novou Datab�zi");
        	 comboBox.setEnabled(false);
         }
         textfield = new JTextField();
         textfield.addKeyListener(this);
         vytvorLabely();
         contentPane.add(textfield, getGBC(1, 1));
         contentPane.add(comboBox, getGBC(1, 0));
         contentPane.add(button1, getGBC(1, 2));
         contentPane.add(button2, getGBC(0, 2));
    }
    
    private void vytvorLabely()
    {
    	label = new JLabel[labelTexts.length];
    	for(int i = 0; i < labelTexts.length; i++)
    	{
    		label[i] = new JLabel();
    		label[i].setText(labelTexts[i]);
    		contentPane.add(label[i], getGBC(0, i));
    	}
    }
    
    private GridBagConstraints getGBC(int x, int y)
    {
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
            		button1.setText("Vytvo�it Datab�zi");
            	}
            	else
            	{
            		button1.setText("P�ipojit Datab�zi");
            	}
            }
        });
    }
}