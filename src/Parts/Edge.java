package Parts;


public class Edge {
	private int target;          //cel polaczenia
	private int time;			//koszt polaczenie
	
	public Edge(int target, int time) {
		this.target = target;
		this.time = time;
	}
	
	public int getTarget() {
		return target;
	}
	
	public int getTime() {
		return time;
	}
	
	@Override
	public String toString() {
		return "cel: " + target +"czas: " + time;
	}
}
