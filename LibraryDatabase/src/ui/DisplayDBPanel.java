package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import transactions.SearchBook;

public class DisplayDBPanel extends JPanel{


	public DisplayDBPanel(){

		super(new GridLayout(9,1));
		
		for (int i=0;i<9;i++){
			createButton(i);
		}

		
	}

	private void createButton(int i){
		final String name = indexToString(i);
		final int j = i;
		JButton button = new JButton(name);
		button.setVerticalTextPosition(AbstractButton.CENTER);
		button.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e)
			{
				new DisplayTable(j,name);
			}
		});
		add(button);
	}

	private String indexToString(int i){
		switch (i){
		case 0: return "Book";
		case 1: return "BookCopy";
		case 2: return "Borrower";
		case 3: return "BorrowerType";
		case 4: return "Borrowing";
		case 5: return "Fine";
		case 6: return "HasAuthor";
		case 7: return "HasSubject";
		case 8: return "HoldRequest";
		default: return null;
		}
	}


}
