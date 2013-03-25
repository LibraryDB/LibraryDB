
package ui;

import java.awt.Component;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import transactions.SearchBook;



public class BorrowerPanel extends JPanel {

	public BorrowerPanel(){

		super(new GridLayout(5,1)); 
		init();

	}

	private void init(){

		// Label
		JLabel label = new JLabel("~Hello Borrower~");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		
		// Search Book button
		JButton addBorrowerButton = new JButton("Search Book");		
		addBorrowerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				//Execute when button is pressed
				// TODO
				new SearchBook();
				System.out.println("Add new Borrower");

			}
		});
		
		// Check Account button
		JButton checkOutButton = new JButton("Check Account");		
		checkOutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				//Execute when button is pressed
				// TODO
				System.out.println("Check Out Item");

			}
		});
		
		// Place a hold request
		JButton processReturnButton = new JButton("Place a hold request");		
		processReturnButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				//Execute when button is pressed
				// TODO
				System.out.println("Place a hold request");

			}
		});
		
		// Pay Fine
		JButton checkOverdueButton = new JButton("Pay Fine");		
		checkOverdueButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				//Execute when button is pressed
				// TODO
				System.out.println("Pay Fine");

			}
		});
		
		add(label);
		add(addBorrowerButton);	
		add(checkOutButton);
		add(processReturnButton);
		add(checkOverdueButton);
		
	}


}