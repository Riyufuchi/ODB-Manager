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
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

import JPA.CJPA;
import Utils.Hotel;

/*
 * Copyright Header
 * 
 * Projetct: ODB Manager
 * Created On: 13.07.2020
 * Created By: Riyufuchi
 * 
 * */

@SuppressWarnings("serial")
public class BookingFormular extends JFrame 
{
    private JButton button1;
    private JButton button2;
    private JPanel contentPane;
    private JLabel[] label;
    private String[] labelTexts = {"Name", "Surrname", "Email", "Bed count:", "From", "To"};
    private JMenuBar menuBar;
    private JMenu file;
    private JMenu tools;
    private JMenu help;
    private JMenuItem[] menuItem;
    private Hotel h;
    private ErrorWindow ew;
    private DataTableForm dtf;
    private GridBagConstraints gbc;
    private JComboBox[] comboBox;
    private DataConnectorForm dcf;
    private DataHolder data;
    private boolean saveToDB;
    private Border borderTextfield;
    private Border borderCombobox;
    private CJPA hotelDB;
    private JTextField[] textfield;
    
    //pridat menu na obsluhovani databaze, zjistovani volnych pokoju, ukladani do databaze, moznost zadani delku pobytu, dynamicky pridavat ovladaci prvky
    public BookingFormular(String nazevOkna)
    {
        this.setTitle(nazevOkna);
        this.setSize(400,300);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        setUp();
        pripojitDatabazi();
        nastavitOvladaciPrvky();
        generujMenu();
        vytvoritUdalosti();
        this.setJMenuBar(menuBar);
        this.add(contentPane);
        this.pack();
        this.setVisible(true);
    }
    
    private void generujMenu()
    {
        menuBar = new JMenuBar();
        file = new JMenu("Database");
        tools = new JMenu("Tools");
        help = new JMenu("Help");
        menuItem = new JMenuItem[5];        
        menuItem[0] = new JMenuItem("Create");
        menuItem[1] = new JMenuItem("Connect");
        menuItem[2] = new JMenuItem("Exit");
        menuItem[3] = new JMenuItem("Data Table Form");
        menuItem[4] = new JMenuItem("About");
        file.add(menuItem[0]);
        file.add(menuItem[1]);
        file.addSeparator();
        file.add(menuItem[2]);
        tools.add(menuItem[3]);
        help.add(menuItem[4]);
        menuBar.add(file);
        menuBar.add(tools);
        menuBar.add(help);
    }
    
    private void setUp()
    {
    	data = new DataHolder();
    	hotelDB = CJPA.getCJPA();
    	h = new Hotel();
    	saveToDB = true;
    }
    
