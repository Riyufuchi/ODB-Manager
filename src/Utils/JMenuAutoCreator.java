package Utils;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/*
 * Copyright Header
 * 
 * Projetct: ODB Manager
 * Created On: 21.07.2020
 * Last Edit: 21.07.2020
 * Created By: Riyufuchi
 * 
 */

public class JMenuAutoCreator 
{
	private int[] index;
	private JMenuBar menuBar;
	private JMenu[] menu;
	private JMenuItem[] menuItem;
	
	public JMenuAutoCreator(String[] menuLabels, String[] menuItemLabels)
	{
		index = new int[menuLabels.length];
		layoutButtonIndex(JMenuItemsPerSection(menuItemLabels));
		generateMenu(menuLabels, menuItemLabels);
	}
    
	private int[] JMenuItemsPerSection(String[] menuItemLabels)
	{
		int value = 0;
		int i = 0;
		int i2 = 0;
		while(true)
		{
			if(i >= menuItemLabels.length)
			{
				index[i2] = value;
				break;
			}
			if(!(menuItemLabels[i].equals("")))
			{
				value++;
				i++;
			}
			else
			{
				index[i2] = value;
				i++;
				value = 0;
				i2++;
			}
		}
		return index;
	}
	
	private void layoutButtonIndex(int[] ips)
	{
		index[0] = ips[0];
		for(int i = 1; i < index.length; i++)
		{
			index[i] = index[i - 1] + ips[i];
		}
	}
	
	private void generateMenu(String[] menuLabels, String[] menuItemLabels)
	{
		menuBar = new JMenuBar();
		menu = new JMenu[menuLabels.length];
		int i = 0;
		int value = menuItemLabels.length;
		for(i = 0; i < menuItemLabels.length; i++)
		{
			if(menuItemLabels[i].equals(""))
			{
				value--;
			}
		}
		menuItem = new JMenuItem[value];
		for(i = 0; i < menu.length; i++)
		{
			menu[i] = new JMenu(menuLabels[i]);
		}
		value = 0;
		for(i = 0; i < menuItemLabels.length; i++)
		{
			if(!menuItemLabels[i].equals(""))
			{
				menuItem[value] = new JMenuItem(menuItemLabels[i]);
				menuItem[value].setName(menuItemLabels[i]);
				value++;
			}
			else
			{
				i++;
				menuItem[value] = new JMenuItem(menuItemLabels[i]);
				menuItem[value].setName(menuItemLabels[i]);
				value++;
			}
		}
		int i2 = 0;
		for(int x = 0; x < menuLabels.length; x++)
		{
			for(i = i2; i < index[x]; i++)
			{
				if(x > 0)
				{
					menu[x].add(menuItem[i]);
				}
				else
				{
					switch(i)
					{
						case 2: menu[x].addSeparator(); menu[x].add(menuItem[i]); break;
						default: menu[x].add(menuItem[i]); break;
					}
				}
				menuBar.add(menu[x]);
			}
			i2 = i;
		}
	}
	
	public JMenuItem[] getMenuItem()
	{
		return menuItem;
	}
	
	public JMenuBar getJMenuBar()
	{
		return menuBar;
	}
}