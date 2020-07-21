package Utils;

/*
 * Copyright Header
 * 
 * Projetct: ODB Manager
 * Created On: 13.07.2020
 * Created By: Riyufuchi
 * 
 * */


public class Hotel 
{
	private Boolean[][] pokoje;
	private final int posledniZnamyPrestupnyRok = 2020;
	private int[] pocetDnu = {31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
	//private final String[] mesice = {"Leden", "Unor", "Brezen", "Duben", "Kveten", "Cerven", "Cervenec", "Srpen", "Zari", "Rijen", "Listopad", "Prosinec"};
	/*
	 * 2020, 2024, ...
	 * leden - 31
	 * unor - 28 - 29
	 * brezen - 31
	 * duben - 30
	 * kveten - 31
	 * cerven - 30
	 * cervenec - 31
	 * srpen - 31
	 * zari - 30
	 * rijen - 31
	 * listopad - 30
	 * prosinec - 31
	 */
	
	public Hotel()
	{
		
	}
	
	public Hotel(String nazev, int pPater, int pPokujuNaPatre)
	{
		pokoje = new Boolean[pPater][pPokujuNaPatre];
	}
	
	public boolean checkDates(String from, String to)
	{
		int denPrijezdu = Integer.parseInt(from.substring(0, 2));
		int mesicPrijezdu = Integer.parseInt(from.substring(3, 5));
		int rokPrijezdu = Integer.parseInt(from.substring(6, from.length()));
		int denOdjezdu = Integer.parseInt(to.substring(0, 2));
		int mesicOdjezdu = Integer.parseInt(to.substring(3, 5));
		int rokOdjezdu = Integer.parseInt(to.substring(6, to.length()));
		if(rokOdjezdu > rokPrijezdu)
		{
			return true;
		}
		else if(mesicOdjezdu > mesicPrijezdu)
		{
			return true;
		}
		else if(mesicOdjezdu == mesicPrijezdu)
		{
			if(denOdjezdu > denPrijezdu)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			return false;
		}
	}
	
	public String[] spitDates(String currentDate)
	{
		int den = Integer.parseInt(currentDate.substring(0, 2));
		int mesic = Integer.parseInt(currentDate.substring(3, 5));
		int rok = Integer.parseInt(currentDate.substring(6, currentDate.length()));
		int i = 0;
		int p1 = 0;
		if(posledniZnamyPrestupnyRok < rok)
		{
			for(i = posledniZnamyPrestupnyRok; i < rok; i = i + 4)
			{
				if(i > rok)
				{
					pocetDnu[2] = pocetDnu[2] - 1;
				}
			}
		}
		for(i = 0; i < mesic - 2; i++)
		{
			p1 = pocetDnu[i] + pocetDnu[i + 2] + p1;
		}
		String[] dates = new String[(337 + pocetDnu[2]) - p1 + (pocetDnu[mesic] - den) + 1 + 100];
		dates[0] = buildDates(den, mesic, rok);
		for(i = 1; i < dates.length; i++)
		{
			if(den < pocetDnu[mesic - 1])
			{
				den++;
				dates[i] = buildDates(den, mesic, rok);
			}
			else
			{
				if(mesic + 1 < 13)
				{
					mesic++;
					den = 1;
					dates[i] = buildDates(den, mesic, rok);
				}
			}
		}
		return dates;
	}
	
	private String buildDates(int den, int mesic, int rok)
	{
		String datum = "";
		if(den < 10)
		{
			datum = datum + "0" + Integer.toString(den) + "/";
		}
		else
		{
			datum = datum + Integer.toString(den) + "/";
		}
		if(mesic < 10)
		{
			datum = datum + "0" + Integer.toString(mesic) + "/";
		}
		else
		{
			datum = datum + Integer.toString(mesic) + "/";
		}
		return datum + Integer.toString(rok);
	}
	
	public void setRoomStatus(int cisloPokoje, Boolean status)
	{
		int p1 = 0;
		int h1 = getPocetPokojuNaPatre();
		int p2 = 0;
		for(int i = 0; i < getPocetPokojuNaPatre()*getPocetPater(); i++)
		{
			if(i > h1)
			{
				p1++;
				h1 = h1 + getPocetPokojuNaPatre();
			}
			if(i == cisloPokoje - 1)
			{
				p2 = h1 - cisloPokoje - 1;
			}
		}
		pokoje[p1][p2] = status;
	}
	
	public boolean isRoomFree(int cisloPokoje)
	{
		int p1 = 0;
		int h1 = getPocetPokojuNaPatre();
		int p2 = 0;
		for(int i = 0; i < getPocetPokojuNaPatre()*getPocetPater(); i++)
		{
			if(i > h1)
			{
				p1++;
				h1 = h1 + getPocetPokojuNaPatre();
			}
			if(i == cisloPokoje - 1)
			{
				p2 = h1 - cisloPokoje - 1;
			}
		}
		return pokoje[p1][p2];
	}
	
	public int getPocetPokojuNaPatre()
	{
		return pokoje[0].length;
	}
	
	public int getPocetPater()
	{
		return pokoje.length;
	}
}