package Parts;

import java.util.Vector;


public class Peak {
	private int nr;					//nr miasta	
	private String name;			// nazwa miasta
	private Vector<Edge> edges;		// vector polaczen wychodzacych z tego miasta
	
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
		return  " nazwa miasta: " + name;
		//+ edges"nr miasta :" + nr +;
	}
	
	public Edge getEdge(int index) {
		return edges.elementAt(index);
	}
	
	public int getLength() {
		return edges.size();
	}
	
	public Vector<Edge> getEdges() {
		return edges;
	}

	public Edge getConcretEdge(int source) {  //zwraca edge o konkretnym zrodle/celu
		for(Edge e : edges) {
			if(e.getTarget() == source) {
				return e;
			}
		}
		return null;
	}
	
	
}
