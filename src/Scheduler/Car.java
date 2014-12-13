package Scheduler;

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
		
		
		System.out.println(" dobralem glowne miasto, jej nr= " + nr);
	
		System.out.println("nr= " +nr + "  load = " + load + "  maxPr= " +maxPr+ " state= " + vector.elementAt(3).getState()+" cost= " +cost );
		vector.elementAt(indexMax).setState();
		System.out.println("dostarczenie nr " +vector.elementAt(indexMax).getState());
		tmp = nr;
		this.cost = graph.getPeaks().elementAt(nr).getConcretEdge(parents[nr]).getTime();
		System.out.println("cost = "+cost);
		System.out.println("indexMax = " + indexMax);
		
		if(load < this.capacity) { 
			System.out.println("wchodzimy do ifa z tym samym miastem");
			target = nr;
			System.out.println("target = " + target);
			for(int i = 0; i < vector.size(); i++) { //iteruje po zestawie paczek
				System.out.println("wchodzimy do fora po raz i-ty "+ i);// sprawdzam czy do miasta "nr" sa jeszcze jakies paczki do dostraczenia
				 if(vector.elementAt(i).getTarget() == target && load<this.capacity) {
						System.out.println("wchodzimy do ifa z targetem przy czym target = "+target+ " i getTarget()= " +vector.elementAt(i).getTarget()+" a i= "+i);
					    	if(vector.elementAt(i).getState() == false) {
					    		System.out.println("wchodzimy do ifa jesli false przy czym vector.elementAt(l).getState()= " + vector.elementAt(i).getState());
					    		vector.elementAt(i).setState();
					    		System.out.println("to samo miasto " + vector.elementAt(i).getState());
					    		load++;
					    	}		//this.cost = cost + graph.getPeaks().elementAt(i).getConcretEdge(parents[target]).getTime();
						}
			}
			if(load < this.capacity) {
				for(int l = 0; l < vector.size(); l++) {
					System.out.println("wchodzimy do fora po raz l-ty "+ l);
					if(parents[tmp] != -1 && load < this.capacity){ //jesli zostalo miejsca sprawdzam czy moge dostarczyc paczke gdzies "po drodze"
						System.out.println("wchodzimy do ifa z parentami");
						previousParent = parents[tmp];
						System.out.println("Pp = " + previousParent+ " parents[tmp] = "+ parents[tmp] + " tmp= "+tmp);
							for(int k = 0; k < tmpParents.length; k++) {
								if(vector.elementAt(l).getTarget() == tmpParents[k] && load < this.capacity) {
									System.out.println("wchodzimy do ifa przy czym gettarget= "+vector.elementAt(l).getTarget());
									if(vector.elementAt(l).getState() == false) {
										load++;
										vector.elementAt(l).setState();
										System.out.println("miasto rodzic");
										//this.cost = cost + graph.getPeaks().elementAt(i).getConcretEdge(parents[tmp]).getTime();
									}
								}
								tmp = previousParent; 
							}
					}
				}
			}
		} 
		System.out.println("nr= " +nr + "  load = " + load + "  maxPr= " +maxPr+ " state= " + vector.elementAt(3).getState()+" cost= " +cost );
	}
	
	public int getCost() {
		return cost;
	}
	
	
}
