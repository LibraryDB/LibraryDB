package jdbc;
// We need to import the java.sql package to use JDBC
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

import model.*;

/*
 * This class implements a graphical login window and a simple text
 * interface for interacting with the branch table 
 */ 
public class JDBCManager 
{

	private Connection con;
	/*
	 * constructs login window and loads JDBC driver
	 */ 
	public JDBCManager()
	{
		try 
		{
			// Load the Oracle JDBC driver
			//lalala
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
		}
		catch (SQLException ex)
		{
			System.out.println("Message: " + ex.getMessage());
			System.exit(-1);
		}
	}


	/*
	 * connects to Oracle database named ug using user supplied username and password
	 */ 
	public boolean connect(String username, String password)
	{
		String connectURL = "jdbc:oracle:thin:@dbhost.ugrad.cs.ubc.ca:1522:ug"; 

		try 
		{
			con = DriverManager.getConnection(connectURL,username,password);

			System.out.println("\nConnected to Oracle!");
			return true;
		}
		catch (SQLException ex)
		{
			System.out.println("Message: " + ex.getMessage());
			return false;
		}
	}


	// **********************************************************
	// ************************ INSERTS *************************
	// **********************************************************
	public void insertBook(Book b){
		PreparedStatement  ps;

		try
		{
			ps = con.prepareStatement("INSERT INTO book VALUES (?,?,?,?,?,?)");
			ps.setString(1, b.getCallNumber());
			ps.setString(2, b.getIsbn());
			ps.setString(3, b.getTitle());
			ps.setString(4, b.getMainAuthor());
			ps.setString(5, b.getPublisher());
			ps.setString(6, b.getYear());
			ps.executeUpdate();

			// commit work 
			con.commit();

			ps.close();
		}
		catch (SQLException ex)
		{
			System.out.println("Message: " + ex.getMessage());
			try 
			{
				// undo the insert
				con.rollback();	
			}
			catch (SQLException ex2)
			{
				System.out.println("Message: " + ex2.getMessage());
				System.exit(-1);
			}
		}
	}

	public void insertBookCopy(BookCopy bc){
		PreparedStatement ps;
		try
		{
			ps = con.prepareStatement("INSERT INTO bookcopy VALUES (?,?,?)");

			ps.setString(1, bc.getCallNumber());
			ps.setInt(2, bc.getCopyNo());
			ps.setString(3, bc.getStatus());
			ps.executeUpdate();

			// commit work 
			con.commit();

			ps.close();
		}
		catch (SQLException ex)
		{
			System.out.println("Message: " + ex.getMessage());
			try 
			{
				// undo the insert
				con.rollback();	
			}
			catch (SQLException ex2)
			{
				System.out.println("Message: " + ex2.getMessage());
				System.exit(-1);
			}
		}
	}

	public void insertBorrower(Borrower b) throws SQLException
	{
		PreparedStatement  ps;

		try
		{
			ps = con.prepareStatement("INSERT INTO borrower VALUES (?,?,?,?,?,?,?,?,?)");

			ps.setString(1, b.getBid());
			ps.setString(2, b.getPassword());
			ps.setString(3, b.getName());
			ps.setString(4, b.getAddress());
			ps.setString(5, b.getPhone());
			ps.setString(6, b.getEmailAddress());
			ps.setString(7, b.getSinOrStNo());
			ps.setString(8, b.getExpiryDate());
			ps.setString(9, b.getType());
			ps.executeUpdate();

			// commit work 
			con.commit();

			ps.close();
		}
		catch (SQLException ex)
		{
			System.out.println("Message1: " + ex.getMessage());
			throw ex;
		}
		finally{
			try 
			{
				// undo the insert
				con.rollback();	

			}
			catch (SQLException ex2)
			{
				System.out.println("Message2: " + ex2.getMessage());
				System.exit(-1);
			}
		}
	}

	public void insertBorrowerType(BorrowerType bt){
		PreparedStatement ps;
		try
		{
			ps = con.prepareStatement("INSERT INTO borrowertype VALUES (?,?)");

			ps.setString(1, bt.getType());
			ps.setInt(2, bt.getBookTimeLimit());
			ps.executeUpdate();

			// commit work 
			con.commit();

			ps.close();
		}
		catch (SQLException ex)
		{
			System.out.println("Message: " + ex.getMessage());
			try 
			{
				// undo the insert
				con.rollback();	
			}
			catch (SQLException ex2)
			{
				System.out.println("Message: " + ex2.getMessage());
				System.exit(-1);
			}
		}
	}

