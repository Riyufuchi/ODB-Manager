package JPA;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/*
 * Copyright Header
 * 
 * Projetct: ODB Manager
 * Created On: 21.07.2020
 * Last Edit: 21.07.2020
 * Created By: Riyufuchi
 * 
 */

@Entity
public class Money 
{ 
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int ID;
	private double moneySum;
	private String date;
	
	public Money()
	{
		
	}
	
	public Money(double sum, String date)
	{
		this.moneySum = sum;
		this.date = date;
	}
	
	public String[] getDataArray()
	{
		String[] data = new String[3];
		data[0] = Integer.toString(ID);
		data[1] = Double.toString(moneySum); 
		data[2] = date;
		return data;
	}
	
	public int getID() 
	{
		return ID;
	}

	public double getMoneySum() 
	{
		return moneySum;
	}

	public String getDate() 
	{
		return date;
	}
}