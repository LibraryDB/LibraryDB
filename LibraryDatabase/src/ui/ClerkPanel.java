
package ui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import transactions.AddBorrower;
import transactions.CheckOut;
import transactions.CheckOverdue;
import transactions.ProcessReturn;



public class ClerkPanel extends JPanel {

	public ClerkPanel(){

		super(new GridLayout(5,1)); 
		init();

	}

	private void init(){

		// Label
		JLabel label = new JLabel("Hello Clerk!! =D");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		
		// Add borrower button
		JButton addBorrowerButton = new JButton("Add New Borrower");		
		addBorrowerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				new AddBorrower();

			}
		});
		
		// Check out item button
		JButton checkOutButton = new JButton("Check Out Item");		
		checkOutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				new CheckOut();

			}
		});
		
		// Process return
		JButton processReturnButton = new JButton("Process Return");		
		processReturnButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				new ProcessReturn();
				System.out.println("Process Return");

			}
		});
		
		// Check overdue
		JButton checkOverdueButton = new JButton("Check Overdue");		
		checkOverdueButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				new CheckOverdue();
			}
		});
		
		add(label);
		add(addBorrowerButton);	
		add(checkOutButton);
		add(processReturnButton);
		add(checkOverdueButton);
		
	}


}