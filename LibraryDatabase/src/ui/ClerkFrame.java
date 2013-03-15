package ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class ClerkFrame extends JFrame{
	private int WIDTH = 200;
	private int HEIGHT = 200;
	
	public ClerkFrame(){
		super("Clerk");
		setBackground(Color.GREEN);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ClerkPanel cp = new ClerkPanel();
		add(cp);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int x = screenSize.width/2 - this.getWidth()/2;
		int y = screenSize.height/2 - this.getHeight()/2;
		this.setLocation(x - WIDTH/2,y - HEIGHT/2);
		setSize(WIDTH,HEIGHT);
		setVisible(true);
	}

}
