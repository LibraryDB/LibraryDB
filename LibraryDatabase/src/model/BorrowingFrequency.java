package model;

public class BorrowingFrequency {
	
	private String callNumber;
	private int frequency;

	public BorrowingFrequency(String b, int f) {
		this.callNumber = b;
		this.frequency = f;
	}

	public String getCallNumber() {
		return callNumber;
	}

	public void setCallNumber(String b) {
		this.callNumber = b;
	}

	public int getFrequency() {
		return frequency;
	}

	public void setFrequency(int f) {
		this.frequency = f;
	}
	
	

}
