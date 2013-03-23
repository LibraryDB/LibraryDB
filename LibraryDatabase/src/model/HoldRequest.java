package model;

import java.sql.Date;

public class HoldRequest {
	// HoldRequest (Hid, Bid, CallNumber, IssueDate)
	// hid is primary key
	// (bid) is foreign key referencing Borrower
	// (CallNumber) is foreign key referencing Book
	
	private int hid;
	private int bid;
	private String callNumber;
	private String issueDate;
	
	public HoldRequest(int holdId, int borrowerId, String callNumber,
			String issueDate) {
		hid = holdId;
		bid = borrowerId;
		this.callNumber = callNumber;
		this.issueDate = issueDate;
	}

	public int getHoldId() {
		return hid;
	}

	public void setHoldId(int holdId) {
		this.hid = holdId;
	}

	public int getBorrowerId() {
		return bid;
	}

	public void setBorrowerId(int borrowerId) {
		this.bid = borrowerId;
	}

	public String getCallNumber() {
		return callNumber;
	}

	public void setCallNumber(String callNumber) {
		this.callNumber = callNumber;
	}

	public String getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(String issueDate) {
		this.issueDate = issueDate;
	}

}
