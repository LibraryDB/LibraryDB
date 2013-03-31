
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
import transactions.CheckAccount;
import transactions.HoldRequest;
import transactions.PayFine;


public class BorrowerPanel extends JPanel {

	BorrowerFrame bf;
	
	public BorrowerPanel(BorrowerFrame bf){

		super(new GridLayout(6,1)); 
		this.bf = bf;
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
				new SearchBook();
				System.out.println("Search Book");

			}
		});
		
		// Check Account button
		JButton checkOutButton = new JButton("Check Account");		
		checkOutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				new CheckAccount();
				System.out.println("Check Account");

			}
		});
		
		// Place a hold request
		JButton processReturnButton = new JButton("Place a hold request");		
		processReturnButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				new HoldRequest();
				System.out.println("Place a hold request");

			}
		});
		
		// Pay Fine
		JButton checkOverdueButton = new JButton("Pay Fine");		
		checkOverdueButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				new PayFine();
				System.out.println("Pay Fine");

			}
		});
		
		JButton backButton = new JButton("Back");		
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				new UserFrame();
				bf.dispose();
			}
		});
		
		add(label);
		add(addBorrowerButton);	
		add(checkOutButton);
		add(processReturnButton);
		add(checkOverdueButton);
		add(backButton);
		
	}


}