package model;

public class Borrowing {
	private String borid;
	private String bid;
	private String callNumber;
	private int copyNo;
	private String outDate;
	private String inDate;
	
	public Borrowing(
			String borid,
			String bid,
			String callNumber,
			int copyNo,
			String outDate,
			String inDate) 
	{
		this.borid = borid;
		this.bid = bid;
		this.callNumber = callNumber;
		this.copyNo = copyNo;
		this.outDate = outDate;
		this.inDate = inDate;
	}
	
	public String getBorid(){
		return borid;
	}
	
	public String getBid(){
		return bid;
	}
	
	public String getCallNumber(){
		return callNumber;
	}
	
	public int getCopyNo(){
		return copyNo;
	}
	
	public String getOutDate(){
		return outDate;
	}
	
	public String getInDate(){
		return inDate;
	}
}
