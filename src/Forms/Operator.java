package Forms;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import JPA.CJPA;

/**
 * Copyright Header
 *
 * Projetct: ODB Manager
 * Created On: 13.07.2020
 * Last Edit: 08.05.2021
 * @author Riyufuchi
 * @version 1.0
 * @since 1.0
 */

@SuppressWarnings("serial")
public class Operator extends JFrame
{
    private JButton button1;
    private JButton button2;
    private JPanel contentPane;
    private JLabel[] label;
    private String[] labelTexts = {"Item ID:"};
    private GridBagConstraints gbc;
    private JComboBox<String> comboBox;
    
    public Operator(String nazevOkna, int[] ID)
    {
        this.setTitle(nazevOkna);
        this.setSize(400,300);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        nastavitOvladaciPrvky(ID);
        vytvoritUdalosti();
        this.setAlwaysOnTop(true);
        this.add(contentPane);
        this.pack();
        this.setVisible(true);
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	private void nastavitOvladaciPrvky(int[] ID)
    {
         contentPane = new JPanel(null);
         contentPane.setBackground(new Color(192,192,192));
         contentPane.setLayout(new GridBagLayout());
    	 button1 = new JButton();
         button1.setBackground(new Color(214,217,223));
         button1.setForeground(new Color(0,0,0));
         button1.setText("Smazat záznam");
         button2 = new JButton();
         button2.setBackground(new Color(214,217,223));
         button2.setForeground(new Color(0,0,0));
         button2.setText("Zavøít");
         vytvorLabely();
         comboBox = new JComboBox();
         comboBox.setBackground(new Color(214,217,223));
         for(int i = 0; i < ID.length; i++)
         {
        	 comboBox.addItem(Integer.toString(ID[i]));
         }
         gbc = new GridBagConstraints();
         for(int i = 0; i < labelTexts.length; i++)
         {
         	contentPane.add(getLabely(i), getGBC(0, i));
         }
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
    
    private void vytvoritUdalosti()
    {
    	button1.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent evt) 
            {
            	CJPA.getCJPA().delateData(Integer.parseInt((String)comboBox.getSelectedItem()));
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