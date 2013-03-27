package transactions;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import model.Borrower;
import ui.LibraryDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import transactions.CheckAccountHelper;

public class CheckAccount extends JFrame{
	// dimensions of the window
	private List<JTextField> textFields = new ArrayList<JTextField>();	
	private List<Integer> bids = new ArrayList<Integer>();
	private List<String> passwords = new ArrayList<String>();
	private int WIDTH = 300;
	private int HEIGHT = 200;
	

	public CheckAccount(){
		super("Check My Account");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //Exits the window when user clicks "x"
		initPanel();

		// Center the window
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int x = screenSize.width/2 - WIDTH/2;
		int y = screenSize.height/2 - HEIGHT/2;
		this.setLocation(x,y);

		setSize(WIDTH,HEIGHT);
		setVisible(true);
	}
	
	// initializes the JPanel
	private void initPanel(){
		int scale = 10;
		int disp = 10;
		JPanel p = new JPanel();
		p.setLayout(null);
		JLabel label0 = new JLabel("Please enter your User ID and Password");
		label0.setBounds(0, 10, WIDTH, HEIGHT/scale);
		label0.setHorizontalAlignment(SwingConstants.CENTER);
		label0.setVerticalAlignment(SwingConstants.CENTER);
		p.add(label0);
		
		JLabel label1 = new JLabel("User ID: ");
		label1.setBounds(0, HEIGHT*2/scale, WIDTH/3, HEIGHT/scale);
		label1.setHorizontalAlignment(SwingConstants.RIGHT);
		label1.setVerticalAlignment(SwingConstants.CENTER);
		p.add(label1);
		
		// UserID textfield
		JTextField tf1 = new JTextField();
		tf1.setBounds(WIDTH/3, HEIGHT*2/scale , WIDTH/3 , HEIGHT/scale);
		textFields.add(tf1);
		p.add(tf1);
		
		JLabel label2 = new JLabel("Password: ");
		label2.setBounds(0, HEIGHT*4/scale, WIDTH/3, HEIGHT/scale);
		label2.setHorizontalAlignment(SwingConstants.RIGHT);
		label2.setVerticalAlignment(SwingConstants.CENTER);
		p.add(label2);
		
		// Password textfield
		JTextField tf2 = new JTextField();
		tf2.setBounds(WIDTH/3, HEIGHT*4/scale, WIDTH/3 , HEIGHT/scale);
		textFields.add(tf2);
		p.add(tf2);
		
        // Confirm Button
		JButton addBookButton = new JButton("Confirm");
		addBookButton.setBounds(WIDTH/8, 5*HEIGHT/8, WIDTH/4, HEIGHT/10);
		addBookButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				confirmOnClick();	
			}
		});
		p.add(addBookButton);
		
		// Cancel Button
		JButton continueButton = new JButton("Cancel");
		continueButton.setBounds(WIDTH*5/8, 5*HEIGHT/8, WIDTH/4, HEIGHT/10);
		continueButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				cancelOnClick();
			}
		});
		p.add(continueButton);
		
		this.add(p);
	}
	
	private void confirmOnClick(){
		
		int bid = Integer.parseInt(textFields.get(0).getText().trim());
		String password;
		password = textFields.get(1).getText().trim();
		
		// Check if borrower ID and password match.
		if(!LibraryDB.getManager().checkPassword(password,bid)){
			popMsg("User ID and Password don't match!");
			return;
		}
		
		new CheckAccountHelper(bid,password);
	}
	
	public void cancelOnClick(){
		this.dispose();
	}
		
	private void popMsg(String msg){
		JOptionPane.showMessageDialog (this, msg);
	}

}
