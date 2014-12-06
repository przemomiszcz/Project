package Parts;

import java.util.Vector;


public class Peak {
	private int nr;
	private String name;
	private Vector<Edge> edges;
	
	public Peak(int nr, String name) {
		this.nr =nr;
		this.name = name;
		edges  = new Vector<>(); 
	}
	
	public int getNr() {
		return nr;
	}
	
	public String getName() {
		return name;
	}
	
	public void addEdge(Edge e) {
		edges.addElement(e);
	}
	
	@Override
	public String toString() {
		return "nr miasta :" + nr + " nazwa miasta " + name + edges;
	}
	
	public Edge getEdge(int index) {
		return edges.elementAt(index);
	}
	
	public int getLength() {
		return edges.size();
	}
	
	
}
