package ui;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class UserFrame extends JFrame{
	
	private UserPanel userPanel;
	
	// dimensions of the window
	private int WIDTH = 200;
	private int HEIGHT = 200;
	
	public UserFrame(){
		super("LibraryDB");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Exits the app when user clicks "x"
		userPanel = new UserPanel(this);
		add(userPanel);
		
		// Center the window
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int x = screenSize.width/2 - WIDTH/2;
		int y = screenSize.height/2 - HEIGHT/2;
		this.setLocation(x - WIDTH/2,y - HEIGHT/2);
		
		setSize(WIDTH,HEIGHT);
		setVisible(true);
		
		
	}
	
	
}
