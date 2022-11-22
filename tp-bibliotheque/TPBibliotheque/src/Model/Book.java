package Model;

public class Book {
	
	private String title, author, isbn, keyword, publisher;
	private int publicationYear, copies, available, id;
	
	Book(int copies, int available, String title, String keyword, String publisher, int publicationYear, String isbn, String author, int id) { 
		this.copies = copies;
		this.available = available;
		this.title = title;
		this.keyword = keyword;
		this.publisher = publisher;
		this.publicationYear = publicationYear;
		this.isbn = isbn;
		this.author = author;
		this.id = id;
	}
	
	public int getCopies() { return this.copies; }
	
	public int getAvailable() { return this.available; }
	
	public String getTitle() { return this.title; }
	
	public String getKeyword()  { return this.keyword; }
	
	public String getPublisher()  { return this.publisher; }
	
	public int getPublicationYear() { return this.publicationYear; }
	
	public String getIsbn()  { return this.isbn; }
	
	public String getAuthor()  { return this.author; }
	
	public int getId()  { return this.id; }

}