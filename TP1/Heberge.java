public class Heberge {
	
	public Heberge(Personne p1, Personne p2) throws RelationException {
		if (!(p1 instanceof Etudiant && p2 instanceof Etudiant)) {
			throw new RelationException("Hosting must be held between a student and a student");
		}
	}

}
