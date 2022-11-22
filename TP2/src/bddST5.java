import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.*;
import java.util.Scanner;

public class bddST5 {
	public static void main(String[ ] args){ 
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Tokyo21_ST5", "root", "*****"); // con est un objet de type connection
			System.out.println("Vous etes connectes !");
			
//			 Inserting line into MySql DataBase
//			 Statement stmt = con.createStatement();
//			 String sql = "INSERT INTO Athletes VALUES ('Jesse Owens', 'USA', 'Athletics')";
//			 int i = stmt.executeUpdate(sql);
//			 System.out.println(i); //rows affected
			
//			Scanner myObj = new Scanner(System.in);  // Create a Scanner object
//			System.out.println("Now let's insert new data to create new 3 lines");
//			
//			for (int i=0; i<3; i++) {
//			    System.out.println("Enter name " + Integer.valueOf(i+1));
//			    String name = myObj.nextLine();  
//			    
//			    System.out.println("Enter country " + Integer.valueOf(i+1));
//			    String country = myObj.nextLine();  
//			    
//			    System.out.println("Enter discipline " + Integer.valueOf(i+1));
//			    String discipline = myObj.nextLine();
//			    
//			    String sql = "INSERT INTO Athletes VALUES (?, ?, ?)";
//			    PreparedStatement prep_stmt = con.prepareStatement(sql);
//			    prep_stmt.setString(1, name);
//			    prep_stmt.setString(2, country);
//			    prep_stmt.setString(3, discipline);
//			    int j = prep_stmt.executeUpdate();
//			    System.out.println("Line added succesfully!\n");
//			}
			
//			 Statement stmt = con.createStatement();
//			 String sql = "DROP TABLE IF EXISTS Athletes";
//			 int i = stmt.executeUpdate(sql);
//			 
//			 sql = "DROP TABLE IF EXISTS AthletesIdent";
//			 i = stmt.executeUpdate(sql);
//			 
//			 sql = "CREATE TABLE IF NOT EXISTS Athletes (Name VARCHAR(50) , Country VARCHAR(50), Discipline VARCHAR(50))";
//			 i = stmt.executeUpdate(sql);
//			 
//			 sql = "CREATE TABLE IF NOT EXISTS AthletesIdent (Name VARCHAR(50) , Country VARCHAR(50), Discipline VARCHAR(50), Ident INT)";
//			 i = stmt.executeUpdate(sql);
//			 
//			 String csvFilePath = "d:\\usuario\\Daniel\\Documentos\\CS\\ST5\\Supervision de capteurs\\TP2\\Athletes.csv";
//			 
//			 sql = "INSERT INTO Athletes VALUES (?, ?, ?)";
//			 PreparedStatement prep_stmt = con.prepareStatement(sql);
//			 
//			 String sql2 = "INSERT INTO AthletesIdent VALUES (?, ?, ?, ?)";
//			 PreparedStatement prep_stmt2 = con.prepareStatement(sql2);
//			 
//			 BufferedReader lineReader = new BufferedReader(new FileReader(csvFilePath));
//	         String lineText = null;
//	         int cont = 1;
//	         
//	         lineReader.readLine(); // skip header line
//        
//	         while ((lineText = lineReader.readLine()) != null) {
//	                String[] data = lineText.split(";");
//	                String name = data[0];
//	                String country = data[1];
//	                String discipline = data[2];
//	 
//	                prep_stmt.setString(1, name);
//	                prep_stmt.setString(2, country);
//	                prep_stmt.setString(3, discipline);
//	                
//	                prep_stmt2.setString(1, name);
//	                prep_stmt2.setString(2, country);
//	                prep_stmt2.setString(3, discipline);
//	                prep_stmt2.setInt(4, cont);
//	                
//	                int j = prep_stmt.executeUpdate();
//	                j = prep_stmt2.executeUpdate();
//	                cont++;
//	            }
//	         lineReader.close();
	         
//			Statement stmt = con.createStatement();
//			String sql = "DROP TABLE IF EXISTS Coaches";
//			stmt.executeUpdate(sql);
//			 
//			sql = "CREATE TABLE IF NOT EXISTS Coaches (Name VARCHAR(50) , Country VARCHAR(50), Discipline VARCHAR(50))";
//			stmt.executeUpdate(sql);
//	        
//			String csvFilePath = "d:\\usuario\\Daniel\\Documentos\\CS\\ST5\\Supervision de capteurs\\TP2\\Coaches.csv";
// 
//			sql = "INSERT INTO Coaches VALUES (?, ?, ?)";
//			PreparedStatement prep_stmt = con.prepareStatement(sql);
// 
//			BufferedReader lineReader = new BufferedReader(new FileReader(csvFilePath));
//			String lineText = null;
//			lineReader.readLine(); // skip header line
//         
//			while ((lineText = lineReader.readLine()) != null) {
//				String[] data = lineText.split(";");
//	
//				String name = data[0];
//				String country = data[1];
//				String discipline = data[2];
//				
//				prep_stmt.setString(1, name);
//				prep_stmt.setString(2, country);
//				prep_stmt.setString(3, discipline);
//        
//				prep_stmt.executeUpdate();
//			}
//			lineReader.close();
	         
//			stmt = con.createStatement();
//			String[] requests = {
//					"SELECT Name FROM Athletes WHERE Country='France' AND Discipline='Athletics' ORDER BY Name",
//					"SELECT DISTINCT Country FROM Athletes WHERE Discipline='Skateboarding' ORDER BY Country",
//					"SELECT DISTINCT Country FROM Athletes WHERE Discipline LIKE '%ball%' ORDER BY Country",
//					"SELECT COUNT(DISTINCT Country) FROM Athletes WHERE Discipline='Judo'"
//			};
//			String[] requests2 = {
//					"SELECT Name FROM AthletesIdent WHERE Country='France' AND Discipline='Athletics' ORDER BY Name",
//					"SELECT DISTINCT Country FROM AthletesIdent WHERE Discipline='Skateboarding' ORDER BY Country",
//					"SELECT DISTINCT Country FROM AthletesIdent WHERE Discipline LIKE '%ball%' ORDER BY Country",
//					"SELECT COUNT(DISTINCT Country) FROM AthletesIdent WHERE Discipline='Judo'"
//			};
//			
//			for (int k=0; k<4; k++) {
//				long start = System.currentTimeMillis();
//				sql = requests[i];
//				ResultSet rs = stmt.executeQuery(sql) ;
////				while (rs.next()) {
////					System.out.println(rs.getString(1));
////				}
//				long stop = System.currentTimeMillis();
//				System.out.println("\nExecution time: " + Long.valueOf(stop-start) + "ms");
//				
//				start = System.currentTimeMillis();
//				sql = requests2[i];
//				rs = stmt.executeQuery(sql) ;
////				while (rs.next()) {
////					System.out.println(rs.getString(1));
////				}
//				stop = System.currentTimeMillis();
//				System.out.println("\nExecution time: " + Long.valueOf(stop-start) + "ms");
//				System.out.println("------------------------\n");
//			}
			
//			Statement stmt = con.createStatement();
//			String sql = "SELECT t1.Name from Athletes t1 JOIN (SELECT * FROM Coaches WHERE Name = 'DORE Karine') t2 ON t1.Country = t2.Country AND t1.Discipline = t2.Discipline";
//			ResultSet rs = stmt.executeQuery(sql) ;
//			while (rs.next()) {
//				System.out.println(rs.getString(1));
//			}
	
			con.close();
		}
		catch(Exception e){ 
			System.out.println(e);
		}
	}
}
