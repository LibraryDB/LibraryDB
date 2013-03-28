package transactions;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;

import ui.LibraryDB;

import model.Book;
import model.Borrowing;
import model.HasSubject;

public class ReportMostPopular extends JFrame{
	// dimensions of the window
	private int WIDTH = 400;
	private int HEIGHT = 500;
	private JList list;
	private DefaultListModel listModel;
	private List<Borrowing> checkedOutBooksInYear;
	private List<Integer> selected;
	final JTextArea textArea = new JTextArea(5, 12);
	private List<HasSubject> allSubjects;
	private Integer year;
	private Integer number;
	private List<Book> uniqueBooks;
	private List<String> callNumberList = new ArrayList<String>();
	//List<Integer> callNumberFreqList = new ArrayList<Integer>();
	private Map<String, Integer> map = new HashMap<String, Integer>();
	
	public ReportMostPopular(Integer year, Integer number) {
		super("Report of most popular items");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //Exits the window when user clicks on "x"
		checkedOutBooksInYear = getCheckedOutItemsInYear();
		//selected = new ArrayList<Integer>();
		this.year = year;
		this.number = number;
		initPanel();

		// Center the window
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int x = screenSize.width/2 - WIDTH/2;
		int y = screenSize.height/2 - HEIGHT/2;
		this.setLocation(x,y);

		setSize(WIDTH,HEIGHT);
		setVisible(true);
	}
	
	private List<Borrowing> getCheckedOutItemsInYear() {
		ArrayList<Borrowing> result = new ArrayList<Borrowing>();
		result = LibraryDB.getManager().getBorrowingByYear(year);
		return result;
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
	
	private Integer countUniqueItems(String callNumber) {
		int count = 0;
		for (int i = 0; i < checkedOutBooksInYear.size(); i++) {
			Borrowing b = checkedOutBooksInYear.get(i);
			if (b.getCallNumber() == callNumber) {
				count = count + 1;
			}
		}
		return count;
	}
	
	private List<Book> createListByCount() {

			
		for (int i = 0; i < checkedOutBooksInYear.size(); i++) {
			Borrowing b = checkedOutBooksInYear.get(i);
			String currentCallNumber = b.getCallNumber();
			Integer currentCallNumberFrequency = countUniqueItems(currentCallNumber);
			
//			callNumberFreqList.add(currentCallNumberFrequency);
			if (currentCallNumberFrequency > number) {
				map.put(currentCallNumber, currentCallNumberFrequency);
				callNumberList.add(currentCallNumber);
			}
			
		}
		return null;
	}
	
	private String max() {
		Integer max = 0;
		Integer max_c = 0;
		String callNumber = null;
		String callNumber_c;
		for (int i = 0; i < callNumberList.size(); i++) {
			max_c = map.get(callNumberList.get(i));
			if (Math.max(max_c, max) == max_c) {
				callNumber = callNumberList.get(i);
			}
		}
		//map.remove(callNumber);
		return callNumber;
	}
	
	private void initList(){
		
		
		listModel = new DefaultListModel();
		
	        for (int i=0;i<checkedOutBooksInYear.size();i++){
	        	Borrowing b = checkedOutBooksInYear.get(i);
//	        	if (LibraryDB.getManager().checkSubject(b.getCallNumber(), subject)) {
//	        		String msg = 
//		        			"CallNumber " + b.getCallNumber() + 
//		        			" Checked Out: " + b.getOutDate() + 
//		        		        	" Due Date:" + b.getInDate();
//		        	listModel.addElement(msg);
//	        	}
	        	
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
}
