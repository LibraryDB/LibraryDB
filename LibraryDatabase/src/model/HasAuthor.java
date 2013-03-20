package model;

public class HasAuthor {
	// HasAuthor (CallNumber, Name)
	// All fields are primary keys
	// CallNumber is foreign key referencing Book
	
	private String callNumber;
	private String name;
	
	public HasAuthor (String callNumber, String name) {
		this.callNumber = callNumber;
		this.name = name;
	}
	
	public String getCallNumber() {
		return callNumber;
	}

	public void setCallNumber(String callNumber) {
		this.callNumber = callNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


}
