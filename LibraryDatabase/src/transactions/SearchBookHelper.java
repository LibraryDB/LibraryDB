package transactions;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import model.Book;
import model.BookCopy;
import model.Borrowing;
import model.HasSubject;
import ui.LibraryDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SearchBookHelper extends JFrame{
	// dimensions of the window
	private int WIDTH = 800;
	private int HEIGHT = 500;
	private String value;
	private int flag;
	private List<JTextArea> textAreas = new ArrayList<JTextArea>();
	
	public SearchBookHelper(int flag, String value){
		super("Search Results");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //Exits the window when user clicks "x"
		
		this.flag = flag;
		this.value = value;
//		System.out.println(value);
//		System.out.println(flag);
		initPanel();

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
		
//		// Search Result Label
//		JLabel label0 = new JLabel("Search Results");
//		label0.setBounds(WIDTH/4, 0, WIDTH/3, HEIGHT/scale);
//		label0.setHorizontalAlignment(SwingConstants.CENTER);
//		label0.setVerticalAlignment(SwingConstants.CENTER);
//		p.add(label0);
			
		JTextArea ta0 = new JTextArea();
		ta0.setBounds(scale, HEIGHT*1/scale , WIDTH-2*scale , HEIGHT*2/scale);
		ta0.setEditable(false);
		switch(flag){
		case 0:
//			System.out.println(value);
			ArrayList<Book> booksByID = LibraryDB.getManager().searchBookByCallID(value);
			for (Book bookByID: booksByID){
				ta0.append("Call Number: " + bookByID.getCallNumber()
						+ " || ISBN: " + bookByID.getIsbn() 
						+ " || Title: " + bookByID.getTitle()
						+ " || Main Author: " + bookByID.getMainAuthor()
						+ " || Publisher: " + bookByID.getPublisher()
						+ " || Year: " + bookByID.getYear() + "\n");
			}
			break;
		case 1:
			ArrayList<Book> booksByAuthor = LibraryDB.getManager().searchBookByAuthor(value);
			System.out.println(value);
			for (Book bookByAuthor: booksByAuthor){
				ArrayList<BookCopy> ins = LibraryDB.getManager().searchBookCopyByCallID(bookByAuthor.getCallNumber(),BookCopy.IN);
				ArrayList<BookCopy> outs = LibraryDB.getManager().searchBookCopyByCallID(bookByAuthor.getCallNumber(),BookCopy.OUT);
				int in = ins.size(); int out = outs.size();
				ta0.append("Call Number: " + bookByAuthor.getCallNumber()
						+ " || ISBN: " + bookByAuthor.getIsbn() 
						+ " || Title: " + bookByAuthor.getTitle()
						+ " || Main Author: " + bookByAuthor.getMainAuthor()
						+ " || Publisher: " + bookByAuthor.getPublisher()
						+ " || Year: " + bookByAuthor.getYear() 
						+ " || in: " + in
						+ " || out: " + out + "\n");
			}
			break;
		case 2:
			ArrayList<HasSubject> hasSubjects = LibraryDB.getManager().getSubject(value);
			ArrayList<String> callnumbers = new ArrayList<String>();
			for (HasSubject hs: hasSubjects)
			{
				if (!callnumbers.contains(hs.getCallNumber())) callnumbers.add(hs.getCallNumber());
			}
			
			ArrayList<Book> booksBySubject = new ArrayList<Book>();

			for (String c :callnumbers)
			{
				booksBySubject.addAll(LibraryDB.getManager().searchBookByCallID(c));
			}
			
			for (Book bookBySubject: booksBySubject){
				ta0.append("Call Number: " + bookBySubject.getCallNumber()
						+ " || ISBN: " + bookBySubject.getIsbn() 
						+ " || Title: " + bookBySubject.getTitle()
						+ " || Main Author: " + bookBySubject.getMainAuthor()
						+ " || Publisher: " + bookBySubject.getPublisher()
						+ " || Year: " + bookBySubject.getYear() + "\n");
			}
			break;
		default:
			break;
		}
		textAreas.add(ta0);
		p.add(ta0);
		
		
		this.add(p);
	}
}
