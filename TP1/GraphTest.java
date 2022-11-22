import static org.junit.jupiter.api.Assertions.*;
import java.util.Collection;
import java.util.Vector;

class GraphTest { 
	Node a = new Node(); 
	Node b = new Node(); 
	Node c = new Node(); 

	Edge ab = new Edge(a,b); 
	Edge bc = new Edge(c,b); 
	Edge ca = new Edge(a,c); 

	Graph getGraph() { 
		
		Vector<Node> nv = new Vector<Node>(); 
		nv.add(a); 
		nv.add(b); 
		nv.add(c); 
		
		Vector<Edge> ne = new Vector<Edge>(); 
		ne.add(ab); 
		ne.add(bc); 
		ne.add(ca); 
		
		Graph g = new Graph(nv, ne); 
		return g; 
	}	
	
	@org.junit.jupiter.api.Test
	void getEdges() {
		System.out.println("Testing getEdges() :)"); 
		Graph g = getGraph(); 
		System.out.println("G: " + g); 
		Collection<Edge> out = g.getEdges(a); 
		assertFalse(out.size() == 2); 
		assertTrue(out.contains(ab)); 
	} 
}