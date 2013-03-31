package ui;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class LibrarianFrame extends JFrame{
	
	private int WIDTH = 200;
	private int HEIGHT = 200;
	
	public LibrarianFrame(){
		super("Borrower");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		LibrarianPanel lp = new LibrarianPanel(this);
		add(lp);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int x = screenSize.width/2;
		int y = screenSize.height/2;
		this.setLocation(x - WIDTH/2,y - HEIGHT/2);
		setSize(WIDTH,HEIGHT);
		setVisible(true);
	}

}
