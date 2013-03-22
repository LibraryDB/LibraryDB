package transactions;

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
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import model.Borrower;


import ui.LibraryDB;
import ui.UserPanel;
//test
public class AddBorrower extends JFrame{

	// dimensions of the window
	private int WIDTH = 300;
	private int HEIGHT = 400;
	private List<JTextField> textFields = new ArrayList<JTextField>();	

	public AddBorrower(){
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

	private void createLabel(JPanel p,int i){
		JLabel label = new JLabel(indexToMsg(i) + ": ");
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		label.setVerticalAlignment(SwingConstants.CENTER);
		label.setBounds(0, i*HEIGHT/11, 3*WIDTH/7, HEIGHT/11);
		p.add(label);
	}

	private void createTextField(JPanel p, int i){
		JTextField tf = new JTextField();
		int tf_y = i*HEIGHT/11;
		int tf_h = HEIGHT/15;
		tf.setBounds(WIDTH/2, tf_y + (HEIGHT/11 - tf_h)/2 , WIDTH/3 , tf_h);
		textFields.add(tf);
		p.add(tf);
	}

	private void initPanel(){
		JPanel p = new JPanel();
		p.setLayout(null);
		for (int i=0;i<9;i++){
			createLabel(p,i);
			createTextField(p,i);
		}

		// Add confirm button
		JButton confirmButton = new JButton("Confirm");
		confirmButton.setBounds(WIDTH/4, 9*HEIGHT/11, WIDTH/2, HEIGHT/13);
		confirmButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				//Execute when button is pressed
				Borrower b = new Borrower(
						textFields.get(0).getText().trim(),
						textFields.get(1).getText().trim(),
						textFields.get(2).getText().trim(),
						textFields.get(3).getText().trim(),
						textFields.get(4).getText().trim(),
						textFields.get(5).getText().trim(),
						textFields.get(6).getText().trim(),
						textFields.get(7).getText().trim(),
						textFields.get(8).getText().trim());

				try {
					LibraryDB.getManager().insertBorrower(b);
					exitWindow();
					System.out.println("Submit");
				} catch (SQLException e1) { 
					determineError(e1);
				}

			}
		});
		p.add(confirmButton);
		this.add(p);
	}

	// if we get an error , pop-up a msg box and reset text boxes. 
	private void determineError(SQLException e){
		if (e.getMessage().contains("ORA-01400"))
			popMsg("Error! \nOne of the values are not given. \nPlease try again.");
		if (e.getMessage().contains("ORA-00001"))
			popMsg("Error! \nbid already exists! \nPlease try again.");
		if (e.getMessage().contains("ORA-02291"))
			popMsg("Error! \ntype must be one of: \nstudent , faculty , or staff");

	}

	private void exitWindow(){
		this.dispose();
	}

	private void popMsg(String msg){
		JOptionPane.showMessageDialog (this, msg);
	}

	private String indexToMsg(int i) {
		switch (i){
		case 0:	return "bid"; 
		case 1:	return "password";
		case 2: return "name";
		case 3: return "address";
		case 4: return "phone";
		case 5: return "email address";
		case 6: return "sin or student #";
		case 7: return "expiry date";
		case 8: return "type";
		}
		return "";

	}
}
