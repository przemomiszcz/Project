package Main;

import java.util.Vector;

import Parts.Peak;

public class Dijkstra {
	private Vector<Peak> S = new Vector<>(); 
	private Vector<Peak> Q = new Vector<>();
	private int[] costs;
	private int[] parents;
	private int source;
	private int tmp;
	private Vector<Integer> dupa = new Vector<>();
	
	
	public Dijkstra(Vector<Peak> get, int source){
		for(int i =0; i < get.size(); i++) {
			Q.add(get.elementAt(i));
		}
		costs = new int[Q.size()];
		parents = new int[Q.size()];
		this.source = source;
	}
	
	
	
	public void start() {
				
		
		for(int i =0; i < Q.size(); i++) {
			S.add(Q.elementAt(i));
		}
		for(int i =0; i < costs.length; i++) {
			costs[i] = 666;
			parents[i] = -1;
		}
		
		
		tmp = source;
		costs[source] = 0;
	    
		while( !(Q.isEmpty())) {
			tmp = searchMin(costs);
			searchNeighbours(S.elementAt(tmp));
			dupa.add(tmp);
			for(int i=0; i< Q.size(); i++) {
				if(Q.elementAt(i).getNr() == tmp){
					Q.remove(i);
				}
			}
		}
		
	}
	
	
	
	public int searchMin(int[] costs) {
		int min=660;
		int where = 0;
			for(int i = 0; i < costs.length; i++) {
				if(!(dupa.contains(i))) {
					if(costs[i] < min) {
						where = i;
						min = costs[i];
					}	
				}
		}
		return where;
	}
	
	public void searchNeighbours(Peak p) {
		for(int i =0; i < p.getLength(); i++) {
			for(int j = 0; j<Q.size(); j++) {
				for(int k =0; k <Q.get(j).getLength(); k++) {
					if(p.getEdges().elementAt(i).getTarget() == Q.get(j).getNr()) {
						if(costs[p.getEdge(i).getTarget()] > costs[p.getNr()]+p.getConcretEdge(Q.elementAt(j).getNr()).getTime()) {
							costs[p.getEdge(i).getTarget()] = costs[p.getNr()]+p.getConcretEdge(Q.elementAt(j).getNr()).getTime();
							parents[p.getEdge(i).getTarget()] = p.getNr();
						}
					}
				}
			}
				
		}
	}
	
	public int[] getParents() {
		return parents;
	}
}
