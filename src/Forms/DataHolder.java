package Forms;

/*
 * Copyright Header
 * 
 * Projetct: ODB Manager
 * Created On: 13.07.2020
 * Created By: Riyufuchi
 * 
 * */


public class DataHolder 
{
	private String name;
	private String surrname;
	private String email;
	private int bedsInRoom;
	private String datumPrijezdu;
	private String datumOdjezdu;
	private String pomocna;
	
	public DataHolder()
	{
	}

	public DataHolder(String name, String surrname, String email, int bedsInRoom, String datumPrijezdu, String datumOdjezdu)
	{
		this.name = name;
		this.surrname = surrname;
		this.email = email;
		this.bedsInRoom = bedsInRoom;
		this.datumPrijezdu = datumPrijezdu;
		this.datumOdjezdu = datumOdjezdu;
	}
	
	public String getName() 
	{
		return name;
	}

	public String getSurrname() 
	{
		return surrname;
	}

	public String getEmail() 
	{
		return email;
	}

	public int getBedsInRoom()
	{
		return bedsInRoom;
	}

	public String getDatumPrijezdu() 
	{
		return datumPrijezdu;
	}

	public String getDatumOdjezdu() 
	{
		return datumOdjezdu;
	}
	
	public String getPomocna()
	{
		return pomocna;
	}

	public void setName(String name) 
	{
		this.name = name;
	}

	public void setSurrname(String surrname) 
	{
		this.surrname = surrname;
	}

	public void setEmail(String email) 
	{
		this.email = email;
	}

	public void setBedsInRoom(int bedsInRoom) 
	{
		this.bedsInRoom = bedsInRoom;
	}

	public void setDatumPrijezdu(String datumPrijezdu) 
	{
		this.datumPrijezdu = datumPrijezdu;
	}

	public void setDatumOdjezdu(String datumOdjezdu) 
	{
		this.datumOdjezdu = datumOdjezdu;
	}
	
	public void setPomocna(String pomocna)
	{
		this.pomocna = pomocna;
	}

}