package Scheduler;

import java.util.Vector;

import Silngletons.Graph;
import Silngletons.Order;
import Parts.Package;
import Parts.Peak;


public class Car extends Thread {
	private int capacity;
	private int id;
	private Vector<Package> vector = new Vector<Package>();
	private int cost;
	private int[] parents;
	private Graph graph;
	
	public Car(int id, int capacity, Vector<Package> vector, int[] parents, Graph graph) {
		this.capacity = capacity;
		this.id = id;
		this.vector = vector;
		this.cost =0;
		this.parents = parents;
		this.graph = graph;
	}
	
	@Override
	public void run() {
		int maxPr  = 0;
		int nr = 0;
		int load =0;
		int target = 0;
		int tmp=0;
		int previousParent = 0;
		
		for(int i = 0; i < vector.size(); i++) {
			if(vector.elementAt(i).getState() == false) {
				if(vector.elementAt(i).getPriority() > maxPr) {
					maxPr = vector.elementAt(i).getPriority();
					nr = i;
					load = 1;
				}
			}
		}
		
		vector.elementAt(nr).setState();
		tmp = nr;
		this.cost = graph.getPeaks().elementAt(nr).getConcretEdge(parents[nr]).getTime();
		
		if(load < this.capacity) {
			target = vector.elementAt(nr).getTarget();
			for(int i = 0; i < vector.size(); i++) {
				if(vector.elementAt(i).getTarget() == target) {
					if(vector.elementAt(i).getState() == false)
						vector.elementAt(i).setState();
						load++;
						this.cost = cost + graph.getPeaks().elementAt(i).getConcretEdge(parents[target]).getTime();
				} else if(parents[tmp] != -1){
					previousParent = parents[tmp];
					if(vector.elementAt(i).getTarget() == parents[tmp]) {
						if(vector.elementAt(i).getState() == false) {
							load++;
							vector.elementAt(i).setState();
							this.cost = cost + graph.getPeaks().elementAt(i).getConcretEdge(parents[tmp]).getTime();
						}
						
					}
					tmp = previousParent;
				}
			}
		} 
		
	}
	
	
}
