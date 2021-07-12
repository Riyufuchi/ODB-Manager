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
import Utils.FilesIO;
import Utils.FinalValues;
import Utils.Helper;
import Utils.XML;


/**
 * Copyright Header
 * 
 * Project: ODB Manager
 * Created On: 31.05.2021
 * Last Edit: 12.07.2021
 * @author Riyufuchi
 * @version 1.3
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
    		{".csv", ".xml", ".dat"},
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
    		contentPane.add(comboBoxes[i], Helper.getGBC(gbc, 1, i));
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
        if(export)
        {
        	ok.setText("Export data");
        }
        else
        {
        	ok.setText("Import data");
        }
        cancel.setBackground(FinalValues.DEFAULT_BUTTON_BACKGROUND);
        cancel.setText("Cancel");
	}
	
	private void setTextFields()
	{
		fileName = new JTextField();
		pathToFile = new JTextField();
		pathToFile.setText("path");
		pathToFile.setEnabled(false);
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
         contentPane.add(fileName, Helper.getGBC(gbc, 1, 2));
         contentPane.add(pathToFile, Helper.getGBC(gbc, 1, 3));
         contentPane.add(ok, Helper.getGBC(gbc, 1, 4));
         contentPane.add(cancel, Helper.getGBC(gbc, 0, 4));
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
    
    private void safelyClose()
    {
    	this.dispose();
    }
    
    private void exportNow()
    {
    	if(!(pathToFile.getText()).equals(""))
		{
    		switch(comboBoxes[0].getSelectedIndex())
    		{
    			case 0: 
    				FilesIO.saveToCVS(getPath(), list); 
    				break;
    			case 1: 
    				XML xml = new XML(getPath(), "MoneyExport", "Money");
    				xml.exportXML(list);
    				break;
    			case 2:
    				FilesIO.writeBinary(getPath(), list);
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
    				list = FilesIO.loadFromCVS(getPath());
    				break;
    			case 1: 
    				XML xml = new XML(getPath(), "MoneyExport", "Money");
    				xml.parsujMoney();
    				list = xml.getList();
    				break;
    			case 2:
    				list = FilesIO.loadBinary(getPath());
    				break;
    		}
		}
    	parentFrame.loadData(list);
    }
    
    public String getPath()
    {
    	return fileName.getText() + comboBoxes[0].getSelectedItem();
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