package ui;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class DisplayDB extends JFrame{
	
	private DisplayDBPanel displayPanel;
	
	// dimensions of the window
	private int WIDTH = 200;
	private int HEIGHT = 700;
	
	public DisplayDB(){
		super("Display");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Exits the app when user clicks "x"
		displayPanel = new DisplayDBPanel();
		add(displayPanel);
		
		this.setLocation(0,0);
		
		setSize(WIDTH,HEIGHT);
		setVisible(true);
		
		
	}
	
	
}
