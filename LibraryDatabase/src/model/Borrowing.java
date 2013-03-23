package model;

import java.sql.Date;

public class Borrowing {
	private int borid;
	private int bid;
	private String callNumber;
	private int copyNo;
	private String outDate;
	private String inDate;
	
	public Borrowing(
			int borid,
			int bid,
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
	
	public int getBorid(){
		return borid;
	}
	
	public int getBid(){
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
