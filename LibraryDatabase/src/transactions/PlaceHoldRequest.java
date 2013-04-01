package transactions;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import model.HoldRequest;

import ui.LibraryDB;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class PlaceHoldRequest extends JFrame{
	// dimensions of the window
	private List<JTextField> textFields = new ArrayList<JTextField>();	
	private int WIDTH = 300;
	private int HEIGHT = 200;
	

	public PlaceHoldRequest(){
		super("Place a Hold Request");
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
		JPanel p = new JPanel();
		p.setLayout(null);
		
		// Date Field
		JLabel label0 = new JLabel("Date:");
		label0.setBounds(10, 10, WIDTH/3+10, HEIGHT/scale);
		label0.setHorizontalAlignment(SwingConstants.RIGHT);
		label0.setVerticalAlignment(SwingConstants.CENTER);
		p.add(label0);
		
		JTextField tf0 = new JTextField(new java.text.SimpleDateFormat("yyyy/MM/dd").format(new java.util.Date()));
		tf0.setBounds(WIDTH/2, 10 , WIDTH/3+10, HEIGHT/scale);
		textFields.add(tf0);
		p.add(tf0);
		
		// FID Field
		JLabel label1 = new JLabel("Borrower ID: ");
		label1.setBounds(10, HEIGHT/scale+20, WIDTH/3+10, HEIGHT/scale);
		label1.setHorizontalAlignment(SwingConstants.RIGHT);
		label1.setVerticalAlignment(SwingConstants.CENTER);
		p.add(label1);
		
		JTextField tf1 = new JTextField();
		tf1.setBounds(WIDTH/2, HEIGHT/scale+20, WIDTH/3+10, HEIGHT/scale);
		textFields.add(tf1);
		p.add(tf1);
		
		// Amount Field
		JLabel label2 = new JLabel("Book Call No.: ");
		label2.setBounds(10, HEIGHT*3/scale+10, WIDTH/3+10, HEIGHT/scale);
		label2.setHorizontalAlignment(SwingConstants.RIGHT);
		label2.setVerticalAlignment(SwingConstants.CENTER);
		p.add(label2);
		
		JTextField tf2 = new JTextField();
		tf2.setBounds(WIDTH/2, HEIGHT*3/scale+10, WIDTH/3+10, HEIGHT/scale);
		textFields.add(tf2);
		p.add(tf2);
		
		// Copy No Field
		
		
        // Confirm Button
		JButton confirmButton = new JButton("Confirm");
		confirmButton.setBounds(WIDTH/8, 5*HEIGHT/8, WIDTH/4, HEIGHT/10);
		confirmButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				confirmOnClick();	
				popMsg("An hold request is placed.");
			}
		});
		p.add(confirmButton);
		
		// Cancel Button
		JButton cancelButton = new JButton("Cancel");
		cancelButton.setBounds(WIDTH*5/8, 5*HEIGHT/8, WIDTH/4, HEIGHT/10);
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				cancelOnClick();
			}
		});
		p.add(cancelButton);
		
		this.add(p);
	}
	
	private void confirmOnClick(){
		String date;
		date = textFields.get(0).getText().trim();
		int bid = Integer.parseInt(textFields.get(1).getText().trim());
		String callNumber = textFields.get(2).getText().trim();
		
		HoldRequest hr = new HoldRequest(0,bid,callNumber,date);
		LibraryDB.getManager().insertHoldRequest(hr);
		
		// update book copy status
		LibraryDB.getManager().updateBookCopyStatus(callNumber);
	}

	public void cancelOnClick(){
		this.dispose();
	}
	
	private void popMsg(String msg){
		JOptionPane.showMessageDialog (this, msg);
	}

}
