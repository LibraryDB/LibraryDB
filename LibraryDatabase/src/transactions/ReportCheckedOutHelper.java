package transactions;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;

import ui.LibraryDB;

import model.Borrowing;
import model.HasSubject;

public class ReportCheckedOutHelper extends JFrame{
	// dimensions of the window
	private int WIDTH = 500;
	private int HEIGHT = 300;
	private JList list;
	private DefaultListModel listModel;
	private List<Borrowing> checkedOutBooks;
	private List<Integer> selected;
	final JTextArea textArea = new JTextArea(5, 12);
	private List<HasSubject> allSubjects;
	private String subject;

	public ReportCheckedOutHelper(String subject) {
		super("Report of checked out items by subject");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //Exits the window when user clicks on "x"
		this.subject = subject;
		checkedOutBooks = getCheckedOutItems();
		//selected = new ArrayList<Integer>();
		
		initPanel();

		// Center the window
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int x = screenSize.width/2 - WIDTH/2;
		int y = screenSize.height/2 - HEIGHT/2;
		this.setLocation(x,y);

		setSize(WIDTH,HEIGHT);
		setVisible(true);
	}

	// Creates the list of borrowed books to be displayed in the textbox
	private void initList(){
		listModel = new DefaultListModel();
		for (int i=0;i<checkedOutBooks.size();i++){

			Borrowing b = checkedOutBooks.get(i);
			Boolean due;
			due = isNotOverDue(b);
			if (!due) {
				String msg = 
						"CallNumber " + b.getCallNumber() + 
						" Checked Out: " + b.getOutDate() + 
						" Due Date:" + b.getInDate() +
						" Note: OVERDUE";
				listModel.addElement(msg);
			}
			else {
				String msg = 
						"CallNumber " + b.getCallNumber() + 
						" Checked Out: " + b.getOutDate() + 
						" Due Date:" + b.getInDate();
				listModel.addElement(msg);
			}

		}

	}
	private Calendar stringToCalendar(String str){
		String parts[] = str.split("/");
		int year = Integer.parseInt(parts[0]);
		int month = Integer.parseInt(parts[1]) - 1;
		int date = Integer.parseInt(parts[2]);
		Calendar cal = Calendar.getInstance();
		cal.set(year, month, date);
		return cal;

	}

	private void initPanel(){
		int disp = 20;

		JPanel p = new JPanel();
		p.setLayout(null);//


		// *********************** LIST **************************
		initList();
		list = new JList(listModel);
		list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		list.setSelectedIndex(0);


		JScrollPane listScrollPane = new JScrollPane(list);
		listScrollPane.setBounds(0, 0, WIDTH , HEIGHT);
		p.add(listScrollPane);

		this.add(p);

	}	

	// Returns list of overdue Borrowings
	private ArrayList<Borrowing> getCheckedOutItems(){
		ArrayList<Borrowing> result = new ArrayList<Borrowing>();
		result = LibraryDB.getManager().getBorrowingAllBySubject(subject);
		return result;
	}
	
	private boolean isNotOverDue(Borrowing b) { // checks if book is not overdue... dun feel like refactoring
		int timeLimit = 0;
		Calendar currentCal = new GregorianCalendar(TimeZone.getTimeZone("PST"));
		
		try {
			timeLimit = LibraryDB.getManager().getTimeLimit(b.getBid());
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return false;
		}
		
		Calendar dueCal = stringToCalendar(b.getOutDate());// get current time
		dueCal.add(Calendar.DATE, timeLimit); 				// dueCal is now current time + timeLimit = due date
		if (dueCal.after(currentCal)) return true;	
		System.out.println(b.getBid());
		return false;
	}
	
	






}
