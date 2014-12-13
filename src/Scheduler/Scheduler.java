package Scheduler;

public class Scheduler {
	private int[] parents;
	
	public Scheduler(int[] parents) {
		this.parents = parents;
	}
	
	public int getParent(int i) {
		return parents[i];
	}
}