	public void insertBorrowing(Borrowing b) throws SQLException{
		PreparedStatement  ps;

		try
		{
			ps = con.prepareStatement("INSERT INTO borrowing VALUES (?,?,?,?,?,?)");
			ps.setString(1, b.getBorid());
			ps.setString(2, b.getBid());
			ps.setString(3, b.getCallNumber());
			ps.setInt(4, b.getCopyNo());
			ps.setString(5, b.getOutDate());
			ps.setString(6, b.getInDate());
			ps.executeUpdate();

			// commit work 
			con.commit();

			ps.close();
		}
		catch (SQLException ex)
		{
			System.out.println("Message: " + ex.getMessage());
			throw ex;
		}
		finally{
			try 
			{
				con.rollback();	
			}
			catch (SQLException ex2)
			{
				System.out.println("Message: " + ex2.getMessage());
				System.exit(-1);
			}
		}
	}


	public void insertFine(Fine f){
		PreparedStatement  ps;

		try
		{
			ps = con.prepareStatement("INSERT INTO fine VALUES (?,?,?,?,?)");
			ps.setString(1, f.getFid());
			ps.setInt(2, f.getAmount());
			ps.setString(3, f.getIssueDate());
			ps.setString(4, f.getPaidDate());
			ps.setString(5, f.getBorid());
			ps.executeUpdate();

			// commit work 
			con.commit();

			ps.close();
		}
		catch (SQLException ex)
		{
			System.out.println("Message: " + ex.getMessage());
			try 
			{
				// undo the insert
				con.rollback();	
			}
			catch (SQLException ex2)
			{
				System.out.println("Message: " + ex2.getMessage());
				System.exit(-1);
			}
		}
	}

	public void insertHasAuthor(HasAuthor ha){
		// CallNumber, Name (Both primary keys)
		PreparedStatement  ps;

		try
		{
			ps = con.prepareStatement("INSERT INTO hasAuthor VALUES (?,?)");
			ps.setString(1, ha.getCallNumber());
			ps.setString(2, ha.getName());
			ps.executeUpdate();

			// commit work 
			con.commit();

			ps.close();
		}
		catch (SQLException ex)
		{
			System.out.println("Message: " + ex.getMessage());
			try 
			{
				// undo the insert
				con.rollback();	
			}
			catch (SQLException ex2)
			{
				System.out.println("Message: " + ex2.getMessage());
				System.exit(-1);
			}
		}

	}

	public void insertHasSubject(HasSubject hs){
		// CallNumber, Subject (Both primary keys)
		PreparedStatement  ps;

		try
		{
			ps = con.prepareStatement("INSERT INTO hasSubject VALUES (?,?)");
			ps.setString(1, hs.getCallNumber());
			ps.setString(2, hs.getSubject());
			ps.executeUpdate();

			// commit work 
			con.commit();

			ps.close();
		}
		catch (SQLException ex)
		{
			System.out.println("Message: " + ex.getMessage());
			try 
			{
				// undo the insert
				con.rollback();	
			}
			catch (SQLException ex2)
			{
				System.out.println("Message: " + ex2.getMessage());
				System.exit(-1);
			}
		}

	}

	public void insertHoldRequest(HoldRequest hr){
		// hid, bid, CallNumber, IssueDate (hid primary keys)
		PreparedStatement  ps;

		try
		{
			ps = con.prepareStatement("INSERT INTO holdRequest VALUES (?,?,?,?)");
			ps.setString(1, hr.getHoldId());
			ps.setString(2, hr.getBorrowerId());
			ps.setString(3, hr.getCallNumber());
			ps.setInt(4, hr.getIssueDate());
			ps.executeUpdate();

			// commit work 
			con.commit();

			ps.close();
		}
		catch (SQLException ex)
		{
			System.out.println("Message: " + ex.getMessage());
			try 
			{
				// undo the insert
				con.rollback();	
			}
			catch (SQLException ex2)
			{
				System.out.println("Message: " + ex2.getMessage());
				System.exit(-1);
			}
		}

	}

