package transactions;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
				// TODO
				Borrower b = new Borrower(
						textFields.get(0).getText(),
						textFields.get(1).getText(),
						textFields.get(2).getText(),
						textFields.get(3).getText(),
						textFields.get(4).getText(),
						textFields.get(5).getText(),
						textFields.get(6).getText(),
						textFields.get(7).getText(),
						textFields.get(8).getText());
				LibraryDB.getManager().insertBorrower(b);
				exitWindow();
				
				System.out.println("Confirmed");
				

			}
		});
		p.add(confirmButton);
		this.add(p);
	}

	private void exitWindow(){
		this.dispose();
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
