package model;

public class BorrowingFrequency {
	
	private Borrowing b;
	private int f;

	public BorrowingFrequency(Borrowing b, int f) {
		this.b = b;
		this.f = f;
	}

	public Borrowing getB() {
		return b;
	}

	public void setB(Borrowing b) {
		this.b = b;
	}

	public int getF() {
		return f;
	}

	public void setF(int f) {
		this.f = f;
	}
	
	

}
