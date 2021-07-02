package Utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import Forms.ErrorWindow;
import JPA.Money;
/**
 * Copyright Header
 * 
 * Project: ODB Manager
 * Created On: 02.07.2021
 * Last Edit: 02.07.2021
 * @author Riyufuchi
 * @version 1.0
 * @since 1.3.1 
 */
public class FilesIO 
{
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
		new ErrorWindow("Export completed", "File saved to path: " + path);
	}
	 
	public static List<Money> loadFromCVS(String path)
	{
		List<Money> l = new LinkedList<Money>();
		int i = 1;
		String s;
		try (BufferedReader br = new BufferedReader(new FileReader(path))) 
		{
			while ((s = br.readLine()) != null) 
			{
				String[] split = s.split(";");
				l.add(new Money(i, Double.valueOf(split[0]), split[1]));
				i++;
			}
		}
		catch(IOException e)
		{
			new ErrorWindow("IO Error", e.getMessage());
		}
		return l;
	}
	
	public static void writeBinary(String path, List<Money> data)
	{
		try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(path))) 
		{
			for (Money m: data) 
			{
				dos.writeDouble(m.getMoneySum());
				dos.writeUTF(m.getDate());
				dos.flush();
			}
		}
		catch (IOException e) 
		{
			new ErrorWindow("IO error", e.getMessage());
		}
		new ErrorWindow("Export completed", "File saved to path: " + path);
	}
	
	public static List<Money> loadBinary(String path)
	{
		List<Money> l = new LinkedList<Money>();
		int i = 0;
		double money = 0;
		String date = "";
		try (DataInputStream dis = new DataInputStream(new FileInputStream(path))) 
		{
			while (dis.available() > 0) 
			{
				money = dis.readDouble();
				date = dis.readUTF();
				l.add(new Money(i, money, date));
				i++;
			}
		}
		catch (IOException e) 
		{
			new ErrorWindow("IO error", e.getMessage());
		}
		return l;
	}
}