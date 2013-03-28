package transactions;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

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
	private int WIDTH = 400;
	private int HEIGHT = 500;
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
		checkedOutBooks = getCheckedOutItems();
		//selected = new ArrayList<Integer>();
		this.subject = subject;
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
	        	if (LibraryDB.getManager().checkSubject(b.getCallNumber(), subject)) {
	        		String msg = 
		        			"CallNumber " + b.getCallNumber() + 
		        			" Checked Out: " + b.getOutDate() + 
		        		        	" Due Date:" + b.getInDate();
		        	listModel.addElement(msg);
	        	}
	        	
	        }
//	        
//	        DefaultListModel allCallNumber = new DefaultListModel();
//	        ArrayList<String> allCallNumberAsString = new ArrayList<String>();
//	        for (int i = 0; i < checkedOutBooks.size(); i++) {
//	        	Borrowing b = checkedOutBooks.get(i);
//	        	String callNumber = b.getCallNumber();
//	        	allCallNumber.addElement(callNumber);
//	        	allCallNumberAsString.add(callNumber);
//	        }
//	        Collections.sort(allCallNumberAsString);
//	        for (int i = 0; i < allCallNumberAsString.size(); i++) {
//	        	for (int j = 0; j < checkedOutBooks.size(); j++) {
//	        		if (allCallNumberAsString.get(i) == checkedOutBooks.get(j).getCallNumber()) {
//	        			Borrowing b = checkedOutBooks.get(j);
//	        			String msg = 
//	                			"CallNumber " + b.getCallNumber() + 
//	                			" Checked Out: " + b.getOutDate() + 
//	                		        	" Due Date:" + b.getInDate();
//	                	listModel.addElement(msg);
//	        		}
//	        	}
//	        }
	        
		}
		
		private void initPanel(){
			int disp = 20;
			
			JPanel p = new JPanel();
			p.setLayout(null);
	       

	        // *********************** LIST **************************
			initList();
	        list = new JList(listModel);
	        list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	        list.setSelectedIndex(0);
	 
	        
	        JScrollPane listScrollPane = new JScrollPane(list);
	        listScrollPane.setBounds(WIDTH/9 - disp, HEIGHT/8 - disp, WIDTH , HEIGHT/2);
	        p.add(listScrollPane);
	        
			this.add(p);
			
		}	
		
		// Returns list of overdue Borrowings
		private ArrayList<Borrowing> getCheckedOutItems(){
			ArrayList<Borrowing> result = new ArrayList<Borrowing>();
			result = LibraryDB.getManager().getOutBorrowings();
			return result;
		}
		




}
