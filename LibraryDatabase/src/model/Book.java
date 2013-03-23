package model;

public class Book {
	
	String callNumber;
	String isbn;
	String title;
	String mainAuthor;
	String publisher;
	int year;
	
	public Book(String callNumber, String isbn, String title, String mainAuthor, String publisher, int year){
		this.callNumber = callNumber;
		this.isbn = isbn;
		this.title = title;
		this.mainAuthor = mainAuthor;
		this.publisher = publisher;
		this.year = year;
	}
	
	public int getYear(){
		return year;
	}
	
	public String getCallNumber(){
		return callNumber;
	}
	
	public String getIsbn(){
		return isbn;
	}

	public String getTitle(){
		return title;
	}
	
	public String getMainAuthor(){
		return mainAuthor;
	}
	
	public String getPublisher(){
		return publisher;
	}
}
