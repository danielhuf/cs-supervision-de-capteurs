package Model;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import Controller.Controller;

public class ModelFacade {
	
	private static ModelFacade mf = null;
	private Connection con;
	private User user;
	
	/**
	 * Singleton pattern implementation
	 * @return unique instance of ModelFacade type
	 */
	public static ModelFacade getAPI() {
		if (mf == null) 
			mf = new ModelFacade();
		return mf;
	}
	
    public void establishConnection() {
    	try {
    		Class.forName("com.mysql.cj.jdbc.Driver");
    		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Library_ST5", "root", "titi1968!@_");
    		System.out.println("Connection established!");
    	}
    	catch (Exception e) {
    		System.out.println(e);
    	}
    }
    
    public void closeConnection() {
    	try {
    		con.close();
    		System.out.println("Connection closed!");
    	}
    	catch (SQLException e) {
    		System.out.println(e);
    	}
    }
    
    public User getUser() { return user; }
    
    public boolean checkCredentials(String u, String password) {
    	try {
			String sql = "SELECT * FROM Users WHERE Email = ? AND ID = ?";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, u);
			stmt.setString(2, password);
			ResultSet rs = stmt.executeQuery();
			
			if (rs.next()) {  // an user with such credentials was found
				if (rs.getString(4).equals("Manager")) {
					Controller.getController().setManager(true);
				}
				else {
					Controller.getController().setManager(false);
				}
				user = new User(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getBoolean(6));
				return true;
			}
			
			else { return false; }
			
		} 
    	catch (SQLException e) {
			System.out.println(e);
			return false;
		}
    }
    
    public ArrayList<String> getCategories() {
    	ArrayList<String> categories = new ArrayList<String>();
    	String sql = "SELECT CatDescription FROM Categories";
    	
    	try {
			ResultSet rs = con.createStatement().executeQuery(sql);
			while (rs.next()) {
			    categories.add(rs.getString(1));
			}
		} 
    	catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	return categories;
    }
    
    public boolean setCategory(String cat, int maxLoan, int maxDuration) {
    	String sql = "UPDATE Categories SET MaxBorrow = ?, MaxBorrowDuration = ? WHERE CatDescription = ?";
    	
		try {
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, maxLoan);
			stmt.setInt(2, maxDuration);
			stmt.setString(3, cat);
			stmt.executeUpdate();
			return true;
		} 
		catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
    }
    
    public int getNbLoans() {
    	String sql = "SELECT COUNT(*) FROM BorrowHistory WHERE UserID = ? AND DeliveryDate IS NULL";
    	
		try {
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, user.getId());
			ResultSet rs = stmt.executeQuery();
			rs.next();
			return rs.getInt(1);
		} 
		catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
    }
    
    public int[] getCategoryData() {
    	String sql = "SELECT MaxBorrow, MaxBorrowDuration FROM Categories WHERE CatDescription = ?";
    	int[] categoryData = new int[2];
    	
		try {
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, user.getCategory());
			ResultSet rs = stmt.executeQuery();
			rs.next();
			categoryData[0] = rs.getInt(1);
			categoryData[1] = rs.getInt(2);
			return categoryData;
		} 
		catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
    }
    
    public List<Borrow> consultBorrows() {
    	
    	List<Borrow> consultBorrowList = new ArrayList<Borrow>();
    	
    	String sql = "SELECT u.FirstName, u.LastName, u.CategoryDescription, w.Title, h.BorrowingDate, TIMESTAMPDIFF(month, h.BorrowingDate, CURRENT_TIMESTAMP), c.MaxBorrowDuration, e.PublicationYear"
    			+ " FROM BorrowHistory h, Books b, Works w, Users u, Categories c, Editions e"
    			+ " WHERE h.DeliveryDate IS NULL AND h.BookID = b.ID AND e.WorkID = w.ID"
    			+ " AND h.UserID = u.ID AND u.CategoryDescription = c.CatDescription AND b.EditionISBN = e.ISBN"
    			+ " ORDER BY h.BorrowingDate";
    	
    	try {
			ResultSet rs = con.createStatement().executeQuery(sql);
			while (rs.next()) {
				Borrow b = new Borrow(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6), rs.getInt(7), rs.getInt(8), "", 0, 0);
				consultBorrowList.add(b);
			}
		} 
    	catch (SQLException e) { e.printStackTrace(); }
    	
    	return consultBorrowList;
    }
    
    public List<User> manageUsers() {
    	
    	List<User> manageUsers = new ArrayList<User>();
    	String sql = "SELECT * FROM Users ORDER BY LastName";
    	
    	try {
			ResultSet rs = con.createStatement().executeQuery(sql);
			while (rs.next()) {
				User u = new User(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getBoolean(6));
				manageUsers.add(u);
			}
		} 
    	catch (SQLException e) { e.printStackTrace(); }
    	
    	return manageUsers;
    }
    
    public List<Integer> filterUsers() {
    	
    	List<Integer> users = new ArrayList<Integer>();
    	String sql = "SELECT ID FROM Users WHERE ID IN"
				+ " (SELECT u.ID"
				+ " FROM Users u, BorrowHistory b, Categories c"
				+ " WHERE u.ID = b.UserID AND u.CategoryDescription = c.CatDescription"
				+ " AND TIMESTAMPDIFF(month, b.BorrowingDate, CURRENT_TIMESTAMP) > c.MaxBorrowDuration)";  // filter users who are late
    	
    	try {
			ResultSet rs = con.createStatement().executeQuery(sql);
			while (rs.next()) 
				users.add(rs.getInt(1));
		} 
    	catch (SQLException e) { e.printStackTrace(); }
    	
    	return users;
    }
    
    public User setRedList(User u) {
    	
    	String comment;
    	if (u.getRedList()) 
    		comment = "removed";
    	else 
    		comment = "put";
    	
    	String sql = "UPDATE Users SET RedList = NOT RedList WHERE ID = ?";
    	String sql2 = "INSERT INTO RedListLog(UserID, RedListStatus, LogDate) VALUES (?, ?, ?)";
		PreparedStatement stmt;
		
		try {
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, u.getId());
			stmt.executeUpdate();
			
			stmt = con.prepareStatement(sql2);
			stmt.setInt(1, u.getId());
			stmt.setString(2, comment);
			stmt.setString(3, LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
			stmt.executeUpdate();
		} 
		catch (SQLException e) { e.printStackTrace(); }
		
		u.setRedList(); 
		if (u.getId() == getUser().getId()) //in the case the selected row is the own manager who is entering the system
			getUser().setRedList();
		
		return u;
    }
    
    public List<Object> borrowBooks() {
    	
    	List<Object> result = new ArrayList<Object>();
    	List<Book> borrowBooks = new ArrayList<Book>();
    	
    	if (user.getRedList())   // user is on red list, cannot borrow
    		result.add(1);
    	
    	else if (checkMaxBorrows())  // user achieved maximum number of borrows
    		result.add(2);
    	
    	else {
    		result.add(3);
        	String sql = "SELECT b.Copies, b.Available, w.Title, w.Keyword, p.Name, e.PublicationYear, e.ISBN, group_concat(CONCAT(a.FirstName, ' ' , a.LastName) SEPARATOR ', '), b.ID"
        			+ " FROM books b, works w, Editions e, Authorships ah, Authors a, Publishers p"
        			+ " WHERE e.WorkID = w.ID AND b.EditionISBN = e.ISBN"
        			+ " AND e.WorkID = ah.WorkID AND ah.AuthorID = a.ID"
        			+ " AND e.PublisherID = p.ID AND Available > 0"
        			+ " GROUP BY b.id;";
        	
        	try {
    			ResultSet rs = con.createStatement().executeQuery(sql);
    			while (rs.next()) {
    				Book b = new Book(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6), rs.getString(7), rs.getString(8), rs.getInt(9));
    				borrowBooks.add(b);
    			}
    		} 
        	catch (SQLException e) { e.printStackTrace(); }
    	}
    	
    	result.add(borrowBooks);
    	return result;
    }
    
    private boolean checkMaxBorrows() {
    	
    	String sql = "SELECT b.UserID, COUNT(b.UserID), c.MaxBorrow"
    			+ " FROM BorrowHistory b, Users u, Categories c"
    			+ " WHERE b.DeliveryDate IS NULL AND b.UserID = u.ID"
    			+ " AND u.CategoryDescription = c.CatDescription"
    			+ " GROUP BY b.UserID"
    			+ " HAVING COUNT(b.UserID) > c.MaxBorrow";
    	
    	try {
			ResultSet rs = con.createStatement().executeQuery(sql);
			while (rs.next()) 
				if (rs.getInt(1) == user.getId())
					return true;
		} 
    	catch (SQLException e) { e.printStackTrace(); }
    	
    	return false;
    }
    
    public void borrowBook(Book b) {
    	
    	PreparedStatement stmt;
    	String sql = "UPDATE Books SET Available = Available - 1 WHERE ID = ?";
    	String sql2 = "INSERT INTO BorrowHistory(BookID, UserID,BorrowingDate)  VALUES (?, ?, ?)";
    	
		try {
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, b.getId());
			stmt.executeUpdate();
			
			stmt = con.prepareStatement(sql2);
			stmt.setInt(1, b.getId());
			stmt.setInt(2, user.getId());
			stmt.setString(3, LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
			stmt.executeUpdate();
		} 
		catch (SQLException e) { e.printStackTrace(); }
		
    }
    
    // Show user's list of borrowed books (to return them if possible)
    public List<Borrow> returnBooksList() {
    	
    	List<Borrow> returnBooks = new ArrayList<Borrow>();
    	
    	String sql = "SELECT w.Title, h.BorrowingDate, TIMESTAMPDIFF(month, h.BorrowingDate, CURRENT_TIMESTAMP), e.PublicationYear, group_concat(CONCAT(a.FirstName, ' ' , a.LastName) SEPARATOR ', '), h.ID, b.ID"
    			+  " FROM BorrowHistory h, Books b, Works w, Users u, Editions e, Authorships ah, Authors a"
    			+ " WHERE h.DeliveryDate IS NULL AND h.BookID = b.ID AND e.WorkID = w.ID"
    			+ " AND h.UserID = u.ID AND b.EditionISBN = e.ISBN AND e.WorkID = ah.WorkID"
    			+ " AND ah.AuthorID = a.ID AND h.UserID = ?"
    			+ " GROUP BY h.id ORDER BY h.BorrowingDate";
    	
    	try {
    		PreparedStatement stmt = con.prepareStatement(sql);
    		stmt.setInt(1, user.getId());
    		ResultSet rs = stmt.executeQuery();
    		
			while (rs.next()) {
				Borrow r = new Borrow("", "", "", rs.getString(1), rs.getString(2), rs.getInt(3), 0, rs.getInt(4), rs.getString(5), rs.getInt(6), rs.getInt(7));
				returnBooks.add(r);
			}
		} 
    	catch (SQLException e) { e.printStackTrace(); }
    	
    	return returnBooks;
    }
    
    public void returnBook(Borrow b) {
    	
    	PreparedStatement stmt;
    	String sql = "UPDATE BorrowHistory SET DeliveryDate = ? WHERE ID = ?";
    	String sql2 = "UPDATE Books SET Available = Available + 1 WHERE ID = ?";
    	
		try {
			stmt = con.prepareStatement(sql);
			stmt.setString(1, LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
			stmt.setInt(2, b.getId());
			stmt.executeUpdate();
			
			stmt = con.prepareStatement(sql2);
			stmt.setInt(1, b.getBookId());
			stmt.executeUpdate();
		} 
		catch (SQLException e) { e.printStackTrace(); }
    		
    }
}
