package Model;

public class User {
	
	private String firstName, lastName, email, category;
	private int id;
	private boolean redList;
	
	User(String firstName, String lastName, String email, String category, int id, boolean redList) { 
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.category = category;
		this.id = id;
		this.redList = redList;
	}
	
	public int getId() { return this.id; }
	
	public String getFirstName() { return this.firstName; }
	
	public String getLastName() { return this.lastName; }
	
	public String getCategory()  { return this.category; }
	
	public String getEmail()  { return this.email.split("@")[0]; }
	
	public boolean getRedList() { return this.redList; }
	
	public void setRedList() { this.redList = !this.redList; }
}
