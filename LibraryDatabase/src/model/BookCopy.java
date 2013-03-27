package model;

public class BookCopy {
	String callNumber; // primary key
	int copyNo; // primary key
	String status;
	public static final String ON_HOLD = "on-hold";
	public static final String IN = "in";
	public static final String OUT = "out";

	public BookCopy(String callNumber, int copyNo, String status){
		this.callNumber = callNumber;
		this.copyNo = copyNo;
		this.status = status;
	}
	
	public String getCallNumber(){
		return callNumber;
	}
	
	public int getCopyNo(){
		return copyNo;
	}
	
	public String getStatus(){
		return status;
	}
}
