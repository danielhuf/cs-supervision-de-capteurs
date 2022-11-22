package Model;

public class Borrow {
	
	public String firstName, lastName, category, title, borrowDate, author;
	public int duration, maxDuration, publicationYear, id, bookId;
	
	Borrow (String firstName, String lastName, String category, String title, String borrowDate, int duration, int maxDuration, int publicationYear, String author, int id, int bookId) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.category = category;
		this.title = title;
		this.borrowDate = borrowDate;
		this.duration = duration;
		this.maxDuration = maxDuration;
		this.publicationYear = publicationYear;
		this.author = author;
		this.id = id;
		this.bookId = bookId;
	}
	
    public String getFirstName() { return firstName; }
    
    public String getLastName() { return lastName; }
	
    public String getCategory() { return category; }
    
    public String getTitle() { return title; }
    
    public String getBorrowDate() { return borrowDate; }
    
    public int getDuration() { return duration; }
    
    public int getMaxDuration() { return maxDuration; }
    
    public int getPublicationYear() { return publicationYear; }
    
    public String getAuthor() { return author; }
    
    public int getId() { return this.id; }
    
    public int getBookId() { return this.bookId; }
}