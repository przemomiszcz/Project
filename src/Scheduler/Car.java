
package Scheduler;

import java.util.Vector;

import Singletons.Graf;
import Singletons.Order;
import Parts.Package;
import Parts.Peak;


public class Car extends Thread {
	private int id;
	private int capacity;
	private Vector<Package> vector;
	private int cost;
	private int[] parents;
	private Graf graf;
	private Vector<Integer> properCosts = new Vector<>();
	private int driven;
		
	public Car(int id, int capacity, Vector<Package> vector, int[] parents, Graf graf) {
		this.capacity = capacity;
		this.id = id;
		this.vector = vector;
		this.cost =0;
		this.parents = parents;
		this.graf = graf;
		this.driven =0;

	}
	
	@Override
	public void run() {
		int maxPr  = 0; 											//najwieksszy priorytet
		int nr = 0; 												// numer miasta o paczce z najwiekszym priorytetem
		int load =0; 												// ilosc zaladowanych do samochodu paczek
		int target = 0; 											// robocza zmienna dla "nr"
		int tmp=0; 													// robocza zmienna szewrokiego zastosowania ;)
		int indexMax = 0; 											//indeks paczki o maksymalnym priorytecie
		int[] tmpParents = new int[parents.length]; 				//robocza tablica poprzednikow miasta "nr"
		int singleTmp = 0; 											// robocza zmienna zawierajaca aktualnie rozpatrywanego poprzednika 
		int j = 0; 													// robocza zmienna szerokiego zastosowania 2
		Vector<Integer> passed = new Vector<Integer>();				//vector poprzednikow
		int a = 0;													// zmienna robocza szerokiego zastosowania 3
		
		synchronized(this) {
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
		printStart(vector.elementAt(indexMax));
		singleTmp = nr;
		while(singleTmp != -1) { //tworze tablice poprzednikow
			tmpParents[j] = parents[singleTmp];
			singleTmp = parents[singleTmp];
			j++;
		}
		
		while(a < tmpParents.length-1) { //tworze uporzadkowany vector poprzednikow
			if(tmpParents[a] != -1 && tmpParents[a] != 666) {
			passed.add(tmp, tmpParents[a]);
			tmp++;
			}
			a++;
		}
		
		
		//System.out.println(" dobralem glowne miasto, jej nr= " + nr);
	
		//System.out.println("nr= " +nr + "  load = " + load + "  maxPr= " +maxPr+ " state= " + vector.elementAt(3).getState()+" cost= " +cost );
		vector.elementAt(indexMax).setState();
		tmp = nr;
		this.cost = graf.getPeaks().elementAt(nr).getConcretEdge(parents[nr]).getTime();
		//System.out.println("load= "+load+ " capacity= "+capacity);
		
		if(load < this.capacity) { //szukamy czy jest jakas paczka w tym samym miescie
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
					    		printStart(vector.elementAt(i));
					    		load++;
					    		////////////////////////////////////////////////this.cost = cost + graph.getPeaks().elementAt(i).getConcretEdge(parents[target]).getTime();
						}
				}
			}
			
		}
		if(load < this.capacity) { // szukamy paczki w miastach-poprzednikach
			for(int i = 0; i < vector.size(); i++) {
				//System.out.println("wchodzimy do fora PARENTOW po raz i-ty "+ i);
				for(j=0; j < tmpParents.length; j++) {
					//System.out.println("wchodzimy do fora po raz j-ty "+ j);
					if(load < this.capacity && vector.elementAt(i).getTarget() == tmpParents[j]) {
						//System.out.println("wchodzimy do ifa przy czym gettarget= "+vector.elementAt(i).getTarget()+ " a tmpparents[j]= "+tmpParents[j]);
						vector.elementAt(i).setState();
						load++;
						printStart(vector.elementAt(i));
						System.out.println("load= "+load);
					}
				}
			}
		}
		Vector<Integer> v = countCost(nr, passed);
		}
		for(int i =0; i < properCosts.size(); i++) {
			driven = driven + properCosts.elementAt(i);
			try {
				Thread.sleep(properCosts.elementAt(i)*50);
				//tutaj wypisanie dostarczenia
				System.out.println("driven= "+driven);
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
		//System.out.println("cost finalny= "+properCosts);
		//System.out.println("nr= " +nr + "  load = " + load + "  maxPr= " +maxPr+ " state= " + vector.elementAt(3).getState()+" cost= " +cost );
	}
	
	public Vector<Integer> countCost(int nr, Vector<Integer> passed) { //tworze vector kosztow przejazdu, od bazy do miasta glownego
		Vector<Integer> koszty = new Vector<>();
		koszty.add(0);
		
		
			for(int i =0; i<passed.size(); i++) { //obliczam koszt z miasta glownego do poprzednika
				//System.out.println("wchodzimy do fora po raz i-ty "+i);
				for(int k=0; k < graf.countPeaks(); k++) {
					//System.out.println("wchodzimy do fora po raz k-ty "+k);
					for(int l =0; l <= graf.countConcretTargets(nr)+1; l++) {
						//System.out.println("wchodzimy do fora po raz l-ty "+l);
						//System.out.println("graph.counssas= "+graph.countConcretTargets(nr));
						if(graf.getPeaks().elementAt(k).getConcretEdge(l) != null && koszty.elementAt(0) == 0) {
							if(nr == graf.getPeaks().elementAt(nr).getNr() && passed.elementAt(i) == graf.getPeaks().elementAt(k).getConcretEdge(l).getTarget()) {
								//System.out.println("wchodzimy do ifa przy czym nr= "+nr+ " i= "+i+" k= "+k+" l= "+l+" graph.getPeaks().elementAt(k).getConcretEdge(l).getTarget()= "+graph.getPeaks().elementAt(k).getConcretEdge(l).getTarget());
								koszty.add(0, graf.getPeaks().elementAt(nr).getConcretEdge(l).getTime());
								//System.out.println("koszt obecny= "+koszty.elementAt(0));
							}
						} 
					}
				}
			}	
		
		for(int m = 0; m < passed.size()-1; m++) { //obliczam koszty reszty poprzednikow
			//System.out.println("wchodzimy do fora po raz m-ty "+m);
			for(int n =0; n < graf.countPeaks(); n++) {
				//System.out.println("wchodzimy do fora po raz n-ty "+n);
				for(int o =0; o < graf.countConcretTargets(m); o++) {
					//System.out.println("wchodzimy do fora po raz o-ty "+o);
					if(passed.elementAt(m) == graf.getPeaks().elementAt(n).getNr() && passed.elementAt(m+1) == graf.getPeaks().elementAt(n).getEdges().elementAt(o).getTarget()) {
						//System.out.println("wchodzimy do ifa przy czym m= "+m+ " n= "+n+" o= "+o);
						koszty.add(m+1, graf.getPeaks().elementAt(passed.elementAt(m)).getEdges().elementAt(o).getTime()); 
						//System.out.println("koszt obecny= "+koszt);
					}	
				}	
			}
		}
		changeVector(koszty);
		return koszty;
		
	}
	
	public Vector<Integer> getCosts() {
		return this.properCosts;
	}
	
	public void changeVector(Vector<Integer> koszty) { //ustawiam koszty w kolejnosci od bazy do miasta glownego
		int j =koszty.size() -2;
		for(int i =0; i < koszty.size()-1; i++) {
				properCosts.add(i, koszty.elementAt(j));
			j--;
		}
	}
	
	public void printStart(Package p) {
		System.out.println("Pobrano paczke nr: " +p.getNr()+ " do"+graf.getPeaks().elementAt(p.getTarget()));
	}
	
	public void printDelivery(Package p) {
		System.out.println("Dostarczono paczke nr: " +p.getNr()+ " do"+graf.getPeaks().elementAt(p.getTarget()));
	}
	
	public int getDriven() {
		return driven;
	}
}