    //pokusit se pripojit databazi, nacist info o hotelu
    private void pripojitDatabazi()
    {
    	hotelDB = CJPA.getCJPA();
    	hotelDB.configFile();
    	if(!hotelDB.getCurrDatabaseName().equals("null"))
    	{
    		hotelDB.connectToDB(hotelDB.getCurrDatabaseName());
    	}
    	this.setTitle(hotelDB.getCurrDatabaseName());
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
         button1.setText("Uložit do databáze");
         button2 = new JButton();
         button2.setBackground(new Color(214,217,223));
         button2.setForeground(new Color(0,0,0));
         button2.setText("Zavøít");
         vytvorLabely();
         comboBox = new JComboBox[3];
         comboBox[0] = new JComboBox();
         comboBox[0].setBackground(new Color(214,217,223));
         borderCombobox = comboBox[0].getBorder();
         for(int i = 0; i < 6; i++)
         {
        	 comboBox[0].addItem(i + 1 + ".");
         }
         comboBox[1] = new JComboBox();
         comboBox[1].setBackground(new Color(214,217,223));
         String[] datumy = h.spitDates(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
         for(int i = 0; i < datumy.length - 1; i++)
         {
        	 comboBox[1].addItem(datumy[i]);
         }
         comboBox[2] = new JComboBox();
         comboBox[2].setBackground(new Color(214,217,223));
         for(int i = 0; i < datumy.length - 1; i++)
         {
        	 comboBox[2].addItem(datumy[i + 1]);
         }
         textfield = new JTextField[3];
         textfield[0] = new JTextField();
         textfield[0].setName("Name");
         borderTextfield = textfield[0].getBorder();
         textfield[1] = new JTextField();
         textfield[1].setName("Surrname");
         textfield[2] = new JTextField();
         gbc = new GridBagConstraints();
         for(int i = 0; i < labelTexts.length; i++)
         {
         	contentPane.add(getLabely(i), getGBC(0, i + 1));
         }
         for(int i = 0; i < textfield.length; i++)
         {
        	 contentPane.add(textfield[i], getGBC(1, i + 1));
         }
         for(int i = 0; i < comboBox.length; i++)
         {
        	 contentPane.add(comboBox[i], getGBC(1, 4 + i));
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
    
    public String zkontrolujEmail(String email)
    {
    	if((!email.contains("@"))||(zkontrolujKoncovku(email)))
    	{
    		ew = new ErrorWindow("Neplatný Email", "Email: " + email + ", který jste zadaly je neplatný.");
    		makeRedBorder(textfield[2]);
    	}
    	return email;
    }
    
    private boolean zkontrolujKoncovku(String email)
    {
    	if(email == null)
    	{
    		return true;
    	}
    	for(int i = email.length(); i > 1; i --)
    	{
    		if(email.substring(i - 1, i).equals("."))
    		{
    			break;
    		}
    		else if(email.substring(i - 1, i).equals("@"))
    		{
    			return true;
    		}
    	}
    	return false;
    }
   
    //misto vraceni chybejciho udaje otevrit formular s dotazanim pro doplneni chybejciho udaje
    private String basicCheck(String text, int id, JComponent control)
    {
    	if(!text.equals(""))
    	{
    		return text;
    	}
    	else
    	{
    		ew = new ErrorWindow("Chybnì zadaná hodnota", "V poli: " + textfield[id].getName() + " chybý údaje");
    		makeRedBorder(control);
    		return "Chybìjcí údaj - doplnit";
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
    	for(int i = 0; i < textfield.length + comboBox.length; i++)
    	{
    		if(i < textfield.length)
    		{
    			makeDefaultBorder(textfield[i], borderTextfield);
    		}
    		if(i < comboBox.length)
    		{
    			makeDefaultBorder(comboBox[i], borderCombobox);
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
    
    private void checkDataPersistance()
    {
    	data.setName(basicCheck(textfield[0].getText(), 0, textfield[0]));
    	data.setSurrname(basicCheck(textfield[1].getText(), 1, textfield[1]));
    	data.setEmail(zkontrolujEmail(textfield[2].getText()));
    	data.setBedsInRoom(Integer.parseInt(((String) comboBox[0].getSelectedItem()).substring(0, 1)));
    	if(h.checkDates((String)comboBox[1].getSelectedItem(), (String)comboBox[2].getSelectedItem()))
        {
    		data.setDatumPrijezdu((String)comboBox[1].getSelectedItem());
    		data.setDatumOdjezdu((String)comboBox[2].getSelectedItem());
        }
    	else
    	{
    		ew = new ErrorWindow("Chybny datum", "Nemuzete odjet drive nez prijedete nebo nestravit v hotelu ani jednu noc.");
    		for(int i = 1; i < 3; i++)
    		{
    			makeRedBorder(comboBox[i]);
    		}
    		saveToDB = false;
    	}
    	if(saveToDB)
    	{
    		hotelDB.addGuest(data);
    		ew = new ErrorWindow("Zapis", "Host uspesne zapsan do databaze.");
    	}
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
    	//upozorneni setVisible(false); schova aplikaci a da se vypnout ze spravce uloh -> podrobnosti javaw.exe
    	button2.addActionListener(new ActionListener() 
    	{
            public void actionPerformed(ActionEvent evt) 
            {
            	System.exit(0);
            }
        });
    	menuItem[0].addActionListener(new ActionListener() 
    	{
            public void actionPerformed(ActionEvent evt) 
            {
            	dcf = new DataConnectorForm("Databáze:", false);
            	//hotelDB.createDB("hotel");
            }
        });
    	menuItem[1].addActionListener(new ActionListener() 
    	{
            public void actionPerformed(ActionEvent evt) 
            {
            	dcf = new DataConnectorForm("Databáze:", true);
            	//hotelDB.connectToDB("hotel");
            }
        });
    	menuItem[2].addActionListener(new ActionListener() 
    	{
            public void actionPerformed(ActionEvent evt) 
            {
            	System.exit(0);
            }
        });
    	menuItem[3].addActionListener(new ActionListener() 
    	{
            public void actionPerformed(ActionEvent evt) 
            {
            	dtf = new DataTableForm();
            }
        });
    	menuItem[4].addActionListener(new ActionListener() 
    	{
            public void actionPerformed(ActionEvent evt) 
            {
            	ew = new ErrorWindow("O programu", "To to je program na pracovaní s databází se zarezervovanými pokoji v hotelu.\n"
            			+ "V sekci |Databáze| mùžete vytvoøit novou databázi nebo se pøipojit do již stávající databázi nebo ukonèit program.\n"
            			+ "V sekci |Nástroje| mùžete vypsat tabulku a mazat z ní.");
            }
        });
    }
}
