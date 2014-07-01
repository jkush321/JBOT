package notabot;

import java.awt.Color;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Main {
	public static Color pixelColor = new Color(0,0,0);
	public static Color originalColor;
	public static int pixelX,pixelY;
	public static Random rand = new Random();
	public static Robot b;
	public static boolean paused = false;
	public static Point inventorySlotPos[] = new Point[28];
	public static Color inventorySlotColor[] = new Color[28];
	public static final int firstMenuOffset = 30;
	public static final int menuOffset = 15;
	
	public static void main(String[] args)
	{
		try {
			b = new Robot();
		} catch (Exception e) {}
		start();
	}
	public static void start()
	{
		OptionsGUI.init();
	}
	public static void waitForColorChange()
	{
		RunningGUI.setLabel("Hello, 5 seconds to position mouse.");
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e1) {
		}
		
		pixelX = MouseInfo.getPointerInfo().getLocation().x - 3;
		pixelY = MouseInfo.getPointerInfo().getLocation().y;
		
		//highlight(pixelX,pixelY);
		
		pixelColor = b.getPixelColor(pixelX, pixelY);
		printColor(pixelColor);
		originalColor = new Color(pixelColor.getRGB());
		RunningGUI.setColor(originalColor);
				
		while (true)
		{
			if (!paused){
				while (pixelColor.equals(b.getPixelColor(pixelX, pixelY)))
				{
					if (isInventoryFull())
					{
						if (onFullInventory())
							return;
						moveMouseTo(pixelX + 3, pixelY);
						b.delay(1000);
						naturalLeftClick();
						b.delay(50);
					}
					RunningGUI.setLabel("Waiting for color change.");
					try {
						Thread.sleep(rand.nextInt(1000) + 500);
					} catch (InterruptedException e) {
						System.out.println("Insomnia? Thread wont sleep.");
					}
				}
				pixelColor = b.getPixelColor(pixelX, pixelY);
				if (isOriginalColorThere())
				{
					RunningGUI.setLabel("Color changed, clicking now.");
					printColor(pixelColor);
					b.mousePress(InputEvent.BUTTON1_MASK);
					b.delay(rand.nextInt(500));
					b.mouseRelease(InputEvent.BUTTON1_MASK);
				}
				else {
					RunningGUI.setLabel("Color changed, but original not there.");
				}
				if (isInventoryFull())
				{
					if (onFullInventory())
						return;
					moveMouseTo(pixelX + 3, pixelY);
					b.delay(1000);
					naturalLeftClick();
					b.delay(50);
				}
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					System.out.println("Insomnia? Thread wont sleep.");
				}
			}
			else
			{
			}
		}
}
	public static Point findColor(Color toFind, int tolerance)
	{
		int toFindRGB = toFind.getRGB();
		for (int s = 5; s >= 0; s--)
		{
			RunningGUI.setLabel("Top left in " + s + " seconds...");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				System.out.println("Insomnia? Thread wont sleep.");
			}
		}
		Point topL = MouseInfo.getPointerInfo().getLocation();
		for (int s = 5; s >= 0; s--)
		{
			RunningGUI.setLabel("Bottom right in " + s + " seconds...");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				System.out.println("Insomnia? Thread wont sleep.");
			}
		}
		Point bottomR = MouseInfo.getPointerInfo().getLocation();
		
		int minX = topL.x;
		int minY = topL.y;
		int maxX = bottomR.x;
		int maxY = bottomR.y;
		int width = maxX - minX;
		int height = maxY - minY;
		
		BufferedImage img = b.createScreenCapture(new Rectangle(minX,minY,width,height));
		try {
			ImageIO.write(img, "png", new File("test.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for (int x = 0; x < width; x++)
		{
			RunningGUI.setLabel("Checking " + (int)(100*(x+1)/width) +  "% (" + (x+1) + "/" + width + ")");
			for (int y = 0; y < height; y++)
			{	
				if (isColorTolerated(img.getRGB(x, y), toFindRGB, tolerance))
				{
					RunningGUI.setLabel("Found it!");
					return new Point(minX + x,minY + y);
				}
			}
		}
		RunningGUI.setLabel("Not found!");
		return null;
	}
	public static void findInvSlots()
	{
//        int val = JOptionPane.showConfirmDialog (null, "Load saved config?","Load saved config?",JOptionPane.YES_NO_OPTION);
//		if (val==JOptionPane.YES_OPTION)
//		{
//			
//			GUI.setLabel("Can't find slot config!");
//		}
		if (loadSlots())
			return;
		List<JFrame> list = new ArrayList<JFrame>();
		for (int i = 0; i < inventorySlotPos.length; i++)
		{
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				System.out.println("Insomnia? Thread wont sleep.");
			}	
			for (int s = 5; s >= 0; s--)
			{
				RunningGUI.setLabel("Slot " + i + " in " + s + " seconds...");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					System.out.println("Insomnia? Thread wont sleep.");
				}
			}
			Point p = (Point) MouseInfo.getPointerInfo().getLocation().clone();
			RunningGUI.setLabel("Captured slot " + i + "! (" + p.x + "," + p.y + ")");
			inventorySlotPos[i] = p;
			
			JFrame frame = new JFrame();
			frame.setLocation(p.x-5,p.y-5);
			frame.setSize(10,10);
			frame.setUndecorated(true);
			frame.setTitle("slot " + i + "");
			frame.setAlwaysOnTop(true);
			frame.getContentPane().setBackground(Color.BLUE);
			
			frame.setVisible(true);
			
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			list.add(frame);
		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		for (JFrame f : list)
		{
			f.setVisible(false);
		}
		JOptionPane.showMessageDialog(null, "Empty your inventory, turn off GUI transparency.", "First time set-up", JOptionPane.INFORMATION_MESSAGE);
		JOptionPane.showMessageDialog(null, "MAKE SURE NOTHING IS BLOCKING YOUR INVENTORY SLOTS!", "First time set-up", JOptionPane.INFORMATION_MESSAGE);		
		RunningGUI.setLabel("Capturing pixels...");
		for (int i = 0; i < inventorySlotPos.length; i++)
		{
			Point p = inventorySlotPos[i];
			inventorySlotColor[i]=b.getPixelColor(p.x, p.y);
		}
		saveSlots();
		RunningGUI.setLabel("Slots saved!");
	}
	
	/*
	 * 
	 * List<JFrame> list = new ArrayList<JFrame>();
		for (int i = 0; i < inventorySlotPos.length; i++)
		{
			int x = inventorySlotPos[i].x;
			int y = inventorySlotPos[i].y;
			
			JFrame frame = new JFrame();
			frame.setLocation(x-5,y-5);
			frame.setSize(10,10);
			frame.setUndecorated(true);
			frame.setTitle("slot " + i + "");
			frame.setAlwaysOnTop(true);
			frame.getContentPane().setBackground((isEmptySlot(i)) ? Color.GREEN : Color.RED);
			
			frame.setVisible(true);
			
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			list.add(frame);
		}
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
		}
		for (JFrame f : list)
		{
			f.setVisible(false);
		}
	 * 
	 */
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
				inventorySlotPos[i] = new Point(x,y);
				inventorySlotColor[i] = new Color(red,green,blue);
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
		
		for (int i = 0; i < inventorySlotPos.length; i++)
		{
			if (i!=0) s+=";";
			Point p = inventorySlotPos[i];
			Color c = inventorySlotColor[i];
			s+=p.x + "," + p.y + "," + c.getRed() + "," + c.getGreen() + "," + c.getBlue();
		}
		
		try {
			Files.write(Paths.get("inv.txt"), s.getBytes(), StandardOpenOption.CREATE);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void printColor(Color c)
	{
		System.out.println("Color is, r."+c.getRed() + " g."+c.getGreen() + " b."+c.getBlue());
	}
	public static void hoverOverSlot(int slot)
	{
		System.out.println("Moving to " + inventorySlotPos[slot].x + "," + inventorySlotPos[slot].y);
		moveMouseTo(inventorySlotPos[slot].x, inventorySlotPos[slot].y);
	}
	public static void clickSlotMenuItem(int slot, int item)
	{
		hoverOverSlot(slot);
		naturalRightClick();
		b.delay(50 + rand.nextInt(100));
		moveMouseTo(inventorySlotPos[slot].x, inventorySlotPos[slot].y + findMenuOffset(item));
		naturalLeftClick();
	}
	public static Color getSlotColor(int slot)
	{
		return b.getPixelColor(inventorySlotPos[slot].x,inventorySlotPos[slot].y);
	}
	public static void printSlotColor(int slot)
	{
		printColor(getSlotColor(slot));
	}
	public static boolean isEmptySlot(int slot)
	{
		Color c = getSlotColor(slot);
		return c.equals(inventorySlotColor[slot]);
	}
	public static void printEmptySlot(int slot)
	{
		String not = " not";
		if (isEmptySlot(slot)) not = "";
		System.out.println("Slot " + slot + " is" + not + " empty");
	}
	public static int getEmptySlots()
	{
		int empty = 0;
		for (int i = 0; i < inventorySlotPos.length; i++)
		{
			if (isEmptySlot(i)) empty++;
		}
		return empty;
	}
	public static int[] getEmptySlotNums()
	{
		int empty[] = new int[getEmptySlots()];
		int pos = 0;
		for (int i = 0; i < inventorySlotPos.length; i++)
		{
			if (isEmptySlot(i))
			{
				empty[pos] = i;
				pos++;
			}
		}
		return empty;
	}
	public static void printEmptySlots()
	{
		System.out.println("There are " + getEmptySlots() + " empty slots.");
	}
	public static void printEmptySlotNums()
	{
		if (getEmptySlots()<=0) {
			System.out.println("No empty slots.");
			return;
		}
		String empty = ": ";
		for (int i : getEmptySlotNums())
		{
			empty+=i + ",";
		}
		empty = empty.substring(0, empty.length() - 1);
		System.out.println("Empty slots: " + empty);
	}
	public static void frameTheSlots()
	{
		List<JFrame> list = new ArrayList<JFrame>();
		for (int i = 0; i < inventorySlotPos.length; i++)
		{
			int x = inventorySlotPos[i].x;
			int y = inventorySlotPos[i].y;
			
			JFrame frame = new JFrame();
			frame.setLocation(x-5,y-5);
			frame.setSize(10,10);
			frame.setUndecorated(true);
			frame.setTitle("slot " + i + "");
			frame.setAlwaysOnTop(true);
			frame.getContentPane().setBackground((isEmptySlot(i)) ? Color.GREEN : Color.RED);
			
			frame.setVisible(true);
			
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			list.add(frame);
		}
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
		}
		for (JFrame f : list)
		{
			f.setVisible(false);
		}
	}
	public static void highlight(int x, int y)
	{
		JFrame frame = new JFrame();
		frame.setLocation(x-5,y-5);
		frame.setSize(10,10);
		frame.setUndecorated(true);
		frame.setAlwaysOnTop(true);
		
		frame.setVisible(true);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		try {
			Thread.sleep(2500);
		} catch (InterruptedException e) {
		}
		
		frame.setVisible(false);
	}
	public static void teleportToLodestone(Lodestone l)
	{
		b.keyPress(KeyEvent.VK_T);
		b.delay(rand.nextInt(500) + 50);
		b.keyRelease(KeyEvent.VK_T);
		b.delay(rand.nextInt(500) + 2750);
		if (!l.hasSecondKey)
		{
			b.keyPress(l.keyint);
			b.delay(rand.nextInt(500) + 50);
			b.keyRelease(l.keyint);
		}
		else
		{
			b.keyPress(l.keyint);
			b.delay(rand.nextInt(50) + 50);
			b.keyPress(l.keyint2);
			b.delay(rand.nextInt(50) + 100);
			b.keyRelease(l.keyint2);
			b.delay(rand.nextInt(10) + 5);
			b.keyRelease(l.keyint);
		}
		b.delay(50+rand.nextInt(50));
	}
	public static void randomSpin()
	{
		b.keyPress(KeyEvent.VK_RIGHT);
		b.delay(rand.nextInt(3000)+100);
		b.keyRelease(KeyEvent.VK_RIGHT);
	}
	public static boolean isInventoryFull()
	{
		return getEmptySlots() == 0;
	}
	public static int findColorDistance(int rgb1, int rgb2)
	{
//		System.out.println("Testing " + rgb1 + " " + rgb2);
		
		double r1 = (rgb1 >> 16) & 0xFF;
		double g1 = (rgb1 >> 8) & 0xFF;
		double b1 = (rgb1 >> 0) & 0xFF;
		
		double r2 = (rgb2 >> 16) & 0xFF;
		double g2 = (rgb2 >> 8) & 0xFF;
		double b2 = (rgb2 >> 0) & 0xFF;
		
		double dis = Math.sqrt((Math.pow((r2-r1),2) + Math.pow((g2-g1),2) + Math.pow((b2-b1),2)));
//		System.out.println(dis);
		int ret = (int) Math.round(dis);
//		System.out.println(ret);
		return ret;
	}
	public static boolean isColorTolerated(int rgb1, int rgb2, int tolerance)
	{
		return findColorDistance(rgb1, rgb2) <= tolerance;
	}
	public static void mouseGlide(int x1, int y1, int x2, int y2, int t, int n)
	{  
		Robot r = b;
	    double dx = (x2 - x1) / ((double) n);
	    double dy = (y2 - y1) / ((double) n);
	    double dt = t / ((double) n);
	    for (int step = 1; step <= n; step++) {
	        try {
				Thread.sleep((int) dt);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	        r.mouseMove((int) (x1 + dx * step), (int) (y1 + dy * step));
	    }
	}
	public static void moveMouseTo(int x, int y)
	{
		mouseGlide(MouseInfo.getPointerInfo().getLocation().x,MouseInfo.getPointerInfo().getLocation().y,x,y,rand.nextInt(1250) + 500, rand.nextInt(500) + 725);
	}
	public static int findMenuOffset(int item)
	{
		return firstMenuOffset + menuOffset * item;
	}
	public static void naturalLeftClick()
	{
		b.delay(50 + rand.nextInt(100));
		b.mousePress(InputEvent.BUTTON1_MASK);
		b.delay(rand.nextInt(100));
		b.mouseRelease(InputEvent.BUTTON1_MASK);
	}
	public static void naturalRightClick()
	{
		b.delay(50 + rand.nextInt(100));
		b.mousePress(InputEvent.BUTTON3_MASK);
		b.delay(rand.nextInt(100));
		b.mouseRelease(InputEvent.BUTTON3_MASK);
	}
	public static void dropItemFromSlot(int slot)
	{
		hoverOverSlot(slot);
		b.delay(50 + rand.nextInt(250));
		b.mousePress(InputEvent.BUTTON1_MASK);
		moveMouseTo(inventorySlotPos[0].x - (rand.nextInt(200)-100),inventorySlotPos[0].y - rand.nextInt(50) - 100);
		b.delay(50 + rand.nextInt(250));
		b.mouseRelease(InputEvent.BUTTON1_MASK);
		b.delay(50 + rand.nextInt(250));
	}
	public static void dropAllItems()
	{
		for (int i = 0; i < inventorySlotPos.length; i++)
		{
			dropItemFromSlot(i);
		}
	}
	public static boolean isOriginalColorThere()
	{
		System.out.println("testing the following");
		printColor(pixelColor);
		System.out.println(" and ");
		printColor(originalColor);
		System.out.println("----------------------");
		return isColorTolerated(pixelColor.getRGB(), originalColor.getRGB(), 5);
	}
	public static boolean onFullInventory()
	{
		RunningGUI.setLabel("Inventory full!");
		if (IFBRadioButton.getSelectedIFB()==InventoryFullBehavior.DROP_WHEN_FULL)
		{
			System.out.println("dropping");
			dropAllItems();
			return false;
		}
		else if (IFBRadioButton.getSelectedIFB()==InventoryFullBehavior.ALERT_WHEN_FULL)
		{
			System.out.println("alerting");
			JOptionPane.showMessageDialog(null, "Your inventory is full!", "JBot", JOptionPane.INFORMATION_MESSAGE);
			return true;
		}
		else if (IFBRadioButton.getSelectedIFB()==InventoryFullBehavior.TP_WHEN_FULL)
		{
			System.out.println("teleporting");
			teleportToLodestone(LSRadioButton.getSelectedLodestone());
			JOptionPane.showMessageDialog(null, "Your inventory is full!", "JBot", JOptionPane.INFORMATION_MESSAGE);
			return true;
		}
		System.out.println("something is wrong");
		return true;
	}
}
