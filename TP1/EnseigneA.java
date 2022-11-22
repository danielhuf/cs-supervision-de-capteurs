public class EnseigneA {

	public EnseigneA(Personne p1, Personne p2) throws RelationException {
		if (!(p1 instanceof Enseignant && p2 instanceof Etudiant)) {
			throw new RelationException("Teaching must be held between a teacher and a student");
		}
	}
}
