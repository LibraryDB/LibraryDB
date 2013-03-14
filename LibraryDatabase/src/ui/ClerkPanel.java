
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



public class ClerkPanel extends JPanel {

	private int WIDTH = 800;
	private int HEIGHT = 700;

	public ClerkPanel(){

		super();
		
		// Allows us to use absolute layout. i.e. setBounds(...) will have an effect, 
		// You could also use other defined layouts e.g. gridLayout().
		this.setLayout(null); 
		
		
		JLabel label = new JLabel("Welcome!");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(WIDTH/2 - 100, 0 , 200 , 100);
		
		JButton addBorrowerButton = new JButton("Add New Borrower");
		addBorrowerButton.setBounds(WIDTH/8, HEIGHT/2, 200, 70);
		
		addBorrowerButton.addActionListener(new ActionListener() {
			
		
			public void actionPerformed(ActionEvent e)
			{
				//Execute when button is pressed
				// TODO
				System.out.println("Add new Borrower");
				
			}
		});

		add(label);
		add(addBorrowerButton);	

	}
	
	

}