package Utils;

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
	 
	 public static void saveToCVS(String path, List<Money> data)
	 {
		 try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) 
		 {
			 for (Money m : data) 
	         {
				 String[] values = {String.valueOf(m.getID()), String.valueOf(m.getMoneySum()), m.getDate()};
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
			 List<Money> l = new LinkedList<Money>();
			 String s;
			 while ((s = br.readLine()) != null) 
			 {
				 String[] split = s.split(";");
				 l.add(new Money(Integer.valueOf(split[0]), Double.valueOf(split[1]), split[2]));
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