package Main;

import java.util.Vector;

import Parts.Peak;

public class Dijkstra {
	private Vector<Integer> S = new Vector<>(); 
	private Vector<Peak> Q;
	private int[] costs;
	private int[] parents;
	private int source;
	private int tmp;
	
	
	public Dijkstra(Vector<Peak> Q, int source){
		this.Q = Q;
		costs = new int[Q.size()];
		parents = new int[Q.size()];
		this.source = source;
	}
	
	
	
	public void start() {
		
		for(int i =0; i < costs.length; i++) {
			costs[i] = 666;
			parents[i] = -1;
		}
		
		tmp = source;
		costs[source] = 0;
		
		while( S.size() < Q.size() *2 ) {	
			//if(tmp == source) {
			//	S.add(tmp);
			//	lomzing(tmp);
			//	tmp = search(Q.elementAt(tmp));
			//} else {
				S.add(tmp);
				lomzing(tmp);
				tmp = search(Q.elementAt(tmp));
			//}
		}
		
	}
	
	
	
	public int search(Peak e) {
		int min = e.getEdge(0).getTime();
		int where = e.getEdge(0).getTarget();
		for(int i = 1; i < e.getLength(); i++) {
			if(e.getEdge(i).getTime() < min) {
				min = e.getEdge(i).getTime();
				where = e.getEdge(i).getTarget();
			}
		}
		return where;
	}
	
	public void lomzing(int actual) {
		for(int i = 0; i < Q.elementAt(actual).getLength(); i++) {
			int neighbour = Q.elementAt(actual).getEdge(i).getTarget();
			if(costs[neighbour] > costs[actual] + Q.elementAt(actual).getEdge(i).getTime()){
				costs[neighbour] = costs[actual] + Q.elementAt(actual).getEdge(i).getTime();
				parents[neighbour] = actual;
			}
		}
	}
	
	public int[] getParents() {
		return parents;
	}
 }