	// **********************************************************
	// ************************ DELETES *************************
	// ********************************************************** 

	public void deleteBook(String callNumber){
		PreparedStatement  ps;

		try
		{
			ps = con.prepareStatement("DELETE FROM book WHERE callNumber = ?");
			ps.setString(1, callNumber);

			int rowCount = ps.executeUpdate();

			if (rowCount == 0)
			{
				System.out.println("\nbook " + callNumber + " does not exist!");
			}

			con.commit();

			ps.close();
		}

		catch (SQLException ex)
		{
			System.out.println("Message: " + ex.getMessage());

			try 
			{
				con.rollback();	
			}
			catch (SQLException ex2)
			{
				System.out.println("Message: " + ex2.getMessage());
				System.exit(-1);
			}
		}
	}

	public void deleteBookCopy(String callNumber, String copyNo){
		PreparedStatement  ps;

		try
		{
			ps = con.prepareStatement("DELETE FROM bookcopy WHERE callNumber = ? AND copyNo = ?");
			ps.setString(1, callNumber);
			ps.setString(2, copyNo);

			int rowCount = ps.executeUpdate();

			if (rowCount == 0)
			{
				System.out.println("\nbookcopy " + callNumber + " does not exist!");
			}

			con.commit();

			ps.close();
		}

		catch (SQLException ex)
		{
			System.out.println("Message: " + ex.getMessage());

			try 
			{
				con.rollback();	
			}
			catch (SQLException ex2)
			{
				System.out.println("Message: " + ex2.getMessage());
				System.exit(-1);
			}
		}
	}

	public void deleteBorrower(String bid)
	{
		PreparedStatement  ps;

		try
		{
			ps = con.prepareStatement("DELETE FROM borrower WHERE bid = ?");
			ps.setString(1, bid);

			int rowCount = ps.executeUpdate();

			if (rowCount == 0)
			{
				System.out.println("\nborrower " + bid + " does not exist!");
			}

			con.commit();

			ps.close();
		}

		catch (SQLException ex)
		{
			System.out.println("Message: " + ex.getMessage());

			try 
			{
				con.rollback();	
			}
			catch (SQLException ex2)
			{
				System.out.println("Message: " + ex2.getMessage());
				System.exit(-1);
			}
		}
	}

	public void deleteBorrowerType(String type){
		PreparedStatement  ps;

		try
		{
			ps = con.prepareStatement("DELETE FROM borrowertype WHERE type = ?");
			ps.setString(1, type);

			int rowCount = ps.executeUpdate();

			if (rowCount == 0)
			{
				System.out.println("\nborrowertype " + type + " does not exist!");
			}

			con.commit();

			ps.close();
		}

		catch (SQLException ex)
		{
			System.out.println("Message: " + ex.getMessage());

			try 
			{
				con.rollback();	
			}
			catch (SQLException ex2)
			{
				System.out.println("Message: " + ex2.getMessage());
				System.exit(-1);
			}
		}

	}

	public void deleteBorrowing(String borid){
		PreparedStatement  ps;

		try
		{
			ps = con.prepareStatement("DELETE FROM borrowing WHERE borid = ?");
			ps.setString(1, borid);

			int rowCount = ps.executeUpdate();

			if (rowCount == 0)
			{
				System.out.println("\nBorrowingID " + borid + " does not exist!");
			}

			con.commit();

			ps.close();
		}

		catch (SQLException ex)
		{
			System.out.println("Message: " + ex.getMessage());

			try 
			{
				con.rollback();	
			}
			catch (SQLException ex2)
			{
				System.out.println("Message: " + ex2.getMessage());
				System.exit(-1);
			}
		}

	}


	public void deleteFine(String fid){
		PreparedStatement  ps;

		try
		{
			ps = con.prepareStatement("DELETE FROM fine WHERE fid = ?");
			ps.setString(1, fid);

			int rowCount = ps.executeUpdate();

			if (rowCount == 0)
			{
				System.out.println("\nFineID " + fid + " does not exist!");
			}

			con.commit();

			ps.close();
		}

		catch (SQLException ex)
		{
			System.out.println("Message: " + ex.getMessage());

			try 
			{
				con.rollback();	
			}
			catch (SQLException ex2)
			{
				System.out.println("Message: " + ex2.getMessage());
				System.exit(-1);
			}
		}

	}

