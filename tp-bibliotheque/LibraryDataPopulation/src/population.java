import java.io.*;
import java.sql.*;

public class population {
	
	public static void main(String[ ] args) {
		
		String sql, csvFilePath, lineText; 
		PreparedStatement prep_stmt;
		BufferedReader lineReader;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
    		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Library_ST5", "root", "*****");
    		System.out.println("Connection established!");
    		
    		// Cleaning DataBase
    		Statement stmt = con.createStatement();
    		String[] tables = {"Editions", "Works", "Books", "Authors", "Authorships", "Users", "Categories", "BorrowHistory", "RedListLog"};
    		
    		for (int i=0; i<tables.length; i++) {
    			sql = "DROP TABLE IF EXISTS " + tables[i];
    			stmt.executeUpdate(sql);
    		}
    		
    		// Creating Tables
    		String[] creations = {
//    				"CREATE TABLE IF NOT EXISTS Editions (Publisher VARCHAR(100) , PublicationYear NUMERIC(4), ISBN NUMERIC(13))",
//    				"CREATE TABLE IF NOT EXISTS Works (Title VARCHAR(100), FirstPublicationYear NUMERIC(4), Keyword VARCHAR(50), ID INT PRIMARY KEY NOT NULL AUTO_INCREMENT)",
//    				"CREATE TABLE IF NOT EXISTS Books (EditionISBN NUMERIC(13), WorkID INT, Copies INT, ID INT PRIMARY KEY NOT NULL AUTO_INCREMENT)",
//    				"CREATE TABLE IF NOT EXISTS Authors (FirstName VARCHAR(50), LastName VARCHAR(50), BirthYear NUMERIC(4), ID INT PRIMARY KEY NOT NULL AUTO_INCREMENT)",
//    				"CREATE TABLE IF NOT EXISTS Authorships (WorkID INT, AuthorID INT)",
    				"CREATE TABLE IF NOT EXISTS Categories (CatDescription VARCHAR(50), MaxBorrow INT, MaxBorrowDuration INT)",
    				"CREATE TABLE IF NOT EXISTS Users (FirstName VARCHAR(50), LastName VARCHAR(50), Email VARCHAR(50), CategoryDescription VARCHAR(50), ID INT)",
//    				"CREATE TABLE IF NOT EXISTS BorrowHistory (BookID INT, UserEmail VARCHAR(50), BorrowingDate DATE, DeliveryDate DATE)",
//    				"CREATE TABLE IF NOT EXISTS RedListLog (UserID INT, RedListStatus ENUM('put', 'removed'), LogDate DATE)"
    		};
    		for (int i=0; i<creations.length; i++) {
    			stmt.executeUpdate(creations[i]);
    		}
    		
    		// Integrity Constraints
    		String[] constraints = {
//    				"ALTER TABLE Editions ADD CONSTRAINT PK_editions PRIMARY KEY (ISBN)",
//    				"ALTER TABLE Authorships ADD CONSTRAINT PK_authorships PRIMARY KEY (WorkID, AuthorID)",
    				"ALTER TABLE Categories ADD CONSTRAINT PK_categories PRIMARY KEY (CatDescription)",
    				"ALTER TABLE Users ADD CONSTRAINT PK_users PRIMARY KEY (Email)",
//    				"ALTER TABLE BorrowHistory ADD CONSTRAINT PK_borrow_history PRIMARY KEY (BookID, UserID, BorrowingDate)",
//    				"ALTER TABLE RedListlog ADD CONSTRAINT PK_red_list_log PRIMARY KEY (UserID, RedListStatus, LogDate)",

//    				"ALTER TABLE Books ADD CONSTRAINT FK_books_1 FOREIGN KEY (EditionISBN) REFERENCES Editions(ISBN)",
//    				"ALTER TABLE Books ADD CONSTRAINT FK_books_2 FOREIGN KEY (WorkID) REFERENCES Works(ID)",
//    				"ALTER TABLE Authorships ADD CONSTRAINT FK_authorships_1 FOREIGN KEY (WorkID) REFERENCES Works(ID)",
//    				"ALTER TABLE Authorships ADD CONSTRAINT FK_authorships_2 FOREIGN KEY (AuthorID) REFERENCES Authors(ID)",
    				"ALTER TABLE Users ADD CONSTRAINT FK_users FOREIGN KEY (CategoryDescription) REFERENCES Categories(CatDescription)",
//    				"ALTER TABLE BorrowHistory ADD CONSTRAINT FK_borrow_history_1 FOREIGN KEY (BookID) REFERENCES Books(ID)",
//    				"ALTER TABLE BorrowHistory ADD CONSTRAINT FK_borrow_history_2 FOREIGN KEY (UserEmail) REFERENCES Users(Email)",
//    				"ALTER TABLE RedListlog ADD CONSTRAINT FK_red_list_log FOREIGN KEY (UserID) REFERENCES Users(ID)"
    		};
    		for (int i=0; i<constraints.length; i++) {
    			stmt.executeUpdate(constraints[i]);
    		}
    		
    		// Insertions
    		
    		csvFilePath = "D:\\usuario\\Daniel\\Documentos\\PUC\\2022.1\\POO\\WS\\LibraryDataPopulation\\data\\Categories.csv";
    		sql = "INSERT INTO Categories VALUES (?, ?, ?)";
    		prep_stmt = con.prepareStatement(sql);
    		
    		lineReader = new BufferedReader(new FileReader(csvFilePath));
    		lineText = null;
    		lineReader.readLine(); // skip header line
    		while ((lineText = lineReader.readLine()) != null) {
    			String[] data = lineText.split(";");
    			prep_stmt.setString(1, data[0]);
    			prep_stmt.setInt(2, Integer.valueOf(data[1]));
    			prep_stmt.setInt(3, Integer.valueOf(data[2]));
    			prep_stmt.executeUpdate();
    		}
    		lineReader.close();
    		
    		csvFilePath = "D:\\usuario\\Daniel\\Documentos\\PUC\\2022.1\\POO\\WS\\LibraryDataPopulation\\data\\Users.csv";
    		sql = "INSERT INTO Users VALUES (?, ?, ?, ?, ?)";
    		prep_stmt = con.prepareStatement(sql);
    		
    		lineReader = new BufferedReader(new FileReader(csvFilePath));
    		lineText = null;
    		lineReader.readLine(); // skip header line
    		while ((lineText = lineReader.readLine()) != null) {
    			String[] data = lineText.split(";");
    			prep_stmt.setString(1, data[0]);
    			prep_stmt.setString(2, data[1]);
    			prep_stmt.setString(3, data[2]);
    			prep_stmt.setString(4, data[3]);
    			prep_stmt.setInt(5, Integer.valueOf(data[4]));
    			prep_stmt.executeUpdate();
    		}
    		lineReader.close();
    		
    		con.close();
		}
		catch(Exception e){ 
			System.out.println(e);
		}
	}
}
