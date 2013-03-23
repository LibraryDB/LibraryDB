package model;

import java.sql.Date;

public class Fine {
	private int fid;
	private int amount;
	private String issueDate;
	private String paidDate;
	private int borid;
	
	public Fine (
			int fid,
			int amount,
			String issueDate,
			String paidDate,
			int borid) 
	{
		this.fid = fid;
		this.amount = amount;
		this.issueDate = issueDate;
		this.paidDate = paidDate;
		this.borid = borid;
	}
	
	public int getFid(){
		return fid;
	}
	
	public int getAmount(){
		return amount;
	}
	
	public String getIssueDate(){
		return issueDate;
	}
	
	public String getPaidDate(){
		return paidDate;
	}
	
	public int getBorid(){
		return borid;
	}
}
