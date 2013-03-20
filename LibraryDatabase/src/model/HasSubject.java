package model;

public class HasSubject {
	// HasSubject (CallNumber, Subject)
	// All fields are primary key
	// CallNumber is foreign key referencing Book
	
	private String callNumber;
	private String subject;
	
	public HasSubject(String callNumber, String subject) {
		this.callNumber = callNumber;
		this.subject = subject;
	}

	public String getCallNumber() {
		return callNumber;
	}

	public void setCallNumber(String callNumber) {
		this.callNumber = callNumber;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

}
