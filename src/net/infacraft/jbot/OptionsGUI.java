package net.infacraft.jbot;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class OptionsGUI {
	public static OptionsFrame frame;
	public static JLabel label;
	public static JButton pause;
	
	public static void init()
	{
		frame = new OptionsFrame();
		frame.setVisible(true);
	}
}
class OptionsFrame extends JFrame
{
	private static final long serialVersionUID = -6871065779730938964L;
	JPanel closePanel;
	JLabel closeButton;
	
	// ALL THE BUTTONS \\
	
	// top buttons \\
	JLabel btnGO = new JLabel();
	JLabel btnX = new JLabel();
	
	// inventory \\
	RadioButton rbDWF = new IFBRadioButton(0,InventoryFullBehavior.DROP_WHEN_FULL);
	RadioButton rbTP = new IFBRadioButton(0,InventoryFullBehavior.TP_WHEN_FULL);
	RadioButton rbAWF = new IFBRadioButton(0,InventoryFullBehavior.ALERT_WHEN_FULL);
	
	// lodestone \\
	RadioButton lL = new LSRadioButton(1,Lodestone.LUMBRIDGE);
	RadioButton lB = new LSRadioButton(1,Lodestone.BURTHORPE);
	RadioButton lLI = new LSRadioButton(1,Lodestone.LUNAR_ISLE);
	RadioButton lBC = new LSRadioButton(1,Lodestone.BANDIT_CAMP);
	RadioButton lT = new LSRadioButton(1,Lodestone.TAVERLY);
	RadioButton lAK = new LSRadioButton(1,Lodestone.ALKHARID);
	RadioButton lV = new LSRadioButton(1,Lodestone.VARROCK);
	RadioButton lE = new LSRadioButton(1,Lodestone.EDGEVILLE);
	RadioButton lF = new LSRadioButton(1,Lodestone.FALADOR);
	RadioButton lPS = new LSRadioButton(1,Lodestone.PORT_SARIM);
	RadioButton lD = new LSRadioButton(1,Lodestone.DRAYNOR);
	RadioButton lAR = new LSRadioButton(1,Lodestone.ARDOUGNE);
	RadioButton lCA = new LSRadioButton(1,Lodestone.CATHERBY);
	RadioButton lY = new LSRadioButton(1,Lodestone.YANILLE);
	RadioButton lSV = new LSRadioButton(1,Lodestone.SEERS_VILLAGE);
	RadioButton lEP = new LSRadioButton(1,Lodestone.EAGLES_PEAK);
	RadioButton lTI = new LSRadioButton(1,Lodestone.TIRANNWN);
	RadioButton lOO = new LSRadioButton(1,Lodestone.OOGLOOG);
	RadioButton lK = new LSRadioButton(1,Lodestone.KARAMJA);
	RadioButton lCAN = new LSRadioButton(1,Lodestone.CANIFIS);
	RadioButton lW = new LSRadioButton(1,Lodestone.WILDERNESS_VOLCANO);
	RadioButton lFR = new LSRadioButton(1,Lodestone.FREMENNIK);
	RadioButton lAS = new LSRadioButton(1,Lodestone.ASHDALE);
	
	// bottom buttons \\
	JLabel btnCfgINV = new JLabel();
	JLabel btnCheckINV = new JLabel();
	JLabel btnAbout = new JLabel();
	JLabel btnDonate = new JLabel();
	
	// help button \\
	JLabel btnHelp = new JLabel();
	
	public OptionsFrame()
	{
		init();
		showWindow();
		installListeners();
	}
	
