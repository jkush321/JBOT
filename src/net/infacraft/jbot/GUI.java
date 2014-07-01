package net.infacraft.jbot;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class GUI {
	public static void showAlert(String... msg)
	{
		String unpacked = "";
		for (String s : msg)
		{
			unpacked+=s + "\n";
		}
		unpacked=unpacked.trim();
		JOptionPane.showMessageDialog(null, unpacked, "JBOT Alert", JOptionPane.INFORMATION_MESSAGE);
	}
}

class RadioButton extends JLabel
{
	private static final long serialVersionUID = -6537930185815349318L;
	public static Map<Integer,List<RadioButton>> groups = new HashMap<>();
	int rbGroup;
	boolean isSelected;
	//private boolean isSelected;
	public RadioButton(int group)
	{
		super();
		rbGroup=group;
		addToMap();
		//setOpaque(true);
		//setBackground(new Color(255,0,0));
		this.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e)
			{
				//System.out.println("click");
				onRBClick();
			}
		});
	}
	private void addToMap()
	{
		List<RadioButton> list;
		if (!groups.containsKey(rbGroup))
		{
			list = new ArrayList<RadioButton>();
		}
		else
		{
			list = groups.get(rbGroup);
			groups.remove(rbGroup);
		}
		list.add(this);
		groups.put(rbGroup, list);
	}
	public void onRBClick()
	{
		List<RadioButton> list = groups.get(rbGroup);
		for (RadioButton r : list)
		{
			r.rbDeselect();
		}
		this.rbSelect();
	}
	public void rbSelect()
	{
		isSelected=true;
		setOpaque(true);
		setBackground(new Color(0,0,0));
		repaint();
	}
	public void rbDeselect()
	{
		isSelected=false;
		setOpaque(false);
		repaint();
	}
	public boolean isSelected()
	{
		return isSelected;
	}
}
class LSRadioButton extends RadioButton
{
	private static final long serialVersionUID = 6790582916637730228L;
	private static int rbGroup;
	Lodestone lodestone;
	public LSRadioButton(int group, Lodestone ls)
	{
		super(group);
		lodestone=ls;
		rbGroup = group;
	}
	public Lodestone getLodestone()
	{
		return lodestone;
	}
	public static Lodestone getSelectedLodestone()
	{
		for (RadioButton r : groups.get(rbGroup))
		{
			if (r instanceof LSRadioButton)
			{
				if (r.isSelected())
				{
					return ((LSRadioButton) r).getLodestone();
				}
			}
		}
		return Lodestone.ALKHARID;
	}
}
class IFBRadioButton extends RadioButton
{
	private static final long serialVersionUID = 6790582916637730228L;
	private static int rbGroup;
	InventoryFullBehavior inventoryFullBehavior;
	public IFBRadioButton(int group, InventoryFullBehavior ifb)
	{
		super(group);
		inventoryFullBehavior=ifb;
		rbGroup = group;
	}
	public InventoryFullBehavior getIFB()
	{
		return inventoryFullBehavior;
	}
	public static InventoryFullBehavior getSelectedIFB()
	{
		for (RadioButton r : groups.get(rbGroup))
		{
			if (r instanceof IFBRadioButton)
			{
				if (r.isSelected())
				{
					return ((IFBRadioButton) r).getIFB();
				}
			}
		}
		return InventoryFullBehavior.ALERT_WHEN_FULL;
	}
}