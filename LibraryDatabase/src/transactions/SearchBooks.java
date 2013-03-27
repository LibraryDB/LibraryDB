package transactions;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class SearchBooks extends JFrame implements ActionListener {
	private JPanel pBook = new JPanel ();
	private JLabel lbSearch;
	private JRadioButton rb1,rb2,rb3;
	private JTextField txtSearch;
	private JButton btnFind, btnCancel;
	private Statement st;
	private String bname,bauthor,bsubject,search;
	private int bref,bmid,bid,rows=0;
	private JTable table;
	private JScrollPane jsp;
	private Object data1[][];
	private Container c;
	private int WIDTH = 400;
	private int HEIGHT = 300;
	
	public SearchBooks() {
		super ("Search Books");
		

		lbSearch = new JLabel ("Search Field");
		lbSearch.setForeground (Color.black);
		lbSearch.setBounds (15, 15, 100, 20);
		
		txtSearch = new JTextField ();
		txtSearch.setBounds (120, 15, 175, 25);
		
		rb1=new JRadioButton("By Title");
		rb1.addActionListener(this);
		rb1.setBounds (15, 45, 100, 20);
		rb2=new JRadioButton("By Author");
		rb2.addActionListener(this);
		rb2.setBounds (15, 75, 100, 20);
		rb3=new JRadioButton("By Subject");
		rb3.addActionListener(this);
		rb3.setBounds (15, 105, 100, 20);
		
		btnFind = new JButton ("Find Book");
		btnFind.setBounds (25, 175, 125, 25);
		btnFind.addActionListener (this);
		btnCancel = new JButton ("Cancel");
		btnCancel.setBounds (165, 175, 125, 25);
		btnCancel.addActionListener (this);
		
		pBook.setLayout (null);
		pBook.add(lbSearch);
		pBook.add(txtSearch);
		pBook.add(btnFind);
		pBook.add(btnCancel);
		ButtonGroup bg=new ButtonGroup();
		bg.add(rb1);
		bg.add(rb2);
		bg.add(rb3);
		pBook.add(rb1);
		pBook.add(rb2);
		pBook.add(rb3);
		rb1.setSelected(true);
		getContentPane().add (pBook, BorderLayout.CENTER);
		c=getContentPane();

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int x = screenSize.width/2;
		int y = screenSize.height/2;
		this.setLocation(x - WIDTH/2,y - HEIGHT/2);
		setSize(WIDTH,HEIGHT);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		Object obj = ae.getSource();
		
		//If Find Button Pressed.
		if (obj == btnFind) {		
			//if search field is not provided
			if (txtSearch.getText().equals ("")) {
				JOptionPane.showMessageDialog (this, "Search Field not Provided.");
				txtSearch.requestFocus ();
			}
			else{
				String name,author,cat;
				
			}
		}
	}

}
