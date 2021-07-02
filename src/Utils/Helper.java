package Utils;

import java.awt.GridBagConstraints;

import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.border.Border;


/**
 * Copyright Header
 * 
 * Project: ODB Manager
 * Created On: 13.07.2020
 * Last Edit: 02.07.2021
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
}