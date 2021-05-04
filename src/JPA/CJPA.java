package JPA;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import Forms.ErrorWindow;

/*
 * Copyright Header
 * 
 * Projetct: ODB Manager
 * Created On: 13.07.2020
 * Last Edit: 04.05.2021
 * Created By: Riyufuchi
 * 
 */


public class CJPA 
{
    private static CJPA instance;
    private static EntityManager em;
    private Connection connection;
    private static String currDatabase;
    
    //CJPA => Controller of Java Persistance Api
    private CJPA()
    {
        
    }
    
    public static CJPA getCJPA()
    {
        if(instance != null)
        {
            return instance;
        }
        else
        {
        	currDatabase = "null";
            return instance = new CJPA();
        }
    }
    
    public void createDB(String name)
    {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory(name);
        em = emfactory.createEntityManager();
        writeToConfigFile(name);
    }
    
    public void connectToDB(String name)
    {
    	EntityManagerFactory emfactory = Persistence.createEntityManagerFactory(name);
        em = emfactory.createEntityManager();
        writeToConfigFile(name);
    }
    
    public Connection getCurrConnection()
    {
    	return connection;
    }
    
    public void connectionToDB(String name)
    {
        try 
        {
            connection = DriverManager.getConnection(name + ".odb");
        } 
        catch (SQLException e) 
        {
            new ErrorWindow("SQL exception", e.getMessage());
        }
    }
    
    @SuppressWarnings("rawtypes")
    public List getList(String query)
    {
        em.getTransaction().begin();
        //Query q1 = em.createQuery("SELECT c FROM Guest c");
        Query q1 = em.createQuery(query);
		List list1 = q1.getResultList();
        em.getTransaction().commit();
        //em.close();
        return list1;
    }
    
    public Connection getConnection(String name)
    {
        try 
        {
            return DriverManager.getConnection(name + ".odb");
        } 
        catch (SQLException e) 
        {
            new ErrorWindow("SQL exception", e.getMessage());
        }
        try 
        {
        	Persistence.createEntityManagerFactory(name + ".odb");
            return DriverManager.getConnection(name + ".odb");
        } 
        catch (SQLException e) 
        {
            new ErrorWindow("SQL exception", e.getMessage());
        }
        return null;
    }
    
    public void editData(int id, double sum, String date)
    {
    	Money m = em.find(Money.class, id);
    	em.getTransaction().begin();       
    	m.setMoneySum(sum);
    	m.setDate(date);
    	em.getTransaction().commit();        
    }
    
    public void delateData(int id)
    {
    	em.getTransaction().begin();       
    	Query q1 = em.createQuery("Delete FROM Money Where ID =" + id);
    	q1.executeUpdate();
    	em.getTransaction().commit();        
    }
    
    public void configFile()
	{
		File f = new File("Settings.txt");
		if(f.isFile()) 
		{ 
			try(Scanner in = new Scanner(new File("Settings.txt"))) 
			{
				while (in.hasNextLine()) 
				{
					currDatabase = in.nextLine();
				}
				in.close(); 
			} 
			catch (Exception e) 
			{
				new ErrorWindow("Config file error", e.getMessage());
			}
		}
		else
		{
			try(BufferedWriter bw = new BufferedWriter(new FileWriter("Settings.txt"))) 
			{
				writeToConfigFile("null");
				configFile();
			} 
			catch (Exception e1) 
			{
				new ErrorWindow("Config file error", e1.getMessage());
			}
		}
	}
	
	public void writeToConfigFile(String name)
	{
		File f = new File("Settings.txt");
		if(f.isFile()) 
		{ 
			try(BufferedWriter bw = new BufferedWriter(new FileWriter("Settings.txt"))) 
			{
				bw.write(name);
			    bw.flush();
			} 
			catch (Exception e1) 
			{
				new ErrorWindow("Config file error", "Writting into config file was unsucessful.\n" + e1.getMessage());
			}
		}
	}
	
	public String getCurrDatabaseName()
	{
		return currDatabase;
	}
	
	public void addMoney(double sum, String date)
    {
    	if(em != null)
    	{
    		em.persist(new Money(sum, date));
    	}
    }
}