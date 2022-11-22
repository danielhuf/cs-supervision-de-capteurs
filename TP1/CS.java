import java.util.Random;

public class CS implements Universite{
	
	public Personne former(Etudiant e) {
      	String[] arr={"En", "In", "Et", "Et", "Et", "Et", "Et", "Et", "Et", "Et"};
      	
      	Random r=new Random();        
      	int randomNumber=r.nextInt(arr.length);
      	if (arr[randomNumber].equals("Et")) {
      		return e;
      	}
      	else if (arr[randomNumber].equals("En")) {
      		return (Enseignant)e;
      	}
      	else {
      		return (Ingenieur)e;
      	}
	}
}
