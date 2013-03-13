package model;

public class Borrower {
	private String bid;
	private String password;
	private String name;
	private String address;
	private String phone;
	private String emailAddress;
	private String sinOrStNo;
	private String expiryDate;
	private String type;
	
	public Borrower(String bid, 
			String password,
			String name,
			String address,
			String phone,
			String emailAddress,
			String sinOrStNo,
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
	
	public String getBid(){
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
	
	public String getPhone(){
		return phone;
	}
	
	public String getEmailAddress(){
		return emailAddress;
	}
	
	public String getSinOrStNo(){
		return sinOrStNo;
	}
	
	public String getExpiryDate(){
		return expiryDate;
	}
	
	public String getType(){
		return type;
	}
	
}
