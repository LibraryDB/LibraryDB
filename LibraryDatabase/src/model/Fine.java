package model;

public class Fine {
	private String fid;
	private int amount;
	private String issueDate;
	private String paidDate;
	private String borid;
	
	public Fine (
			String fid,
			int amount,
			String issueDate,
			String paidDate,
			String borid) 
	{
		this.fid = fid;
		this.amount = amount;
		this.issueDate = issueDate;
		this.paidDate = paidDate;
		this.borid = borid;
	}
	
	public String getFid(){
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
	
	public String getBorid(){
		return borid;
	}
}
