package ui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import transactions.AddBook;
import transactions.ReportCheckedOut;
import transactions.ReportCheckedOutHelper;
import transactions.SearchBook;
//import transactions.addBook;
//import transactions.addBook;

public class LibrarianPanel extends JPanel{
	
	LibrarianFrame lf;
	
	public LibrarianPanel(LibrarianFrame lf){

		super(new GridLayout(5,1)); 
		this.lf = lf;
		init();

	}

	private void init(){

		// Label
		JLabel label = new JLabel("~Hello Librarian~");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		
		// Search Book button
		JButton addBookButton = new JButton("Add Book");		
		addBookButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				//Execute when button is pressed
				new AddBook();
				System.out.println("Add new Book");

			}
		});
		
		
		// Check Account button
		JButton reportPopularButton = new JButton("Report: Most Popular Items");		
		reportPopularButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				//Execute when button is pressed
				// TODO
				//new generatePopular();
				new InsertYearAndNumberFrame();
				System.out.println("Generating most popular items");

			}
		});
		
		// Place a hold request
		JButton reportCheckedOut = new JButton("Report: Checked out Items");		
		reportCheckedOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				//Execute when button is pressed
				// TODO
				//new ReportCheckedOut();
				new InsertSubjectFrame();
				System.out.println("Generating all checked out items");

			}
		});
		
		JButton backButton = new JButton("Back");		
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				new UserFrame();
				lf.dispose();
			}
		});
				
		add(label);
		add(addBookButton);	
		add(reportCheckedOut);
		add(reportPopularButton);
		add(backButton);
		
	}
	
	
}
