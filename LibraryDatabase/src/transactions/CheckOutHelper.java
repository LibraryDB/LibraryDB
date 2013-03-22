package transactions;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import oracle.sql.DATE;

import model.Borrower;
import model.BorrowerType;
import model.Borrowing;

import ui.LibraryDB;

public class CheckOutHelper extends JFrame{

	// dimensions of the window
	private int WIDTH = 400;
	private int HEIGHT = 500;
	private List<JTextField> textFields = new ArrayList<JTextField>();	
	private List<Integer> copyNos = new ArrayList<Integer>();
	private List<String> callNumbers = new ArrayList<String>();

	public CheckOutHelper(List<String> callNumbers,List<Integer> copyNos){
		super("Check Out");
		this.copyNos = copyNos;
		this.callNumbers = callNumbers;

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

	private void initPanel(){
		int scale = 10;
		int disp = 10;
		JPanel p = new JPanel();
		p.setLayout(null);
		JLabel label0 = new JLabel("Please enter Borrower info");
		label0.setBounds(WIDTH/4, 0, WIDTH/2, HEIGHT/scale);
		label0.setHorizontalAlignment(SwingConstants.CENTER);
		label0.setVerticalAlignment(SwingConstants.CENTER);
		p.add(label0);

		JLabel label1 = new JLabel("borid: ");
		label1.setBounds(0, HEIGHT/scale, WIDTH/3, HEIGHT/scale);
		label1.setHorizontalAlignment(SwingConstants.RIGHT);
		label1.setVerticalAlignment(SwingConstants.CENTER);
		p.add(label1);

		JTextField tf1 = new JTextField();
		tf1.setBounds(WIDTH/2, HEIGHT/scale + disp , WIDTH/3 , HEIGHT/scale - 2*disp);
		textFields.add(tf1);
		p.add(tf1);

		JLabel label2 = new JLabel("bid: ");
		label2.setBounds(0, HEIGHT*2/scale, WIDTH/3, HEIGHT/scale);
		label2.setHorizontalAlignment(SwingConstants.RIGHT);
		label2.setVerticalAlignment(SwingConstants.CENTER);
		p.add(label2);

		JTextField tf2 = new JTextField();
		tf2.setBounds(WIDTH/2, HEIGHT*2/scale + disp , WIDTH/3 , HEIGHT/scale - 2*disp);
		textFields.add(tf2);
		p.add(tf2);

		JButton submitButton = new JButton("Submit");
		submitButton.setBounds(WIDTH/4, HEIGHT*4/scale, WIDTH/2, HEIGHT/scale);
		submitButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				onClick();

			}

		});
		p.add(submitButton);
		this.add(p);
	}

	private void onClick(){

		// fetches the text in the text boxes.
		String borid = textFields.get(0).getText().trim();
		String bid = textFields.get(1).getText().trim();
		
		String outDate;
		int timeLimit = 0;
		try {
			timeLimit = LibraryDB.getManager().getTimeLimit(bid);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		// Find the current date
		Date date = new Date();
		Calendar currentCal = new GregorianCalendar(TimeZone.getTimeZone("PST")) ;
		currentCal.setTime(date);
		outDate = calendarToString(currentCal);
		
		// currentCal will now store the due date
		currentCal.add(Calendar.DATE, timeLimit);
		
		for (int i=0;i<callNumbers.size();i++){
			Borrowing b = new Borrowing(borid.concat("" + i),bid,callNumbers.get(i),copyNos.get(i),outDate,null);
			try {
				// create new tuple in borrowing table
				LibraryDB.getManager().insertBorrowing(b);
				
				// set the status of the checked out books to "out"
				LibraryDB.getManager().updateBookCopy(b.getCallNumber(), b.getCopyNo(), "out");
			} catch (SQLException e1) { 
				determineError(e1);
				return;
			}
		}		
		
		String message = "These books are due on " + calendarToString(currentCal) + " :\n";
		for (int i=0;i<callNumbers.size();i++){
			message += callNumbers.get(i) + copyNos.get(i) + "\n";
		}
		
		popMsg(message);
		this.dispose();
	}
	
	// Converts Calendar to String in the format of "yyyy/mm/dd"
	private String calendarToString(Calendar calendar) {
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		String result = year + "/"+ month + "/" + day;
		return result;
	}

	
	private void determineError(SQLException e){
		
		if (e.getMessage().contains("ORA-01400")) // Null value error when attribute should be non-null
			popMsg("Error! \nOne of the values are not given. \nPlease try again.");
		
		if (e.getMessage().contains("ORA-00001")) // Primary Key Constraint
			popMsg("Error! \nborid already exists! \nPlease try again.");
		
		if (e.getMessage().contains("ORA-02291")) // Foreign Key Constraint
			popMsg("Error! \nbid does not exist! \nPlease try again.");
		
		else // otherwise, I would like to know what the error is!
			System.out.println(e.getMessage());
	}
	
	private void popMsg(String msg){ 
		JOptionPane.showMessageDialog (this, msg);
	}

}
