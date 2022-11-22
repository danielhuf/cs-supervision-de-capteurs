import static org.junit.jupiter.api.Assertions.*; 

public class RelationTest { 
	@org.junit.jupiter.api.Test
	void relations() { 
		System.out.println("Testing relations"); 

		Etudiant bob = new Etudiant("Bob"); 
		Etudiant alice = new Etudiant("Alice"); 
		Enseignant jf = new Enseignant("JF"); 
		Enseignant fred = new Enseignant("Fred");
		
		new BoisDesCafesAvec(jf, fred); 
		new BoisDesCafesAvec(alice,jf);
		
		assertThrows(Exception.class,() -> new EnseigneA(fred, jf) ); 
		assertThrows(Exception.class,() -> new Heberge(jf, bob) ); 
	} 
} 
