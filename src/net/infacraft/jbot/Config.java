package net.infacraft.jbot;

import java.awt.Color;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class Config {

	public static boolean loadSlots()
	{
		File f = new File("inv.txt");
		if (!f.exists())
		{
			return false;
		}
		try {
			String content = new String(Files.readAllBytes(Paths.get("inv.txt")));
			String cpairs[] = content.split(";");
			for (int i = 0; i<cpairs.length; i++)
			{
				String[] cs = cpairs[i].split(",");
				int x = Integer.parseInt(cs[0]);
				int y = Integer.parseInt(cs[1]);
				int red = Integer.parseInt(cs[2]);
				int green = Integer.parseInt(cs[3]);
				int blue = Integer.parseInt(cs[4]);
				Bot.inventorySlotPos[i] = new Point(x,y);
				Bot.inventorySlotColor[i] = new Color(red,green,blue);
				System.out.println("Loaded slot " + i + " ("+x + "," + y+ ")");
			}
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	public static void saveSlots()
	{
		String s = "";
		
		for (int i = 0; i < Bot.inventorySlotPos.length; i++)
		{
			if (i!=0) s+=";";
			Point p = Bot.inventorySlotPos[i];
			Color c = Bot.inventorySlotColor[i];
			s+=p.x + "," + p.y + "," + c.getRed() + "," + c.getGreen() + "," + c.getBlue();
		}
		
		try {
			Files.write(Paths.get("inv.txt"), s.getBytes(), StandardOpenOption.CREATE);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
