package ui;

import model.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class DisplayTable extends JFrame{

	// dimensions of the window
	private int WIDTH = 850;
	private int HEIGHT = 500;
	final JTextArea textArea = new JTextArea();

	public DisplayTable(int index, String name){
		super(name);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //Exits the window when user clicks "x"

		initPanel();
		updateTextArea(index);
		// Center the window
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int x = screenSize.width/2 - WIDTH/2;
		int y = screenSize.height/2 - HEIGHT/2;
		this.setLocation(x,y);

		setSize(WIDTH,HEIGHT);
		setVisible(true);
	}

	private void initPanel() {
		JPanel p = new JPanel();
		p.setLayout(null);
		textArea.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setBounds(0, 0, WIDTH-50, HEIGHT-50);;
		add(scrollPane, BorderLayout.CENTER);
		
	}

	private void updateTextArea(int i){
		switch (i){
		case 0:
			ArrayList<Book> books = LibraryDB.getManager().getBook();
			for (Book book : books){
				textArea.append("callNumber: " + book.getCallNumber()
						+ " || isbn: " + book.getIsbn() 
						+ " || title: " + book.getTitle()
						+ " || mainAuthor: " + book.getMainAuthor()
						+ " || publisher: " + book.getPublisher()
						+ " || year: " + book.getYear() + "\n");
			}
			break;
			
		case 1:
			ArrayList<BookCopy> a = LibraryDB.getManager().getBookCopy();
			for (BookCopy b: a){
				textArea.append("callNumber: " + b.getCallNumber()
						+ " || copyNo: " + b.getCopyNo()
						+ " || status: " + b.getStatus() + "\n");
			}
			break;
			
		case 2: 
			ArrayList<Borrower> borrowers = LibraryDB.getManager().getBorrower();
			for (Borrower b: borrowers){
				textArea.append("bid: " + b.getBid()
						+ " || password: " + b.getPassword()
						+ " || name: " + b.getName()
						+ " || address: " + b.getAddress()
						+ " || phone: " + b.getPhone()
						+ " || emailAddress: " + b.getEmailAddress()
						+ " || sinOrStNo: " + b.getSinOrStNo()
						+ " || expiryDate: " + b.getExpiryDate()
						+ " || type: " + b.getType()+"\n");
			}
			break;
			
		case 3:
			ArrayList<BorrowerType> bts = LibraryDB.getManager().getBorrowerType();
			for (BorrowerType b: bts){
				textArea.append("type: " + b.getType()
						+ " || timeLimit: " + b.getBookTimeLimit() + "\n");
			}
			break;
			
		case 4:
			ArrayList<Borrowing> borrowings = LibraryDB.getManager().getBorrowing();
			for (Borrowing b : borrowings){
				textArea.append("borid: " + b.getBorid()
						+ " || bid: " + b.getBid()
						+ " || callNumber: " + b.getCallNumber()
						+ " || copyNo: " + b.getCopyNo()
						+ " || outDate: " + b.getOutDate()
						+ " || inDate: " + b.getInDate() + "\n");
			}
			break;
			
		case 5:
			ArrayList<Fine> fs = LibraryDB.getManager().getFine();
			for (Fine b: fs){
				textArea.append("fid: " + b.getFid()
						+ " || amount: " + b.getAmount()
						+ " || issueDate: " + b.getIssueDate()
						+ " || paidDate: " + b.getPaidDate()
						+ " || borid: " + b.getBorid() + "\n" );
				
			}
			break;
			
		case 6:
			ArrayList<HasAuthor> has = LibraryDB.getManager().getHasAuthor();
			for (HasAuthor b: has){
				textArea.append("callNumber: " + b.getCallNumber()
						+ " || name: " + b.getName() + "\n");
				
			}
			break;
			
		case 7:
			ArrayList<HasSubject> hss = LibraryDB.getManager().getHasSubject();
			for (HasSubject b: hss){
				textArea.append("callNumber: " + b.getCallNumber()
						+ " || subject: " + b.getSubject() + "\n");
			}
			break;
			
		case 8:
			ArrayList<HoldRequest> hrs = LibraryDB.getManager().getHoldRequest();
			for (HoldRequest b: hrs){
				textArea.append("hid: " + b.getHoldId()
						+ " || bid: " + b.getBorrowerId()
						+ " || callNumber: " + b.getCallNumber()
						+ " || issueDate: " + b.getIssueDate() + "\n");
				
			}
			break;
			
		default:
			break;
		}

	}

}
