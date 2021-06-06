package Forms;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import JPA.Money;
import Utils.FinalValues;
import Utils.Helper;
import Utils.XML;


/**
 * Copyright Header
 * 
 * Projetct: ODB Manager
 * Created On: 31.05.2021
 * Last Edit: 06.06.2021
 * @author Riyufuchi
 * @version 1.1
 * @since 1.3 
 */

@SuppressWarnings("serial")
public class DatabaseFileIO extends JFrame
{
    private JButton cancel, ok;
    private JPanel contentPane;
    private JLabel[] label;
    private String[] labelTexts = {"File type:", "Path type:", "File name:", "Actual path:"};
    private String[][] comboBoxTexts = {
    		{".csv", ".xml"},
    		{"Same as Database", "Custom"}};
    private GridBagConstraints gbc;
    private JComboBox<String>[] comboBoxes;
    private JTextField fileName, pathToFile;
    private DataTableForm parentFrame;
    private List<Money> list;
    private boolean export;
    
    public DatabaseFileIO(String title, DataTableForm prevFrame)
    {
        this.setTitle(title);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.export = false;
        this.parentFrame = prevFrame;
        this.gbc = new GridBagConstraints();
        this.gbc.fill = GridBagConstraints.HORIZONTAL;
        setComponents();
        vytvoritUdalosti();
        this.setAlwaysOnTop(true);
        this.add(contentPane);
        this.pack();
        this.setVisible(true);
    }
    
    public DatabaseFileIO(String title, List<Money> list)
    {
        this.setTitle(title);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.list = list;
        this.export = true;
        this.gbc = new GridBagConstraints();
        this.gbc.fill = GridBagConstraints.HORIZONTAL;
        setComponents();
        vytvoritUdalosti();
        this.setAlwaysOnTop(true);
        this.add(contentPane);
        this.pack();
        this.setVisible(true);
    }
    
	@SuppressWarnings("unchecked")
	private void setComboBoxes()
    {
		comboBoxes = new JComboBox[2];
    	for(int i = 0; i < comboBoxes.length; i++)
    	{
    		comboBoxes[i] = new JComboBox<String>();
    		comboBoxes[i].setBackground(FinalValues.DEFAULT_BUTTON_BACKGROUND);
    		contentPane.add(comboBoxes[i], getGBC(1, i));
    		for(int l = 0; l < comboBoxTexts[i].length; l++)
    		{
    			comboBoxes[i].addItem(comboBoxTexts[i][l]);
    		}
    	}
    }
	
	private void setButtons()
	{
		ok = new JButton();
		cancel = new JButton();
        ok.setBackground(FinalValues.DEFAULT_BUTTON_BACKGROUND);
        ok.setText("Import data");
        cancel.setBackground(FinalValues.DEFAULT_BUTTON_BACKGROUND);
        cancel.setText("Cancel");
	}
	
	private void setTextFields()
	{
		fileName = new JTextField();
		pathToFile = new JTextField();
	}
    
	private void setComponents()
    {
         contentPane = new JPanel(null);
         contentPane.setBackground(FinalValues.DEFAULT_PANE_BACKGROUND);
         contentPane.setLayout(new GridBagLayout());
         setComboBoxes();
         setButtons();
         setTextFields();
         vytvorLabely();
         contentPane.add(fileName, getGBC(1, 2));
         contentPane.add(pathToFile, getGBC(1, 3));
         contentPane.add(ok, getGBC(1, 4));
         contentPane.add(cancel, getGBC(0, 4));
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
    
    private void safelyClose()
    {
    	this.dispose();
    }
    
    private GridBagConstraints getGBC(int x, int y)
    {
        gbc.gridx = x;
        gbc.gridy = y;
		return gbc;
    }
    
    private void exportNow()
    {
    	if(!(pathToFile.getText()).equals(""))
		{
    		switch(comboBoxes[0].getSelectedIndex())
    		{
    			case 0: 
    				Helper.saveToCVS(pathToFile.getText(), list); 
    				break;
    			case 1: 
    				XML xml = new XML(pathToFile.getText(), "MoneyExport", "Money");
    				xml.exportXML(list);
    				break;
    		}
		}
    }
    
    private void importNow()
    {
    	list = new ArrayList<Money>();
    	if(!(pathToFile.getText()).equals(""))
		{
    		switch(comboBoxes[0].getSelectedIndex())
    		{
    			case 0: 
    				list = Helper.loadFromCVS(pathToFile.getText());
    				break;
    			case 1: 
    				XML xml = new XML(pathToFile.getText(), "MoneyExport", "Money");
    				xml.parsujMoney();
    				list = xml.getList();
    				break;
    		}
		}
    	parentFrame.loadData(list);
    }
    
    private void vytvoritUdalosti()
    {
    	ok.addActionListener(new ActionListener() 
        {
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				if(export)
				{
					exportNow();
				}
				else
				{
					importNow();
				}
				safelyClose();
			}
        });
    	cancel.addActionListener(new ActionListener() 
    	{
            public void actionPerformed(ActionEvent evt) 
            {
            	safelyClose();
            }
        });
    }
}