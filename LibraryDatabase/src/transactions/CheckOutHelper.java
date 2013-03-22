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

//		JLabel label3 = new JLabel("outDate (yyyy/mm/dd): ");
//		label3.setBounds(0, HEIGHT*3/scale, WIDTH/3, HEIGHT/scale);
//		label3.setHorizontalAlignment(SwingConstants.RIGHT);
//		label3.setVerticalAlignment(SwingConstants.CENTER);
//		p.add(label3);
//
//		JTextField tf3 = new JTextField();
//		tf3.setBounds(WIDTH/2, HEIGHT*3/scale + disp , WIDTH/3 , HEIGHT/scale - 2*disp);
//		textFields.add(tf3);
//		p.add(tf3);

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

		String borid = textFields.get(0).getText().trim();
		String bid = textFields.get(1).getText().trim();
		String outDate;// = textFields.get(2).getText();
		int timeLimit = getTimeLimit(bid);
		
		Date date = new Date();
		Calendar currentCal = new GregorianCalendar(TimeZone.getTimeZone("PTC")) ;
		currentCal.setTime(date);
		outDate = calendarToString(currentCal);
		
		currentCal.add(Calendar.DATE, timeLimit);
		
		for (int i=0;i<callNumbers.size();i++){
			Borrowing b = new Borrowing(borid,bid,callNumbers.get(i),copyNos.get(i),outDate,null);
			try {
				LibraryDB.getManager().insertBorrowing(b);
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
	
	private String calendarToString(Calendar calendar) {
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		String result = year + "/"+ month + "/" + day;
		return result;
	}

	private int getTimeLimit(String bid){
		List<BorrowerType> types = LibraryDB.getManager().getBorrowerType();
		List<Borrower> borrowers = LibraryDB.getManager().getBorrower();
		String type = null;
		for (Borrower borrower : borrowers){
			if (borrower.getBid().equals(bid))
				type = borrower.getType();
		}
		
		for (BorrowerType bType : types){
			if (bType.getType().equals(type))
				return bType.getBookTimeLimit();		
		}
		return 0;
	}
	
	private void determineError(SQLException e){
		if (e.getMessage().contains("ORA-01400"))
			popMsg("Error! \nOne of the values are not given. \nPlease try again.");
		if (e.getMessage().contains("ORA-00001"))
			popMsg("Error! \nborid already exists! \nPlease try again.");
		if (e.getMessage().contains("ORA-02291"))
			popMsg("Error! \nbid does not exist! \nPlease try again.");
		System.out.println(e.getMessage());

	}
	private void popMsg(String msg){
		JOptionPane.showMessageDialog (this, msg);
	}

}
