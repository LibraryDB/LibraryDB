package model;

public class BookCopy {
	String callNumber;
	int copyNo;
	String status;

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
