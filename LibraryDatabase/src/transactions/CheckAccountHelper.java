package transactions;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import model.Borrower;
import ui.LibraryDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class CheckAccountHelper extends JFrame {
	// dimensions of the window
	private int WIDTH = 400;
	private int HEIGHT = 500;
	private int bid;
	private String password;
		
	public CheckAccountHelper(int bid, String password) {
		super("Account Information");
		this.bid = bid;
		this.password = password;
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //Exits the window when user clicks "x"
		
		// Center the window
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int x = screenSize.width/2 - WIDTH/2;
		int y = screenSize.height/2 - HEIGHT/2;
		this.setLocation(x,y);

		setSize(WIDTH,HEIGHT);
		setVisible(true);
	}
}
