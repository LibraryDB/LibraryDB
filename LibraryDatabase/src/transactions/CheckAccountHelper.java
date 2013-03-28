package transactions;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import model.Book;
import model.Borrower;
import ui.LibraryDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class CheckAccountHelper extends JFrame {
	// dimensions of the window
	private int WIDTH = 800;
	private int HEIGHT = 500;
	private List<JTextArea> textAreas = new ArrayList<JTextArea>();
	private List<JTextField> textFields = new ArrayList<JTextField>();	
	private int bid;
	private String password;
		
	public CheckAccountHelper(int bid, String password) {
		super("Account Information");
		this.bid = bid;
		this.password = password;
		
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
		
		//Outstanding Borrowing 
		JLabel label0 = new JLabel("Outstanding Borrowing: ");
		label0.setBounds(scale, 0, WIDTH/3, HEIGHT/scale);
		label0.setHorizontalAlignment(SwingConstants.LEFT);
		label0.setVerticalAlignment(SwingConstants.CENTER);
		p.add(label0);
		
		JTextArea ta0 = new JTextArea();
		ta0.setBounds(scale, HEIGHT*1/scale , WIDTH-2*scale , HEIGHT*2/scale);
		ta0.setEditable(false);
		textAreas.add(ta0);
		p.add(ta0);
		
		//Outstanding Fine
		JLabel label1 = new JLabel("Outstanding Fine: ");
		label1.setBounds(scale, HEIGHT*3/scale, WIDTH/3, HEIGHT/scale);
		label1.setHorizontalAlignment(SwingConstants.LEFT);
		label1.setVerticalAlignment(SwingConstants.CENTER);
		p.add(label1);
		
		JTextArea ta1 = new JTextArea();
		ta1.setBounds(scale, HEIGHT*4/scale , WIDTH-2*scale , HEIGHT*2/scale);
		ta1.setEditable(false);
		textAreas.add(ta1);
		p.add(ta1);
		
		// Fine Payment Button
		JButton addBookButton = new JButton("Pay a Fine");
		addBookButton.setBounds(WIDTH-WIDTH/scale-5*scale, HEIGHT*3/scale+scale, WIDTH/8, HEIGHT/20);
		addBookButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				new PayFine();	
				System.out.println("Pay Fine");
			}
		});
		p.add(addBookButton);
		
		//Hold Request
		JLabel label2 = new JLabel("Hold Requests: ");
		label2.setBounds(scale, HEIGHT*6/scale, WIDTH/3, HEIGHT/scale);
		label2.setHorizontalAlignment(SwingConstants.LEFT);
		label2.setVerticalAlignment(SwingConstants.CENTER);
		p.add(label2);
		
		JTextArea ta2 = new JTextArea();
		ta2.setBounds(scale, HEIGHT*7/scale , WIDTH-2*scale , HEIGHT*2/scale);
		ta2.setEditable(false);
		ArrayList<Book> books = LibraryDB.getManager().getBookOnHold(bid);
		for (Book book : books){
			ta2.append("callNumber: " + book.getCallNumber()
					+ " || isbn: " + book.getIsbn() 
					+ " || title: " + book.getTitle()
					+ " || mainAuthor: " + book.getMainAuthor()
					+ " || publisher: " + book.getPublisher()
					+ " || year: " + book.getYear() + "\n");
		}
		textAreas.add(ta2);
		p.add(ta2);
		
		this.add(p);
	}
}
