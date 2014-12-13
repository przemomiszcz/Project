package Scheduler;


import Silngletons.Graph;

public class Scheduler {
	private int[] parents;
	private Graph graph;
			
	public Scheduler(int[] parents, Graph graph) {
		this.parents = parents;
		this.graph = graph; 
	}
	
	public int getParent(int i) {
		return parents[i];
	}
}
