package ui;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class InsertYearAndNumberFrame extends JFrame{
	private int WIDTH = 200;
	private int HEIGHT = 200;
	
	public InsertYearAndNumberFrame(){
		super("insertYearAndNumber");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		InsertYearAndNumberPanel lp = new InsertYearAndNumberPanel();
		add(lp);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int x = screenSize.width/2;
		int y = screenSize.height/2;
		this.setLocation(x - WIDTH/2,y - HEIGHT/2);
		setSize(WIDTH,HEIGHT);
		setVisible(true);
	}

}
