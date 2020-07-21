package JPA;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
//import javax.persistence.OneToMany;

/*
 * Copyright Header
 * 
 * Projetct: ODB Manager
 * Created On: 13.07.2020
 * Created By: Riyufuchi
 * 
 * */


@Entity
public class Guest 
{ 
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int ID;
	private String name;
	private String surrname;
	private String email;
	private String datumPrijezdu;
	private String datumOdjezdu;
	private int bedsInRoom;
	
	public Guest()
	{
		
	}
	
	public Guest(String name, String surrname, String email, int bedsInRoom, String datumPrijezdu, String datumOdjezdu)
	{
		this.name = name;
		this.surrname = surrname;
		this.email = email;
		this.bedsInRoom = bedsInRoom;
		this.datumPrijezdu = datumPrijezdu;
		this.datumOdjezdu = datumOdjezdu;
	}
	
	public String[] getDataArray()
	{
		String[] data = new String[7];
		data[0] = Integer.toString(ID);
		data[1] = name;
		data[2] = surrname;
		data[3] = Integer.toString(bedsInRoom);
		data[4] = datumPrijezdu;
		data[5] = datumOdjezdu;
		return data;
	}
	
	public int getID() 
	{
		return ID;
	}

	public String getName() 
	{
		return name;
	}

	public String getSurrname() 
	{
		return surrname;
	}

	public String getGuestEmail() 
	{
		return email;
	}
	
	public String getDatumPrijezdu()
	{
		return datumPrijezdu;
	}
	
	public String getDatumOdjezdu()
	{
		return datumOdjezdu;
	}

	public int getBedsInRoom() 
	{
		return bedsInRoom;
	}
	
	public void setID(int iD) 
	{
		ID = iD;
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

	public void setRoomNumber(int bedsInRoom) 
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
}