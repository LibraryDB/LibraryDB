
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
import transactions.PlaceHoldRequest;


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
		JButton searchBookrButton = new JButton("Search Book");		
		searchBookrButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				new SearchBook();
				System.out.println("Search Book");

			}
		});
		
		// Check Account button
		JButton checkAccountButton = new JButton("Check Account");		
		checkAccountButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				new CheckAccount();
				System.out.println("Check Account");

			}
		});
		
		// Place a hold request
		JButton holdRequestButton = new JButton("Place a hold request");		
		holdRequestButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				new PlaceHoldRequest();
				System.out.println("Place a hold request");

			}
		});
		
//		// Pay Fine
//		JButton checkOverdueButton = new JButton("Pay Fine");		
//		checkOverdueButton.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e)
//			{
//				new PayFine();
//				System.out.println("Pay Fine");
//
//			}
//		});
		
		JButton backButton = new JButton("Back");		
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				new UserFrame();
				bf.dispose();
			}
		});
		
		add(label);
		add(searchBookrButton);	
		add(checkAccountButton);
		add(holdRequestButton);
//		add(checkOverdueButton);
		add(backButton);
		
	}


}