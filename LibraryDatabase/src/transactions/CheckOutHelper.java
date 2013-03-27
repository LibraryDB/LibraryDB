package transactions;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import oracle.sql.DATE;

import model.Borrower;
import model.BorrowerType;
import model.Borrowing;

import ui.LibraryDB;

public class CheckOutHelper extends JFrame{

	// dimensions of the window
	private int WIDTH = 400;
	private int HEIGHT = 500;
	private List<JTextField> textFields = new ArrayList<JTextField>();	


	public CheckOutHelper(){
		super("Check Out");

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

	private void initPanel(){
		int scale = 10;
		int disp = 10;
		JPanel p = new JPanel();
		p.setLayout(null);
		JLabel label0 = new JLabel("Please enter Borrower info");
		label0.setBounds(WIDTH/4, 0, WIDTH/2, HEIGHT/scale);
		label0.setHorizontalAlignment(SwingConstants.CENTER);
		label0.setVerticalAlignment(SwingConstants.CENTER);
		p.add(label0);


		JTextField tf1 = new JTextField();
		tf1.setBounds(WIDTH/2, HEIGHT/scale + disp , WIDTH/3 , HEIGHT/scale - 2*disp);
		textFields.add(tf1);
		tf1.setVisible(false); // too lazy to do more refactoring, looking for shortcuts :P
		p.add(tf1);

		JLabel label2 = new JLabel("bid: ");
		label2.setBounds(0, HEIGHT*2/scale, WIDTH/3, HEIGHT/scale);
		label2.setHorizontalAlignment(SwingConstants.RIGHT);
		label2.setVerticalAlignment(SwingConstants.CENTER);
		p.add(label2);

		JTextField tf2 = new JTextField();
		tf2.setBounds(WIDTH/2, HEIGHT*2/scale + disp , WIDTH/3 , HEIGHT/scale - 2*disp);
		textFields.add(tf2);
		p.add(tf2);

		JButton submitButton = new JButton("Next");
		submitButton.setBounds(WIDTH/4, HEIGHT*4/scale, WIDTH/2, HEIGHT/scale);
		submitButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				onClick();

			}

		});
		p.add(submitButton);
		this.add(p);
	}

	private void onClick(){

		// fetches the text in the text boxes.
		String bidString = textFields.get(1).getText().trim();
		
		if (!isNumeric(bidString)){
			popMsg("bid must be a number!");
			return;
		}
		
		int bid = Integer.parseInt(bidString);
		
		if (!LibraryDB.getManager().isValidBid(bid)) {
			popMsg("Not a valid BID! \nPlease try again.");
			return;
		}
		
		new CheckOut(bid);
		this.dispose();
	}
		
	
	private void popMsg(String msg){ 
		JOptionPane.showMessageDialog (this, msg);
	}

	private boolean isNumeric(String str)  
	{  
		try  
		{  
			Integer.parseInt(str);  
		}  
		catch(NumberFormatException nfe)  
		{  
			return false;  
		}  
		return true;  
	}
	
}
