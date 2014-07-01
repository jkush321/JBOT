package net.infacraft.jbot;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class RunningGUI {
	public static JFrame frame;
	public static JLabel label;
	public static JButton pause;
	public static JLabel close;
	private static Point initialClick = new Point(0,0);
	
	public static void init()
	{
		frame = new JFrame();
		frame.setSize(250, 60);
		frame.setAlwaysOnTop(true);
		frame.setLocation(0, 0);
		frame.setTitle("JBot");
		frame.setUndecorated(true);
		frame.setContentPane(new JLabel(new ImageIcon(RunningGUI.class.getResource("img/jbot-running-gui.png"))));
		frame.addMouseListener( new MouseAdapter()
	    { public void mousePressed(MouseEvent arg0) { initialClick = arg0.getPoint(); }});
		frame.addMouseMotionListener( new MouseMotionAdapter() {
	        public void mouseDragged( MouseEvent e )
	        {
	            int thisX = frame.getLocation().x; int thisY = frame.getLocation().y;
	            int xMoved = ( thisX + e.getX() ) - ( thisX + initialClick.x );
	            int yMoved = ( thisY + e.getY() ) - ( thisY + initialClick.y );
	            int X = thisX + xMoved; int Y = thisY + yMoved; frame.setLocation( X, Y );
	        }
	    }); 
		
		label = new JLabel();
		label.setText("Focus on the RS window");
		label.setBounds(5,12,240,60);
        Font font;
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, RunningGUI.class.getResourceAsStream("img/Furore.ttf"));
	        label.setFont(font.deriveFont(Font.PLAIN, 14f));
		} catch (Exception e1) {
		}

		close = new JLabel();
		close.setBounds(220,0,30,19);
		close.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				frame.setVisible(false);
				frame.dispose();
				System.exit(0);
			}
		});
		
		frame.setLayout(null);
		frame.getContentPane().setLayout(null);
		frame.add(label);
		frame.add(close);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setVisible(true);
		
		SwingUtilities.invokeLater(() -> {
			(new Thread(() -> {
				try {
					Thread.sleep(5000);
				} catch (Exception e1) {}
				Bot.findInvSlots();
				Bot.frameTheSlots();
				Bot.waitForColorChange();})).start();
		});
	}
	public static void setLabel(String s)
	{
		label.setText(s);
	}
	public static void setColor(Color c)
	{
		frame.getContentPane().setBackground(inverseColor(Bot.pixelColor));
		label.setForeground(Bot.pixelColor);
	}
	public static Color inverseColor(Color c)
	{
		return new Color(255 - c.getRed(), 255 - c.getGreen(), 255 - c.getBlue());
	}
}
