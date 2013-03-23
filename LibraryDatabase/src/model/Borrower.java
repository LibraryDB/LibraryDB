package model;

import java.sql.Date;

public class Borrower {
	private int bid;
	private String password;
	private String name;
	private String address;
	private int phone;
	private String emailAddress;
	private int sinOrStNo;
	private String expiryDate;
	private String type;
	
	public Borrower(int bid, 
			String password,
			String name,
			String address,
			int phone,
			String emailAddress,
			int sinOrStNo,
			String expiryDate,
			String type)
	{
		this.bid = bid;
		this.password = password;
		this.name = name;
		this.address = address;
		this.phone = phone;
		this.emailAddress = emailAddress;
		this.sinOrStNo = sinOrStNo;
		this.expiryDate = expiryDate;
		this.type = type;
	}
	
	public int getBid(){
		return bid;
	}
	
	public String getPassword(){
		return password;
	}

	public String getName(){
		return name;
	}
	
	public String getAddress(){
		return address;
	}
	
	public int getPhone(){
		return phone;
	}
	
	public String getEmailAddress(){
		return emailAddress;
	}
	
	public int getSinOrStNo(){
		return sinOrStNo;
	}
	
	public String getExpiryDate(){
		return expiryDate;
	}
	
	public String getType(){
		return type;
	}
	
}
