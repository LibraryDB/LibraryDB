package transactions;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import model.BookCopy;

public class CheckOverdue extends JFrame{

	// dimensions of the window
	private int WIDTH = 400;
	private int HEIGHT = 500;
	
	public CheckOverdue(){
		super("Check Overdue");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //Exits the window when user clicks on "x"
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
		JPanel p = new JPanel();
		p.setLayout(null);
		
	}
	
	private ArrayList<BookCopy> getOverdueItems(){
		ArrayList<BookCopy> overdues = new ArrayList<BookCopy>();
		
		
		
		return overdues;
	}
}
