import java.util.Vector;


public class Peak {
	private int nr;
	private String name;
	private Vector<Edge> vector = new Vector<>(); 
	
	public Peak(int nr, String name) {
		this.nr =nr;
		this.name = name;
	}
	
	public int getNr() {
		return nr;
	}
	
	public String getName() {
		return name;
	}
	
	public void addEdge(Edge e) {
		vector.addElement(e);
	}
	
	@Override
	public String toString() {
		return "nr miasta :" + nr + " nazwa miasta " + name;
	}
}
