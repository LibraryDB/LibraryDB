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
import model.BorrowingFrequency;

@SuppressWarnings({"serial", "rawtypes", "unchecked"})
public class ReportMostPopular extends JFrame{
	// dimensions of the window
	private int WIDTH = 300;
	private int HEIGHT = 300;
	private JList list;
	private DefaultListModel listModel;
	private List<Borrowing> checkedOutBooksInYear;
	final JTextArea textArea = new JTextArea(5, 12);
	private Integer year;
	private Integer number;
	private ArrayList<BorrowingFrequency> bf = new ArrayList<BorrowingFrequency>();

	public ReportMostPopular(Integer year, Integer number) {
		super("Report of most popular items");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //Exits the window when user clicks on "x"
		this.year = year;
		this.number = number;
		checkedOutBooksInYear = getCheckedOutItemsInYear();
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
		result = LibraryDB.getManager().getBorrowingByYear(this.year);
		return result;
	}
	private Integer countUniqueItems(String callNumber) { 
		int count = 0; 
		for (int i = 0; i < checkedOutBooksInYear.size(); i++) { 
			Borrowing b = checkedOutBooksInYear.get(i); 
			if (b.getCallNumber().equals(callNumber)) { 
				count = count + 1; 
			} 
		} 
		return count; 
	}


	private void initPanel(){
		//int disp = 20;

		JPanel p = new JPanel();
		p.setLayout(null);


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


	private void initList(){
		listModel = new DefaultListModel();

		for (int i=0;i<checkedOutBooksInYear.size();i++){
			Borrowing b = checkedOutBooksInYear.get(i);
			BorrowingFrequency bfr = new BorrowingFrequency(b.getCallNumber(), countUniqueItems(b.getCallNumber()));
			bf.add(bfr);


		}



		int count = 0;
		for (int i = 0; i < checkedOutBooksInYear.size(); i++) {
			int index;

			index = topN();
			if (index != -1) {
				BorrowingFrequency b = bf.get(index);
				String msg = "CallNumber " + b.getCallNumber() + " Borrowed " + b.getFrequency() + " times this year";
				if (!listModel.contains(msg) && count < number) {
					listModel.addElement(msg);
					count = count + 1;
				}
				bf.remove(index);
			}
		}



	}

	private int topN() {
		int max = 0;
		int index = -1; // to ensure if n is empty then change
		for (int i = 0; i < bf.size(); i++) {
			BorrowingFrequency b = bf.get(i);
			int bfCurrent = b.getFrequency();
			if (bfCurrent > max ) {
				max = bfCurrent;
				index = i;
			}

		}
		return index;
	}

}
