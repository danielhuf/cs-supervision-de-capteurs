import java.util.Vector;

public class main {
	public static void main(String[] args) {
		System.out.println("Hello World");
		
		Vector<Personne> people = new Vector<Personne>();
		
		Universite cs = new CS();
		for (int i=0; i<50; i++) {
			Personne p = cs.former(new Etudiant());
			people.add(p);
		}
	}
}  