	public void deleteHasAuthor(String callNumber, String name){
		PreparedStatement  ps;

		try
		{
			ps = con.prepareStatement("DELETE FROM hasauthor WHERE callNumber = ? AND name = ?");
			ps.setString(1, callNumber);
			ps.setString(2, name);

			int rowCount = ps.executeUpdate();

			if (rowCount == 0)
			{
				System.out.println("\nCall Number and Subject (" + callNumber + ", " + name + ") does not exist!");
			}

			con.commit();

			ps.close();
		}

		catch (SQLException ex)
		{
			System.out.println("Message: " + ex.getMessage());

			try 
			{
				con.rollback();	
			}
			catch (SQLException ex2)
			{
				System.out.println("Message: " + ex2.getMessage());
				System.exit(-1);
			}
		}

	}

	public void deleteHasSubject(String callNumber, String subject){
		PreparedStatement  ps;

		try
		{
			ps = con.prepareStatement("DELETE FROM hassubject WHERE callNumber = ? AND subject = ?");
			ps.setString(1, callNumber);
			ps.setString(2, subject);

			int rowCount = ps.executeUpdate();

			if (rowCount == 0)
			{
				System.out.println("\nThe book " + callNumber + "with subject, " + subject  + " does not exist!");
			}

			con.commit();

			ps.close();
		}

		catch (SQLException ex)
		{
			System.out.println("Message: " + ex.getMessage());

			try 
			{
				con.rollback();	
			}
			catch (SQLException ex2)
			{
				System.out.println("Message: " + ex2.getMessage());
				System.exit(-1);
			}
		}
	}

	public void deleteHoldRequest(String hid){
		PreparedStatement  ps;

		try
		{
			ps = con.prepareStatement("DELETE FROM holdrequest WHERE holdId = ?");
			ps.setString(1, hid);

			int rowCount = ps.executeUpdate();

			if (rowCount == 0)
			{
				System.out.println("\nHold Request " + hid + " does not exist!");
			}

			con.commit();

			ps.close();
		}

		catch (SQLException ex)
		{
			System.out.println("Message: " + ex.getMessage());

			try 
			{
				con.rollback();	
			}
			catch (SQLException ex2)
			{
				System.out.println("Message: " + ex2.getMessage());
				System.exit(-1);
			}
		}
	}

	// **********************************************************
	// ********************* FETCH DATA *************************
	// **********************************************************

	public ArrayList<Book> getBook(){
		ArrayList<Book> books = new ArrayList<Book>();
		Statement  stmt;
		ResultSet  rs;

		try
		{
			stmt = con.createStatement();

			rs = stmt.executeQuery("SELECT * FROM book");

			while(rs.next())
			{

				Book b = new Book(rs.getString("callnumber"),
						rs.getString("isbn"),
						rs.getString("title"),
						rs.getString("mainauthor"),
						rs.getString("publisher"),
						rs.getString("year"));
				books.add(b);

			}

			stmt.close();
		}
		catch (SQLException ex)
		{
			System.out.println("Message: " + ex.getMessage());
		}	
		return books;
	}

	public ArrayList<BookCopy> getBookCopy(){
		ArrayList<BookCopy> bookcopys = new ArrayList<BookCopy>();
		Statement  stmt;
		ResultSet  rs;

		try
		{
			stmt = con.createStatement();

			rs = stmt.executeQuery("SELECT * FROM bookcopy");

			while(rs.next())
			{

				BookCopy b = new BookCopy(rs.getString("callnumber"),
						rs.getInt("copyNo"),
						rs.getString("status"));
				bookcopys.add(b);

			}

			stmt.close();
		}
		catch (SQLException ex)
		{
			System.out.println("Message: " + ex.getMessage());
		}	
		return bookcopys;
	}

