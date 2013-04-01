package ui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import transactions.AddBook;
import transactions.ReportCheckedOut;
import transactions.ReportCheckedOutHelper;

public class InsertSubjectPanel extends JPanel{
	private List<JTextField> textFields = new ArrayList<JTextField>();	
	public InsertSubjectPanel(){

		super(new GridLayout(5,1)); 
		init();

	}

	private void init(){

		// Label
		JLabel label = new JLabel("Insert Subject");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		
		// Search Book button
		JButton addBookButton = new JButton("Submit");		
		addBookButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				//Execute when button is pressed
				String subjectText = textFields.get(0).getText().trim();
				
				System.out.println(subjectText + subjectText.length());
				if (subjectText.length() != 0) {
					new ReportCheckedOutHelper(subjectText);
				}
				else
					new ReportCheckedOut();
				System.out.println("Possibly insert subject");

			}
		});
		
		JButton backButton = new JButton("Back");		
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				new UserFrame();
			}
		});
		JTextField tf = new JTextField();
		int tf_y = HEIGHT/11;
		int tf_h = HEIGHT/15;
		tf.setBounds(WIDTH/2, tf_y + (HEIGHT/11 - tf_h)/2 , WIDTH/3 , tf_h);
		textFields.add(tf);
		
			
		add(label);
		add(tf);
		add(addBookButton);	
		add(backButton);
	}
}
