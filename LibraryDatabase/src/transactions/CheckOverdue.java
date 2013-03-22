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

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ui.LibraryDB;

import model.BookCopy;
import model.Borrowing;

public class CheckOverdue extends JFrame{

	// dimensions of the window
	private int WIDTH = 400;
	private int HEIGHT = 500;
	private JList list;
	private DefaultListModel listModel;
	private JButton send2AllButton;
	private JButton send2SelectedButton;
	private List<Borrowing> overdues;
	
	public CheckOverdue(){
		super("Check Overdue");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //Exits the window when user clicks on "x"
		initPanel();

		// Center the window
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int x = screenSize.width/2 - WIDTH/2;
		int y = screenSize.height/2 - HEIGHT/2;
		this.setLocation(x,y);

		setSize(WIDTH,HEIGHT);
		setVisible(true);
	}
	
	private void initList(){
		overdues = getOverdueItems();
        listModel = new DefaultListModel();
        for (int i=0;i<overdues.size();i++){
        	Borrowing b = overdues.get(i);
        	String msg = 
        			"User " + b.getBid() + 
        			" borrowered " + b.getCallNumber() + 
        		        	" " + b.getCopyNo();
        	listModel.addElement(msg);
        }

        
	}
	
	private void initPanel(){
		JPanel p = new JPanel();
		p.setLayout(null);
		System.out.println(getOverdueItems().size());
		
		
		initList();
        list = new JList(listModel);
        list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(new ListSelectionListener(){

			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
        	
        });
        list.setVisibleRowCount(5);
        JScrollPane listScrollPane = new JScrollPane(list);
        listScrollPane.setBounds(WIDTH/8, HEIGHT/8, WIDTH*6/8 , HEIGHT/2);
        p.add(listScrollPane);
        
        send2AllButton = new JButton("Send Email To All");
        send2AllButton.setBounds(0, HEIGHT*6/8, WIDTH*3/7, HEIGHT/10);
        send2AllButton.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				onClickSendAll();
				
			}
        	
        });
        p.add(send2AllButton);
        
		
        send2SelectedButton = new JButton("Send Email To Selected");
        send2SelectedButton.setBounds(WIDTH/2, HEIGHT*6/8, WIDTH*3/7, HEIGHT/10);
        send2SelectedButton.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				onClickSendSelected();
				
			}
        	
        });
        p.add(send2SelectedButton);
        
		this.add(p);
		
	}
	
	protected void onClickSendSelected() {
		// TODO Auto-generated method stub
		
	}

	private void onClickSendAll() {
		// TODO Auto-generated method stub
		
	}
	
	// Returns list of overdue Borrowings
	private ArrayList<Borrowing> getOverdueItems(){
		ArrayList<Borrowing> result = new ArrayList<Borrowing>();
		result = LibraryDB.getManager().getOutBorrowings();
		ArrayList<Borrowing> toRemove = new ArrayList<Borrowing>();
		for (Borrowing b : result){
			if (isOverDue(b)) toRemove.add(b);
		}
		result.removeAll(toRemove);
		return result;
	}

	private boolean isOverDue(Borrowing b) {
		int timeLimit = 0;
		Calendar currentCal = new GregorianCalendar(TimeZone.getTimeZone("PST"));
		
		try {
			timeLimit = LibraryDB.getManager().getTimeLimit(b.getBid());
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return false;
		}
		
		Calendar dueCal = stringToCalendar(b.getOutDate());
		dueCal.add(Calendar.DATE, timeLimit);
		if (dueCal.after(currentCal)) return true;	
		System.out.println(b.getBid());
		return false;
	}
	
	// converts "yyyy/mm/dd" to Calendar.
	private Calendar stringToCalendar(String str){
		String parts[] = str.split("/");
		int year = Integer.parseInt(parts[0]);
		int month = Integer.parseInt(parts[1]) - 1;
		int date = Integer.parseInt(parts[2]);
		Calendar cal = Calendar.getInstance();
		cal.set(year, month, date);
		return cal;
		
	}
}