	public ArrayList<Borrower> getBorrower()
	{

		ArrayList<Borrower> borrowers = new ArrayList<Borrower>();
		Statement  stmt;
		ResultSet  rs;

		try
		{
			stmt = con.createStatement();

			rs = stmt.executeQuery("SELECT * FROM borrower");

			String bid;
			String password;
			String name;
			String address;
			String phone;
			String emailAddress;
			String sinOrStNo;
			String expiryDate;
			String type;
			while(rs.next())
			{
				bid = rs.getString("bid");
				password = rs.getString("password");
				name = rs.getString("name");
				address = rs.getString("address");
				phone = rs.getString("phone");
				emailAddress = rs.getString("emailAddress");
				sinOrStNo = rs.getString("sinOrStNo");
				expiryDate = rs.getString("expiryDate");
				type = rs.getString("type");
				Borrower b = new Borrower(bid,password,name,address,phone,emailAddress,
						sinOrStNo,expiryDate,type);
				borrowers.add(b);

			}

			stmt.close();
		}
		catch (SQLException ex)
		{
			System.out.println("Message: " + ex.getMessage());
		}	
		return borrowers;
	}


	public ArrayList<BorrowerType> getBorrowerType(){
		ArrayList<BorrowerType> borrowertypes = new ArrayList<BorrowerType>();
		Statement  stmt;
		ResultSet  rs;

		try
		{
			stmt = con.createStatement();

			rs = stmt.executeQuery("SELECT * FROM borrowertype");

			while(rs.next())
			{

				BorrowerType bt = new BorrowerType(rs.getString("type"),
						rs.getInt("bookTimeLimit"));
				borrowertypes.add(bt);

			}

			stmt.close();
		}
		catch (SQLException ex)
		{
			System.out.println("Message: " + ex.getMessage());
		}	
		return borrowertypes;
	}

	public ArrayList<Borrowing> getBorrowing(){
		ArrayList<Borrowing> borrowings = new ArrayList<Borrowing>();
		Statement  stmt;
		ResultSet  rs;

		try
		{
			stmt = con.createStatement();

			rs = stmt.executeQuery("SELECT * FROM borrowing");

			while(rs.next())
			{

				Borrowing b = new Borrowing(rs.getString("borid"),
						rs.getString("bid"),
						rs.getString("callNumber"), 
						rs.getInt("copyNo"), 
						rs.getString("outDate"), 
						rs.getString("inDate"));
				borrowings.add(b);

			}

			stmt.close();
		}
		catch (SQLException ex)
		{
			System.out.println("Message: " + ex.getMessage());
		}	
		return borrowings;
	}


	public ArrayList<Fine> getFine(){
		ArrayList<Fine> fines = new ArrayList<Fine>();
		Statement  stmt;
		ResultSet  rs;

		try
		{
			stmt = con.createStatement();

			rs = stmt.executeQuery("SELECT * FROM fine");

			while(rs.next())
			{

				Fine f = new Fine(rs.getString("fid"),
						rs.getInt("amount"),
						rs.getString("issueDate"),
						rs.getString("paidDate"),
						rs.getString("borid"));
				fines.add(f);

			}

			stmt.close();
		}
		catch (SQLException ex)
		{
			System.out.println("Message: " + ex.getMessage());
		}	
		return fines;
	}

	public ArrayList<HasAuthor> getHasAuthor(){
		ArrayList<HasAuthor> hasAuthor = new ArrayList<HasAuthor>();
		Statement  stmt;
		ResultSet  rs;

		try
		{
			stmt = con.createStatement();

			rs = stmt.executeQuery("SELECT * FROM hasauthor");

			while(rs.next())
			{
				HasAuthor h = new HasAuthor(rs.getString("callNumber"), rs.getString("name"));
				hasAuthor.add(h);
			}

			stmt.close();
		}
		catch (SQLException ex)
		{
			System.out.println("Message: " + ex.getMessage());
		}	
		return hasAuthor;
	}

	public ArrayList<HasSubject> getHasSubject(){
		ArrayList<HasSubject> hasSubjects = new ArrayList<HasSubject>();
		Statement  stmt;
		ResultSet  rs;

		try
		{
			stmt = con.createStatement();

			rs = stmt.executeQuery("SELECT * FROM hassubject");

			while(rs.next())
			{
				HasSubject h = new HasSubject(rs.getString("callNumber"), rs.getString("subjcet"));
				hasSubjects.add(h);
			}

			stmt.close();
		}
		catch (SQLException ex)
		{
			System.out.println("Message: " + ex.getMessage());
		}	
		return hasSubjects;
	}

