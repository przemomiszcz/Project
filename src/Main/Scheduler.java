package Main;

public class Scheduler {
	private int[] parents;
	
	public Scheduler(int[] parents) {
		this.parents = parents;
	}
	
	public int[] getPar() {
		return parents;
	}
}
