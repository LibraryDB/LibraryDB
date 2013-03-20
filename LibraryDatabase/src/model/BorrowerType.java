package model;

public class BorrowerType {
	private String type;
	private int bookTimeLimit; 
	
	public BorrowerType(String type, int bookTimeLimit) {
		this.type = type;
		this.bookTimeLimit = bookTimeLimit;
	}
	
	public String getType(){
		return type;
	}
	
	public int getBookTimeLimit(){
		return bookTimeLimit;
	}
}
