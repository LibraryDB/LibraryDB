package transactions;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.sql.*;

public class SearchBook extends JFrame implements ActionListener {

	private JPanel pBook = new JPanel ();
	private JLabel lbSearch;
	private JRadioButton rb1,rb2,rb3;
	private JTextField txtSearch;
	private JButton btnFind, btnCancel;
	private int flag=0;
	private Statement st;
	private String bname,bauthor,bsubject,search;
	private int bref,bmid,bid,rows=0;
	private JTable table;
	private JScrollPane jsp;
	private Object data1[][];
	private Container c;
	private int WIDTH = 200;
	private int HEIGHT = 200;
	
	public SearchBook () {

		
		super ("Search Books");
		

		lbSearch = new JLabel ("Search Field");
		lbSearch.setForeground (Color.black);
		lbSearch.setBounds (15, 15, 100, 20);
		
		txtSearch = new JTextField ();
		txtSearch.setBounds (120, 15, 175, 25);
		
		
		btnFind = new JButton ("Find Book");
		btnFind.setBounds (25, 175, 125, 25);
		btnFind.addActionListener (this);
		btnCancel = new JButton ("Cancel");
		btnCancel.setBounds (165, 175, 125, 25);
		btnCancel.addActionListener (this);
		
		rb1=new JRadioButton("By Title");
		rb1.addActionListener(this);
		rb1.setBounds (15, 45, 100, 20);
		rb2=new JRadioButton("By Author");
		rb2.addActionListener(this);
		rb2.setBounds (15, 75, 100, 20);
		rb3=new JRadioButton("By Subject");
		rb3.addActionListener(this);
		rb3.setBounds (15, 105, 100, 20);
		
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

		/*try {
		//Connection con=null;
			//	Class.forName ("sun.jdbc.odbc.JdbcOdbcDriver");
			//String loc = "jdbc:odbc:Library";
			//con = DriverManager.getConnection (loc);
		}
		catch (SQLException sqlex) {			//If Problem then Show the User a Message.
 			JOptionPane.showMessageDialog (null, "A Problem Occurs While Loading Form.");
 			dispose ();				//Closing the Form.
	 	}*/

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int x = screenSize.width/2;
		int y = screenSize.height/2;
		this.setLocation(x - WIDTH/2,y - HEIGHT/2);
		setSize(WIDTH,HEIGHT);
		setVisible(true);
		

	}
	
	public void actionPerformed (ActionEvent ae) {

		Object obj = ae.getSource();

		if (obj == btnFind) {		//If Find Button Pressed.

			if (txtSearch.getText().equals ("")) {
				JOptionPane.showMessageDialog (this, "Search Field not Provided.");
				txtSearch.requestFocus ();
			}
			else 
			{
				
				String bname1,bauthor1,bcat1;
				int num;
				boolean found = false;				//To Confirm the Book's Id Existance.
				Connection con=null;
				String url="jdbc:odbc:NitLibrary";	// connect to our db
				Statement st=null;
				try {	//SELECT Query to Retrieved the Record.
					Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
					con=DriverManager.getConnection(url);
					st = con.createStatement();
				
					String q,bavl,bisr;
					num=st.executeUpdate("Delete * from bksearch");
					ResultSet rs = st.executeQuery ("SELECT * FROM book ");	//Executing the Query.
					
					
					search=txtSearch.getText();
					search=search.toLowerCase();
					search=search.trim();

					
					while(rs.next())
					{
						
						bname=rs.getString(2);
						bauthor=rs.getString("bauther");
						bsubject=rs.getString("bsubject");
						
						bmid=rs.getInt("Statuss");
						if(bmid==0) bavl="Available";
						else bavl="Issued:"+ bmid;
						bid=rs.getInt("bookid");
						
						if(flag==0)
						{
							bname1=bname.toLowerCase();
							if(bname1.equals(search)||(bname1.indexOf(search)!=-1))
							{
								System.out.println("Came Here2");
							    num=st.executeUpdate("insert into bksearch values("+bid+", '"+bname+"','"+bsubject+"','"+bauthor+"' , '"+bavl+"')");
								rows++;
							    found=true;
								System.out.println("bookname");
							}
						}
						else if(flag==1)
						{
							bauthor1=bauthor.toLowerCase();
							if(bauthor1.equals(search)||(bauthor1.indexOf(search)!=-1))
							{
								num=st.executeUpdate("insert into bksearch values("+bid+", '"+bname+"' , '"+bsubject+"' , '"+bauthor+"' , '"+bavl+"')");
								rows++;
								found=true;
								System.out.println("auther");
							}
						}
						else if(flag==2)
						{
							bcat1=bsubject.toLowerCase();
							if(bcat1.equals(search)||(bcat1.indexOf(search)!=-1))
							{
								num=st.executeUpdate("insert into bksearch values("+bid+", '"+bname+"' , '"+bsubject+"' , '"+bauthor+"' , '"+bavl+"')");
								rows++;
								found=true;
								System.out.println("catagory");
							}
						}
						else if(flag==3)
						{
							if(bid==Integer.parseInt(txtSearch.getText()))
							{
								rows++;
								num=st.executeUpdate("insert into bksearch values("+bid+", '"+bname+"' , '"+bsubject+"' , '"+bauthor+"' , '"+bavl+"')");
								found=true;
								System.out.println("id");
							}
						}
						
					}
				
					
					
				}
				catch(SQLException ex)
			   {
			   if (found == false) {
						JOptionPane.showMessageDialog (this, "Record not Found.");
					}
			   }
			  catch(ClassNotFoundException ex)
			   {
			    System.out.println("Class not found");
			   }
			  catch(Exception ex)
			  {
               System.out.println("Exception raised is:"+ex);
			  }
				if(found==true){
				
				try{
				data1=new Object[rows][5];
				
				Object[] Colheads={"Book Id","Book Name","Category","Author","Availability"};
				ResultSet rs=st.executeQuery("Select * from bksearch");
			
				for(int i1=0;i1<rows;i1++)
				{
						rs.next();
						for(int j1=0;j1<5;j1++)
						{
							data1[i1][j1]=rs.getString(j1+1);
						}
				}
				rs.close();
						st.close();
						con.close();
				table=new JTable(data1,Colheads);
				int v=ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
				int h=ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
				System.out.println("hai we came here");
				jsp=new JScrollPane(table,v,h);
				//replace with our table
				//TableDisp td=new TableDisp(table);
			
				
				}
				catch(Exception sqlex) {
					if (found == false) {
						JOptionPane.showMessageDialog (this, "Some prob Found.");
					}
				}
			}
			}

		}		

		if (obj == btnCancel) {		//If Cancel Button Pressed Unload the From.

			setVisible (false);
			dispose();

		}
		
		if(obj==rb1)
		{
			flag=0;
		}
		if(obj==rb2)
		{
			flag=1;
		}
		if(obj==rb3)
		{
			flag=2;
		}
	}
	
}
