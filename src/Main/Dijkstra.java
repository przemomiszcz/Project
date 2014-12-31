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
	
	
	public Dijkstra(Vector<Peak> dupa, int source){
		for(int i =0; i < dupa.size(); i++) {
			Q.add(dupa.elementAt(i));
		}
		costs = new int[Q.size()];
		parents = new int[Q.size()];
		this.source = source;
		//this.S = dupa;
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
	    //Q.removeElementAt(source);
		
		//System.out.println(Q);
		//System.out.println(S);	
		
		while( !(Q.isEmpty())) {	
			/*for(int i =0; i < costs.length; i++) {
				System.out.println(costs[i]);
				System.out.println(parents[i]);
				System.out.println("----------------");
			}*/
			//System.out.println("weszlem");
			tmp = searchMin(costs);
			//System.out.println("tmp= "+tmp);
			
			//Q.remove(tmp);
			searchNeighbours(S.elementAt(tmp));
			//System.out.println("NOWE tmp= "+tmp);
			dupa.add(tmp);
			for(int i=0; i< Q.size(); i++) {
				if(Q.elementAt(i).getNr() == tmp){
					Q.remove(i);
				}
			}
			//System.out.println("Q= "+Q);
			//System.out.println("usunelem");
		}
		/*for(int i =0; i < costs.length; i++) {
			System.out.println("do miasta: "+i);
			System.out.println(parents[i]);
			System.out.println("-------");
			System.out.println(costs[i]);
		}*/
		
	}
	
	
	
	public int searchMin(int[] costs) {
		int min=660;
		int where = 0;
			for(int i = 0; i < costs.length; i++) {
				//System.out.println(" MIN: i= "+i);
				if(!(dupa.contains(i))) {
					if(costs[i] < min) {
						//System.out.println("IF W MIN");
						where = i;
						min = costs[i];
					}	
				}
		}
			//System.out.println("min = "+min+" where= "+where);
		return where;
	}
	
	public void searchNeighbours(Peak p) {
		for(int i =0; i < p.getLength(); i++) {
			//System.out.println("NEIGHBOURS: i= "+i);
			for(int j = 0; j<Q.size(); j++) {
				//System.out.println("j= "+j);
				for(int k =0; k <Q.get(j).getLength(); k++) {
					if(p.getEdges().elementAt(i).getTarget() == Q.get(j).getNr()) {
						//System.out.println("p.getEdges().elementAt(i).getTarget()= "+p.getEdges().elementAt(i).getTarget()+ " Q.get(j).getNr()= "+Q.get(j).getNr());
						if(costs[i] > costs[p.getNr()]+p.getConcretEdge(Q.elementAt(j).getNr()).getTime()) {
							//System.out.println("if: i= "+ i+  " time= "+p.getConcretEdge(Q.elementAt(j).getNr()).getTime());
							costs[j] = costs[p.getNr()]+p.getConcretEdge(Q.elementAt(j).getNr()).getTime();
							parents[j] = p.getNr();
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
