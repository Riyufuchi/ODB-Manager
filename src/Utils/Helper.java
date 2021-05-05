package Utils;

import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class Helper 
{
	 public static Border defaultTextFieldBorder()
	 {
		 return new JTextField().getBorder();
	 }
	 
	 public static void makeBorder(JComponent control, Border border)
	 {
		 control.setBorder(border);
	 }
}
