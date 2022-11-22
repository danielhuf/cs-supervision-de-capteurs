import java.util.Collection;
import java.util.Vector;

public class Graph {
	
	private Graph g;
	private Vector<Node> nv;
	private Vector<Edge> ne;
	
	public Graph (Vector<Node> nv, Vector<Edge> ne) {
		this.nv = nv;
		this.ne = ne;
	}
	
	public Collection<Edge> getEdges(Node n) {
		return ne;
	}
	
	public String toString() {
		String answer = "";
		for (int i = 0; i < nv.size(); i++) {
			answer += "n" + String.valueOf(i+1) + " ";
		}
	 	return answer;
	}
}
