package transactions;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import model.Book;
import model.Borrowing;
import ui.LibraryDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SearchBook extends JFrame {
	// dimensions of the window
	private List<JTextField> textFields = new ArrayList<JTextField>();	
	private int WIDTH = 300;
	private int HEIGHT = 300;
	private int flag;
	
	public SearchBook() {
		
		super ("Search Books");
		
		initPanel();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //Exits the window when user clicks "x"
		
		// Center the window
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int x = screenSize.width/2 - WIDTH/2;
		int y = screenSize.height/2 - HEIGHT/2;
		this.setLocation(x,y);

		setSize(WIDTH,HEIGHT);
		setVisible(true);
	}
	
	private void initPanel(){
		int scale = 10;
		JPanel p = new JPanel();
		p.setLayout(null);
		
		// Search Field
		JLabel label0 = new JLabel("Search Field: ");
		label0.setBounds(WIDTH/scale, HEIGHT/scale, WIDTH/3, HEIGHT/scale);
		label0.setHorizontalAlignment(SwingConstants.LEFT);
		label0.setVerticalAlignment(SwingConstants.CENTER);
		p.add(label0);
		
		JTextField tf0 = new JTextField();
		tf0.setBounds(WIDTH/3+scale+5, HEIGHT/scale , WIDTH*5/scale , HEIGHT/scale);
		textFields.add(tf0);
		p.add(tf0);
		
		// By Call Number
		JRadioButton byCallNumber = new JRadioButton("By Call Number");
		byCallNumber.setBounds(WIDTH/scale-scale, HEIGHT/(scale/2)+scale, WIDTH/2, HEIGHT/scale);
		byCallNumber.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				flag = 0;	
			}
		});
		p.add(byCallNumber);
		
		// By Author
		JRadioButton byAuthor = new JRadioButton("By Author");
		byAuthor.setBounds(WIDTH/scale-scale, HEIGHT/(scale/2)+4*scale, WIDTH/2, HEIGHT/scale);
		byAuthor.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				flag = 1;	
			}
		});
		p.add(byAuthor);
		
		// By Subject
		JRadioButton bySubject = new JRadioButton("By Subject");
		bySubject.setBounds(WIDTH/scale-scale, HEIGHT/(scale/2)+7*scale, WIDTH/2, HEIGHT/scale);
		bySubject.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				flag = 2;	
			}
		});
		p.add(bySubject);

		// Search Button
		JButton searchButton = new JButton("Confirm");
		searchButton.setBounds(WIDTH/8, 5*HEIGHT/8, WIDTH/4, HEIGHT/10);
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				new SearchBookHelper(flag);	
			}
		});
		p.add(searchButton);
		
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
	
	public void cancelOnClick(){
		this.dispose();
	}
		
	private void popMsg(String msg){
		JOptionPane.showMessageDialog (this, msg);
	}
}
