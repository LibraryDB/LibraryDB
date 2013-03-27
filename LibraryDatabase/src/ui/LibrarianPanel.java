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
import transactions.SearchBook;
//import transactions.addBook;
//import transactions.addBook;

public class LibrarianPanel extends JPanel{
	public LibrarianPanel(){

		super(new GridLayout(5,1)); 
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
		
		JButton addCopyButton = new JButton("Add Book Copy");
		addCopyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				//Execute when button is pressed
				// TODO
				//new addBook();
				System.out.println("Add new Copy of Existing Book");

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
				new ReportCheckedOut();
				System.out.println("Generating all checked out items");

			}
		});
				
		add(label);
		add(addCopyButton);
		add(addBookButton);	
		add(reportCheckedOut);
		add(reportPopularButton);
		
	}
}