	public void init()
	{
		setContentPane(new JLabel( new ImageIcon( getClass().getResource( "img/jbot-gui.png" ) ) ));
		setUndecorated(true);
		setLayout(null);
		getContentPane().setLayout(null);
		closePanel = new JPanel(new BorderLayout());
		getContentPane().setBackground(new Color(255,255,255));
		setSize(504,360);
		
		// top \\
		btnGO.setBounds(0, 0, 66, 38);
		add(btnGO);
		
		btnX.setBounds(437,0,67,39);
		add(btnX);
		
		// inventory \\
		rbDWF.setBounds( 23, 116, 13, 10);
		rbTP.setBounds(23,129,13,10);
		rbAWF.setBounds(23,142,13,10);
		addLots(rbDWF,rbTP,rbAWF);
		
		// lodestones \\
		lL.setBounds(23,177,13,10);
		lB.setBounds(23,189,13,10);
		lLI.setBounds(23,201,13,10);
		lBC.setBounds(23,213,13,10);
		lT.setBounds(23, 225, 13, 10);
		lAK.setBounds(23, 237, 13, 10);
		lV.setBounds(23, 249, 13, 10);
		lE.setBounds(23,261,13,10);
		lF.setBounds(23, 273,13,10);
		lPS.setBounds(23, 285,13,10);
		lD.setBounds(142, 177,13,10);
		lAR.setBounds(142, 189,13,10);
		lCA.setBounds(142, 201,13,10);
		lY.setBounds(142, 213,13,10);
		lSV.setBounds(142, 225,13,10);
		lEP.setBounds(142, 237,13,10);
		lTI.setBounds(142, 249,13,10);
		lOO.setBounds(142, 261,13,10);
		lK.setBounds(142, 273,13,10);
		lCAN.setBounds(142, 285,13,10);
		lW.setBounds(273, 177,13,10);
		lFR.setBounds(273, 189,13,10);
		lAS.setBounds(273, 201,13,10);
		addLots(lL,lB,lLI,lBC,lT,lAK,lV,lE,lF,lPS,lD,lAR,lCA,lY,lSV,lEP,lTI,lOO,lK,lCAN,lW,lFR,lAS);

		// bottom buttons \\
		btnCfgINV.setBounds(12,308,101,45);
		btnCheckINV.setBounds(123,308,101,45);
		btnAbout.setBounds(281, 308, 101, 45);
		btnDonate.setBounds(392,308,101,45);
		addLots(btnCfgINV,btnCheckINV, btnAbout, btnDonate);
		
		// help button \\
		btnHelp.setBounds(469,74,34,27);
		add(btnHelp);
		
		// default settings \\
		rbDWF.rbSelect();
		lAK.rbSelect();
	}
	public void addLots(Component... c)
	{
		for (Component com : c)
		{
			add(com);
		}
	}
	private void showWindow()
	{
	    // close "button" - show this image by default
	    closeButton = new JLabel( new ImageIcon( getClass().getResource( "img/closebutton.png" ) ) );
	 
	    // Put the label with the image on the far right
	    closePanel.add( closeButton, BorderLayout.EAST );
//	    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	    setLocationRelativeTo(null);
	 
	    // keep window on top of others
	    setAlwaysOnTop( true );
	}
	private Point initialClick = new Point(0,0);
	private void installListeners()
	{
	    // Get point of initial mouse click
	    addMouseListener( new MouseListener()
	    { public void mousePressed(MouseEvent arg0) { initialClick = arg0.getPoint(); }public void mouseClicked(MouseEvent arg0) {}public void mouseEntered(MouseEvent arg0) {}public void mouseExited(MouseEvent arg0) {		}		public void mouseReleased(MouseEvent arg0) {		}});
	 
	    // Move window when mouse is dragged
	    addMouseMotionListener( new MouseMotionAdapter() {
	        public void mouseDragged( MouseEvent e )
	        {
	            int thisX = getLocation().x; int thisY = getLocation().y;
	            int xMoved = ( thisX + e.getX() ) - ( thisX + initialClick.x );
	            int yMoved = ( thisY + e.getY() ) - ( thisY + initialClick.y );
	            int X = thisX + xMoved; int Y = thisY + yMoved; setLocation( X, Y );
	        }
	    }); 
	    
	    // top buttons \\
	    btnGO.addMouseListener(new MouseAdapter()
	    {
	    	public void mouseClicked(MouseEvent e)
	    	{
	    		close();
	    		RunningGUI.init();
	    		Bot.normalStart();
	    	}
	    });
	    
	    btnX.addMouseListener(new MouseAdapter()
	    {
	    	public void mouseClicked(MouseEvent e)
	    	{
	    		close();
	    	}
	    });
	    btnAbout.addMouseListener(new MouseAdapter()
	    {
	    	public void mouseClicked(MouseEvent e)
	    	{
	    		setAlwaysOnTop(false);
	    		GUI.showAlert(
	    				"JBOT is a simple color based bot for Runescape.",
	    				"Currently there have been no reported bans.",
	    				"This is an InfaCraft project, check out our",
	    				"website at InfaCraft.net for more information.",
	    				"Pull requests are accepted and welcomed.",
	    				"Remember to run this program in moderation,",
	    				"never more than 8 hours a day and check on it",
	    				"occasionally.",
	    				"If you paid for JBOT get a refund. This is",
	    				"freely available software.",
	    				"Thank you for using JBOT!");
	    		setAlwaysOnTop(true);
	    	}
	    });
	    
	    btnDonate.addMouseListener(new MouseAdapter()
	    {
	    	public void mouseClicked(MouseEvent e)
	    	{
	    		setAlwaysOnTop(false);
	    		GUI.showAlert(
	    				"Donations can be sent directly through",
	    				"Paypal to jkush321@yahoo.com, where they",
	    				"will be manually divided between devs.",
	    				"An automated system is coming soon,",
	    				"Thank you for your kindness :)");
	    		setAlwaysOnTop(true);
	    	}
	    });
	    
	    btnHelp.addMouseListener(new MouseAdapter()
	    {
	    	public void mouseClicked(MouseEvent e)
	    	{
	    		setAlwaysOnTop(false);
	    		GUI.showAlert(
	    				"JBOT can be confusing to use the first time.",
	    				"THIS TEXT BASED TUTORIAL CAN BE HARD TO",
	    				"UNDERSTAND, OUR YOUTUBE TUTORIAL SHOULD BE",
	    				"MUCH MORE HELPFUL.",
	    				"YouTube channel URL: youtube.com/jkush321",
	    				"You can also find the tutorial at InfaCraft.net",
	    				"\nText based tutorial:",
	    				"As a start, make sure Runescape is running ",
	    				"(preferably at a low graphics setting)",
	    				"Then, you should stand over the ore, tree,",
	    				"or etc. that the bot will use...",
	    				"IMPORTANT: EMPTY YOUR INVENTORY BEFORE",
	    				"YOU CONTINUE!!!",
	    				"Now, click the 'Config INV' button.",
	    				"Put your mouse over the first slot of your",
	    				"inventory, when a blue box appears that slot",
	    				"has been saved.",
	    				"Do this for all 28 slots... it will take a few",
	    				"minutes, but it is worth the time.",
	    				"After you choose your config, you can click GO.",
	    				"Now, you have to properly position the mouse",
	    				"over the ore in a rock, a tree (above where the",
	    				"stump will appear). When the bot is ready, start",
	    				"it by clicking the ore/tree/whatever. It will now",
	    				"continue this action every time the color of the",
	    				"pixel returns to the original color.",
	    				"The bot will now click until inventory is full,",
	    				"and then perform the action you chose at the",
	    				"launch screen.",
	    				"\nAgain, the video tutorial is much easier to use.",
	    				"Thanks for using JBOT!");
	    		setAlwaysOnTop(true);
	    	}
	    	
	    });
	    
	    // inv buttons \\
    	btnCheckINV.addMouseListener(new MouseAdapter()
	    {
	    	public void mouseClicked(MouseEvent e)
	    	{
	    		setVisible(false);
	    		RunningGUI.init();
	    		Bot.checkStart();
	    	}
	    });
    	btnCfgINV.addMouseListener(new MouseAdapter()
	    {
	    	public void mouseClicked(MouseEvent e)
	    	{
	    		setVisible(false);
	    		Config.deleteSlots();
	    		RunningGUI.init();
	    		Bot.configStart();
	    	}
	    });
	}
	 
	// close and dispose
	public void close()
	{
	    setVisible( false ); dispose();
	}	
}