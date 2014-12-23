
package Scheduler;

import java.util.Vector;

import Singletons.Graf;
import Parts.Package;


public class Car extends Thread {
	private Visual visual;
	private int id;
	private int capacity;
	private Vector<Package> vector;
	//private int cost;
	private int[] parents;
	private Graf graf;
	private Vector<Integer> properCosts = new Vector<>();
	private int driven;
	private int delivered;
		
	public Car(int id, int capacity, Vector<Package> vector, int[] parents, Graf graf, Visual visual) {
		this.capacity = capacity;
		this.id = id;
		this.vector = vector;
		//this.cost =0;
		this.parents = parents;
		this.graf = graf;
		this.driven =0;
		this.visual = visual;
		this.delivered  =0;
	}
	
	@Override
	public void run() {
		int maxPr  = 0; 											//najwieksszy priorytet
		int nr = 0; 												// numer miasta o paczce z najwiekszym priorytetem
		int load =0; 												// ilosc zaladowanych do samochodu paczek
		int tmp=0; 													// robocza zmienna szewrokiego zastosowania ;)
		int indexMax = 0; 											//indeks paczki o maksymalnym priorytecie
		int[] tmpParents = new int[parents.length]; 				//robocza tablica poprzednikow miasta "nr"
		int singleTmp = 0; 											// robocza zmienna zawierajaca aktualnie rozpatrywanego poprzednika 
		int j = 0; 													// robocza zmienna szerokiego zastosowania 2
		Vector<Integer> passed = new Vector<Integer>();				//vector poprzednikow
		int a = 0;													// zmienna robocza szerokiego zastosowania 3
		int tmpDriven=0;											// zmienna przechowuje przejechana odleglosc podczas jednego "kursu"
		int toSleep =0;												// zmienna przechowuje czas drogi powrotnej samochodu do bazy
		
		properCosts.clear();
		
		for(int i =0; i<tmpParents.length; i++) {
			tmpParents[i] = 666;
		}
		

		synchronized(this) {
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
		
		
		
		vector.elementAt(indexMax).setState();
		tmp = nr;
		
		
		if(load < this.capacity) { //szukamy czy jest jakas paczka w tym samym miescie
			for(int i = 0; i < vector.size(); i++) { //iteruje po zestawie paczek
				 if(vector.elementAt(i).getTarget() == nr && load<this.capacity) {
					if(vector.elementAt(i).getState() == false) {
					    vector.elementAt(i).setState();
					    printStart(vector.elementAt(i));
					    load++;
					}
				}
			}
			
		}
		if(load < this.capacity) { // szukamy paczki w miastach-poprzednikach
			for(int i = 0; i < vector.size(); i++) {
				for(j=0; j < tmpParents.length; j++) {
					if(load < this.capacity && vector.elementAt(i).getTarget() == tmpParents[j]) {
						vector.elementAt(i).setState();
						load++;
						printStart(vector.elementAt(i));
						System.out.println("load= "+load);
					}
				}
			}
		}
		
		countCost(nr, passed);
		visual.updateGraph(passed, nr);
		delivered = delivered + load;
		}										//koniec bloku synchronizowanego
		
		for(int i =0; i < properCosts.size(); i++) {
			tmpDriven = tmpDriven + properCosts.elementAt(i);
				try {
					Thread.sleep(properCosts.elementAt(i)*50);
					//tutaj wypisanie dostarczenia
				} catch(InterruptedException e) {
					e.printStackTrace();
				}
			
			}
		
			try {
				
				System.out.println("zasypiam na "+tmpDriven);
				Thread.sleep(tmpDriven*50);
				System.out.println("Samochod nr " + id+" wraca do miasta-bazy");
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
			
	
		
		
		this.driven = driven + tmpDriven*2;
		
		
	}
	
	public Vector<Integer> countCost(int nr, Vector<Integer> passed) { //tworze vector kosztow przejazdu, od bazy do miasta glownego
		Vector<Integer> koszty = new Vector<>();
		koszty.add(0);
		
		
			for(int i =0; i<passed.size(); i++) { //obliczam koszt z miasta glownego do poprzednika
				for(int k=0; k < graf.countPeaks(); k++) {
					for(int l =0; l <= graf.countConcretTargets(nr)+1; l++) {
						if(graf.getPeaks().elementAt(k).getConcretEdge(l) != null && koszty.elementAt(0) == 0) {
							if(nr == graf.getPeaks().elementAt(nr).getNr() && passed.elementAt(i) == graf.getPeaks().elementAt(k).getConcretEdge(l).getTarget()) {
								koszty.add(0, graf.getPeaks().elementAt(nr).getConcretEdge(l).getTime());
							}
						} 
					}
				}
			}	
		
		for(int m = 0; m < passed.size()-1; m++) { //obliczam koszty reszty poprzednikow
			for(int n =0; n < graf.countPeaks(); n++) {
				for(int o =0; o < graf.countConcretTargets(m); o++) {
					if(passed.elementAt(m) == graf.getPeaks().elementAt(n).getNr() && passed.elementAt(m+1) == graf.getPeaks().elementAt(n).getEdges().elementAt(o).getTarget()) {
						koszty.add(m+1, graf.getPeaks().elementAt(passed.elementAt(m)).getEdges().elementAt(o).getTime()); 
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
	
	public int getDelivered() {
		return delivered;
	}
	
}

