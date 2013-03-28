package transactions;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import model.Book;
import model.Borrower;
import model.Borrowing;
import model.Fine;
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
		
	public CheckAccountHelper(int bid) {
		super("Account Information");
		this.bid = bid;
		
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
		ArrayList<Borrowing> borrowings = LibraryDB.getManager().getBorrowingByID(bid);
		for (Borrowing borrowing: borrowings){
			ArrayList<Book> books = LibraryDB.getManager().searchBookByCallID(borrowing.getCallNumber());
			Book book = books.get(0);
			ta0.append("Call No.: " + borrowing.getCallNumber()
					+ " || Copy No.: " + borrowing.getBid() 
					+ " || Title: " + book.getTitle()
					+ " || Out Date: " + borrowing.getOutDate() 
					+ "\n");
		}
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
		int borid = LibraryDB.getManager().getBorrowingByID(bid).get(0).getBorid();
		ArrayList<Fine> fines = LibraryDB.getManager().getFineByID(borid);
		for (Fine fine: fines){
			// show fines that are not paid yet
			if (fine.getPaidDate() == null){
				ta1.append("Fine ID: " + fine.getFid()
						+ " || Borrowing ID: " + fine.getBorid()
						+ " || Amount: " + fine.getAmount() 
						+ " || Issue Date: " + fine.getIssueDate() 
						+ "\n");
			}
		}
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
			ta2.append("Call Number: " + book.getCallNumber()
					+ " || ISBN: " + book.getIsbn() 
					+ " || Title: " + book.getTitle()
					+ " || Main Author: " + book.getMainAuthor()
					+ " || Publisher: " + book.getPublisher()
					+ " || Year: " + book.getYear() + "\n");
		}
		textAreas.add(ta2);
		p.add(ta2);
		
		this.add(p);
	}
}