	public ArrayList<HoldRequest> getHoldRequest(){
		ArrayList<HoldRequest> holdRequest = new ArrayList<HoldRequest>();
		Statement  stmt;
		ResultSet  rs;

		try
		{
			stmt = con.createStatement();

			rs = stmt.executeQuery("SELECT * FROM fine");

			while(rs.next())
			{
				HoldRequest h = new HoldRequest(rs.getString("holdId"),
						rs.getString("borrowerId"),
						rs.getString("callNumber"),
						rs.getInt("issueDate"));

				holdRequest.add(h);

			}

			stmt.close();
		}
		catch (SQLException ex)
		{
			System.out.println("Message: " + ex.getMessage());
		}	
		return holdRequest;
	}

	//**************************************************************
	//************************ UPDATES *****************************
	//**************************************************************

	public void updateBookCopy(String callNumber,int copyNo,String status){
		PreparedStatement ps;
		ResultSet rs;

		try {
			ps = con.prepareStatement("UPDATE bookcopy SET status = ? WHERE callnumber = ? AND copyno = ?");
			ps.setString(1, status);
			ps.setString(2,callNumber);
			ps.setInt(3, copyNo);

			int rowCount = ps.executeUpdate();
			if (rowCount == 0)
			{
				System.out.println("bookcopy does not exist!");
			}

			con.commit();

			ps.close();
		}
		catch (SQLException ex)
		{
			System.out.println("Message: " + ex.getMessage());

			try 
			{
				con.rollback();	
			}
			catch (SQLException ex2)
			{
				System.out.println("Message: " + ex2.getMessage());
				System.exit(-1);
			}
		}
	}

	//**************************************************************
	//************************ OTHER *******************************
	//**************************************************************

	// Determines whether or not the book exists
	public boolean hasBookCopy(String callNumber, int copyNo){
		PreparedStatement ps;

		try
		{
			ps = con.prepareStatement("SELECT * FROM bookcopy WHERE callNumber = ? AND copyNo = ?");
			ps.setString(1, callNumber);
			ps.setInt(2, copyNo);
			int rowCount = ps.executeUpdate();
			if (rowCount > 0) return true;
			ps.close();
		}
		catch (SQLException ex)
		{
			System.out.println("Message: " + ex.getMessage());
		}	
		return false;
	}

	// Determines whether or not the bookcopy's status is "in"
	public boolean isBookCopyIn(String callNumber, int copyNo) {
		PreparedStatement ps;
		ResultSet rs;

		try
		{
			ps = con.prepareStatement("SELECT status FROM bookcopy WHERE callNumber = ? AND copyNo = ?");
			ps.setString(1, callNumber);
			ps.setInt(2, copyNo);
			rs = ps.executeQuery();
			if (rs.next()) {
				if (rs.getString("status").equals("in")) 
					return true;
			}
			ps.close();
		}
		catch (SQLException ex)
		{
			System.out.println("Message from IsBookCopyIn: " + ex.getMessage());
		}	
		return false;
	}

	// returns the bookTimeLimit for borrower with borrower id = bid
	public int getTimeLimit(String bid) throws SQLException{
		PreparedStatement ps,ps2;
		ResultSet rs,rs2;
		String type;
		
		ps = con.prepareStatement("SELECT type FROM borrower WHERE bid = ?");
		ps.setString(1, bid);
		rs = ps.executeQuery();
		
		if (rs.next()) {
			type = rs.getString("type");
		}
		else {
			throw new SQLException("No borrower matches!");
		}	
		
		ps.close();
		rs.close();
		
		ps2 = con.prepareStatement("SELECT bookTimeLimit FROM borrowertype WHERE type = ?");
		ps2.setString(1, type);
		rs2 = ps2.executeQuery();
		
		if (rs2.next()){
			int result = rs2.getInt("bookTimeLimit");
			ps2.close();
			rs2.close();
			return result;
		}
		else throw new SQLException("type is invalid");	

	}
	
	public ArrayList<Borrowing> getOutBorrowings(){
		ArrayList<Borrowing> result = new ArrayList<Borrowing>();
		PreparedStatement ps;
		ResultSet rs;
		
		try {
			ps = con.prepareStatement("SELECT * FROM borrowing WHERE inDate is null");
			rs = ps.executeQuery();
			while (rs.next()){
				result.add(new Borrowing(
						rs.getString("borid"),
						rs.getString("bid"),
						rs.getString("callnumber"),
						rs.getInt("copyno"),
						rs.getString("outdate"),
						null));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return result;

	}


	


}
