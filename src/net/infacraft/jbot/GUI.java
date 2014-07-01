package net.infacraft.jbot;

import javax.swing.JOptionPane;

public class GUI {
	public static void showAlert(String msg)
	{
		JOptionPane.showMessageDialog(null, msg, "JBOT Alert", JOptionPane.INFORMATION_MESSAGE);
	}
}
