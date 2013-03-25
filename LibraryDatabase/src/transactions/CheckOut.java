package transactions;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import model.Borrower;
import model.Borrowing;


import ui.LibraryDB;
import ui.UserPanel;
//test
public class CheckOut extends JFrame{

	// dimensions of the window
	private int WIDTH = 400;
	private int HEIGHT = 500;
	private List<JTextField> textFields = new ArrayList<JTextField>();	
	private List<Integer> copyNos = new ArrayList<Integer>();
	private List<String> callNumbers = new ArrayList<String>();
	final JTextArea textArea = new JTextArea(5, 20);

	public CheckOut(){
		super("Add Borrower");
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

	// initializes the JPanel
	private void initPanel(){
		int scale = 10;
		int disp = 10;
		JPanel p = new JPanel();
		p.setLayout(null);
		JLabel label0 = new JLabel("Please enter book info");
		label0.setBounds(WIDTH/4, 0, WIDTH/2, HEIGHT/scale);
		label0.setHorizontalAlignment(SwingConstants.CENTER);
		label0.setVerticalAlignment(SwingConstants.CENTER);
		p.add(label0);
		
		JLabel label1 = new JLabel("Call Number: ");
		label1.setBounds(0, HEIGHT/scale, WIDTH/3, HEIGHT/scale);
		label1.setHorizontalAlignment(SwingConstants.RIGHT);
		label1.setVerticalAlignment(SwingConstants.CENTER);
		p.add(label1);
		
		// Call number textfield
		JTextField tf1 = new JTextField();
		tf1.setBounds(WIDTH/2, HEIGHT/scale + disp , WIDTH/3 , HEIGHT/scale - 2*disp);
		textFields.add(tf1);
		p.add(tf1);
		
		JLabel label2 = new JLabel("Copy No: ");
		label2.setBounds(0, HEIGHT*2/scale, WIDTH/3, HEIGHT/scale);
		label2.setHorizontalAlignment(SwingConstants.RIGHT);
		label2.setVerticalAlignment(SwingConstants.CENTER);
		p.add(label2);
		
		// Copy no textfield
		JTextField tf2 = new JTextField();
		tf2.setBounds(WIDTH/2, HEIGHT*2/scale + disp , WIDTH/3 , HEIGHT/scale - 2*disp);
		textFields.add(tf2);
		p.add(tf2);
		
        // The textArea for a list of books
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(WIDTH/6, HEIGHT*3/8 - disp, 2*WIDTH/3, HEIGHT*3/8);
        add(scrollPane, BorderLayout.CENTER);

        // Add Book Button
		JButton addBookButton = new JButton("Add Book");
		addBookButton.setBounds(WIDTH/8, 6*HEIGHT/8, WIDTH/4, HEIGHT/12);
		addBookButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				addBookOnClick();	
			}
		});
		p.add(addBookButton);
		
		// Next Button
		JButton continueButton = new JButton("Next");
		continueButton.setBounds(WIDTH*5/8, 6*HEIGHT/8, WIDTH/4, HEIGHT/12);
		continueButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				// Must check out at least one book
				if (callNumbers.size() == 0) {
					popMsg("Need to enter some books!");
					return;
				}
				new CheckOutHelper(callNumbers,copyNos);
				exitWindow();
			}
		});
		p.add(continueButton);
		
		this.add(p);
	}

	private void addBookOnClick(){
		
		String callNumber = textFields.get(0).getText().trim();
		int copyNo;
		if (!isNumeric(textFields.get(1).getText().trim())){
			popMsg("copyNo must be a number!");
			return;
		}
		copyNo = Integer.parseInt(textFields.get(1).getText().trim());
		
		// Check if the bookcopy exists.
		if(!LibraryDB.getManager().hasBookCopy(callNumber, copyNo)){
			popMsg("Book does not exist in library!");
			return;
		}
		
		// Check if the bookcopy was put onto the list already
		for (int i=0;i<copyNos.size();i++){
			if ((copyNos.get(i) == copyNo) && callNumbers.get(i).matches(callNumber)){
				popMsg("BookCopy already added to list");
				return;
			}			
		}
		
		// check if bookcopy is available for borrowing, i.e. status = in.
		if (!LibraryDB.getManager().isBookCopyStatus(callNumber,copyNo,"in")){
			popMsg("Sorry! This book is not available for borrowing.");
			return;
		}
		
		copyNos.add(copyNo);
		callNumbers.add(callNumber);
		textArea.append(callNumber + " " + copyNo + "\n");
		resetTextField();
		System.out.println(callNumbers.size());
	}
	
	// check if str is numeric
	private boolean isNumeric(String str)  
	{  
	  try  
	  {  
	    int i = Integer.parseInt(str);  
	  }  
	  catch(NumberFormatException nfe)  
	  {  
	    return false;  
	  }  
	  return true;  
	}
	
	// if we get an error , pop-up a msg box and reset text boxes. 
	private void resetTextField(){
		for (int i=0;i<2;i++)
			textFields.get(i).setText("");
	}

	private void exitWindow(){
		this.dispose();
	}

	private void popMsg(String msg){
		JOptionPane.showMessageDialog (this, msg);
	}

}
