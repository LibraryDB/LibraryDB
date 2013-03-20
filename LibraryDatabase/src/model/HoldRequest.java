package model;

public class HoldRequest {
	// HoldRequest (Hid, Bid, CallNumber, IssueDate)
	// hid is primary key
	// (bid) is foreign key referencing Borrower
	// (CallNumber) is foreign key referencing Book
	
	private String holdId;
	private String borrowerId;
	private String callNumber;
	private Integer issueDate;
	
	public HoldRequest(String holdId, String borrowerId, String callNumber,
			Integer issueDate) {
		this.holdId = holdId;
		this.borrowerId = borrowerId;
		this.callNumber = callNumber;
		this.issueDate = issueDate;
	}

	public String getHoldId() {
		return holdId;
	}

	public void setHoldId(String holdId) {
		this.holdId = holdId;
	}

	public String getBorrowerId() {
		return borrowerId;
	}

	public void setBorrowerId(String borrowerId) {
		this.borrowerId = borrowerId;
	}

	public String getCallNumber() {
		return callNumber;
	}

	public void setCallNumber(String callNumber) {
		this.callNumber = callNumber;
	}

	public Integer getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(Integer issueDate) {
		this.issueDate = issueDate;
	}

}
