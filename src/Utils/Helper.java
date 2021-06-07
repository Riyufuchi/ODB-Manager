package Utils;

import java.awt.GridBagConstraints;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.border.Border;

import Forms.ErrorWindow;
import JPA.Money;

/**
 * Copyright Header
 * 
 * Project: ODB Manager
 * Created On: 13.07.2020
 * Last Edit: 07.06.2021
 * @author Riyufuchi
 * @version 1.1
 * @since 1.2 
 */

public class Helper 
{
	public static GridBagConstraints getGBC(GridBagConstraints gbc, int x, int y)
    {
        gbc.gridx = x;
        gbc.gridy = y;
		return gbc;
    }
	
	public static Border defaultTextFieldBorder()
	{
		return new JTextField().getBorder();
	}
	 
	public static void makeBorder(JComponent control, Border border)
	{
		control.setBorder(border);
	}
	 
	public static void saveToCVS(String path, List<Money> data)
	{
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) 
		{
			for (Money m : data) 
			{
				String[] values = {String.valueOf(m.getMoneySum()), m.getDate()};
				bw.append(String.join(";", values) + "\n");
			}
			bw.flush();
		}
		catch(IOException e)
		{
			new ErrorWindow("IO Error", e.getMessage());
		}
	}
	 
	public static List<Money> loadFromCVS(String path)
	{
		try (BufferedReader br = new BufferedReader(new FileReader(path))) 
		{
			int i = 1;
			List<Money> l = new LinkedList<Money>();
			String s;
			while ((s = br.readLine()) != null) 
			{
				String[] split = s.split(";");
				l.add(new Money(i, Double.valueOf(split[0]), split[1]));
				i++;
			}
			return l;
		}
		catch(IOException e)
		{
			new ErrorWindow("IO Error", e.getMessage());
		}
		return null;
	}
}