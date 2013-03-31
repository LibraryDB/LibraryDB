
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
import transactions.CheckOutHelper;
import transactions.CheckOverdue;
import transactions.ProcessReturn;



public class ClerkPanel extends JPanel {

	ClerkFrame cf;
	public ClerkPanel(ClerkFrame cf){
		super(new GridLayout(6,1)); 
		this.cf = cf;
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
				new CheckOutHelper();

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
		
		JButton backButton = new JButton("Back");		
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				new UserFrame();
				cf.dispose();
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