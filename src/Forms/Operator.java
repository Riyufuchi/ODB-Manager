package Forms;

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
import Utils.FinalValues;
import Utils.Helper;

/**
 * Copyright Header
 *
 * Project: ODB Manager
 * Created On: 13.07.2020
 * Last Edit: 07.06.2021
 * @author Riyufuchi
 * @version 1.1
 * @since 1.0
 */

@SuppressWarnings("serial")
public class Operator extends JFrame
{
	private JButton button1, button2;
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
         contentPane.setBackground(FinalValues.DEFAULT_PANE_BACKGROUND);
         contentPane.setLayout(new GridBagLayout());
         gbc = new GridBagConstraints();
         gbc.fill = GridBagConstraints.HORIZONTAL;
    	 button1 = new JButton();
         button1.setBackground(FinalValues.DEFAULT_BUTTON_BACKGROUND);
         button1.setText("Delete");
         button2 = new JButton();
         button2.setBackground(FinalValues.DEFAULT_BUTTON_BACKGROUND);
         button2.setText("Close");
         vytvorLabely();
         comboBox = new JComboBox();
         comboBox.setBackground(FinalValues.DEFAULT_BUTTON_BACKGROUND);
         for(int i = 0; i < ID.length; i++)
         {
        	 comboBox.addItem(Integer.toString(ID[i]));
         }
         contentPane.add(comboBox, Helper.getGBC(gbc, 1, 0));
         contentPane.add(button1, Helper.getGBC(gbc, 1, 2));
         contentPane.add(button2, Helper.getGBC(gbc, 0, 2));
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
    		contentPane.add(getLabely(i), Helper.getGBC(gbc, 0, i));
    	}
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