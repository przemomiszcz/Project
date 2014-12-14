package Scheduler;

import java.awt.List;
import java.util.Vector;

import Silngletons.Graph;
import Silngletons.Order;
import Parts.Package;
import Parts.Peak;


public class Car extends Thread {
	private int id;
	private int capacity;
	private Vector<Package> vector;
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
		int maxPr  = 0; //najwieksszy priorytet
		int nr = 0; // numer miasta o paczce z najwiekszym priorytetem
		int load =0; // ilosc zaladowanych do samochodu paczek
		int target = 0; // robocza zmienna dla "nr"
		int tmp=0; // robocza zmienna szewrokiego zastosowania ;)
		int previousParent = 0; //indeks poprzednio rozpatrywanego rodzica
		int indexMax = 0; //indeks paczki o maksymalnym priorytecie
		int[] tmpParents = new int[parents.length]; //robocza tablica poprzednikow miasta "nr"
		int singleTmp = 0; // robocza zmienna zawierajaca aktualnie rozpatrywanego poprzednika 
		int j = 0; // robocza zmienna szerokiego zastosowania 2
		Vector<Integer> passed = new Vector<Integer>();
		int a = 0;
		
		for(int i =0; i<tmpParents.length; i++) {
			tmpParents[i] = 666;
		}
		
		for(int i = 0; i < vector.size(); i++) { //szukam paczki o najwiekszym priorytecie
			if(vector.elementAt(i).getState() == false) {
				if(vector.elementAt(i).getPriority() > maxPr) {
					maxPr = vector.elementAt(i).getPriority();
					nr = vector.elementAt(i).getTarget();
					load = 1;
					indexMax = i;
				}
			}	
		}
		singleTmp = nr;
		while(singleTmp != -1) { //tworze tablice poprzednikow
			tmpParents[j] = parents[singleTmp];
			singleTmp = parents[singleTmp];
			j++;
		}
		
		while(a < tmpParents.length-1) {
			if(tmpParents[a] != -1 && tmpParents[a] != 666) {
			passed.add(tmp, tmpParents[a]);
			tmp++;
			}
			a++;
		}
		
		//System.out.println(" dobralem glowne miasto, jej nr= " + nr);
	
		//System.out.println("nr= " +nr + "  load = " + load + "  maxPr= " +maxPr+ " state= " + vector.elementAt(3).getState()+" cost= " +cost );
		vector.elementAt(indexMax).setState();
		//System.out.println("dostarczenie nr " +vector.elementAt(indexMax).getState());
		tmp = nr;
		this.cost = graph.getPeaks().elementAt(nr).getConcretEdge(parents[nr]).getTime();
		System.out.println("cost = "+cost);
		//System.out.println("indexMax = " + indexMax);
		
		if(load < this.capacity) { 
			//System.out.println("wchodzimy do ifa z tym samym miastem");
			target = nr;
			//System.out.println("target = " + target);
			for(int i = 0; i < vector.size(); i++) { //iteruje po zestawie paczek
				//System.out.println("wchodzimy do fora po raz i-ty "+ i);// sprawdzam czy do miasta "nr" sa jeszcze jakies paczki do dostraczenia
				 if(vector.elementAt(i).getTarget() == target && load<this.capacity) {
						//System.out.println("wchodzimy do ifa z targetem przy czym target = "+target+ " i getTarget()= " +vector.elementAt(i).getTarget()+" a i= "+i);
					    	if(vector.elementAt(i).getState() == false) {
					    		//System.out.println("wchodzimy do ifa jesli false przy czym vector.elementAt(l).getState()= " + vector.elementAt(i).getState());
					    		vector.elementAt(i).setState();
					    		//System.out.println("to samo miasto " + vector.elementAt(i).getState());
					    		load++;
					    		this.cost = cost + graph.getPeaks().elementAt(i).getConcretEdge(parents[target]).getTime();
						}
				}
			}
			
		}
		if(load < this.capacity) {
			for(int i = 0; i < vector.size(); i++) {
				//System.out.println("wchodzimy do fora PARENTOW po raz i-ty "+ i);
				for(j=0; j < tmpParents.length; j++) {
					//System.out.println("wchodzimy do fora po raz j-ty "+ j);
					if(load < this.capacity && vector.elementAt(i).getTarget() == tmpParents[j]) {
						//System.out.println("wchodzimy do ifa przy czym gettarget= "+vector.elementAt(i).getTarget()+ " a tmpparents[j]= "+tmpParents[j]);
						vector.elementAt(i).setState();
						load++; 
						//System.out.println("jeden z parentow( "+tmpParents[j]+ " ) ma dostarczenie "+vector.elementAt(i).getState());
					}
				}
			}
		} 
		//System.out.println("nr= " +nr + "  load = " + load + "  maxPr= " +maxPr+ " state= " + vector.elementAt(3).getState()+" cost= " +cost );
	}
	//graph.getPeaks().elementAt(c).getEdges().elementAt(d).getTarget()
	public void countCost(int nr, Vector<Integer> passed) {
		int cost=0;
		if(cost == 0)
			for(int e =0; e<passed.size(); e++) {
				for(int c=0; c < graph.countTargets(); c++) {
					for(int d =0; d < graph.countConcretTargets(nr); d++) {
						if(nr == graph.getPeaks().elementAt(nr).getNr() && passed.elementAt(e) == graph.getPeaks().elementAt(c).getConcretEdge(d).getTarget()) {
						
						} else if(nr == graph.getPeaks().elementAt(c).getConcretEdge(d).getTarget() && passed.elementAt(e) == graph.getPeaks().elementAt(nr).getNr()) {
						
						}
					}
				}
			}	
		}
		for(int a =0; a<=passed.size(); a++) {
			for(int b =0; b <graph.countTargets(); b++) {
				
					if(passed.elementAt(a) == graph.getPeaks().elementAt())
				
			}
		}
	}
	
	
}
