package transactions;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import model.Borrower;
import ui.LibraryDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CheckAccount extends JFrame implements ActionListener{
	// dimensions of the window
	private JPanel pBook = new JPanel ();
	private JLabel lbSearch;
	private JButton btnSubmit, btnCancel;
	private JTextField txtBorrowerID;
	private Statement st;
	private JTable table;
	private JScrollPane jsp;
	private Object data1[][];
	private Container c;
	private int WIDTH = 400;
	private int HEIGHT = 300;
	

	public CheckAccount(){
		super("Check My Account");
		lbSearch = new JLabel ("Borrower ID");
		lbSearch.setForeground (Color.black);
		lbSearch.setBounds (15, 15, 100, 20);
			
		txtBorrowerID = new JTextField ();
		txtBorrowerID.setBounds (120, 15, 175, 25);
		
		btnSubmit = new JButton ("Submit");
		btnSubmit.setBounds (25, 175, 125, 25);
		btnSubmit.addActionListener (this);
		btnCancel = new JButton ("Cancel");
		btnCancel.setBounds (165, 175, 125, 25);
		btnCancel.addActionListener (this);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
