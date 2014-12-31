
package Scheduler;

import java.util.Vector;

import Singletons.Graf;
import Parts.Package;


public class Car extends Thread {
	private int id;
	private int capacity;
	private Vector<Package> vector;
	private int[] parents;
	private Graf graf;
	private Vector<Integer> properCosts = new Vector<>();
	private int driven;
	private int delivered;
		
	public Car(int id, int capacity, Vector<Package> vector, int[] parents, Graf graf) {
		this.capacity = capacity;
		this.id = id;
		this.vector = vector;
		this.parents = parents;
		this.graf = graf;
		this.driven =0;
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
		int thisTimeDriven=0;										// zmienna przechowuje przejechana odleglosc podczas jednego "kursu"
		Vector<Integer> inProgress = new Vector<>();				// vextor przechowuje cele dostarczanych w jednej turze paczek
		Vector<Integer> orderedPassed = new Vector<>();				// vector przechowuje mijane miasta (w kolejnosci mijania)
 		Vector<Package> thisCourse = new Vector<>();				// vector przechowuje paczki pobrane w tej turze
 		int toSleep =0;												// zmienna przechowuje czas potrzebny na powrot samochodu do miasta-bazy
 		
 		
 		delivered = 0;
		properCosts.clear();
		
		for(int i =0; i<tmpParents.length; i++) {
			tmpParents[i] = 666;
		}
		

		//synchronized(this) {														// poczatek bloku synchronizowanego
		Visual visual = new Visual(vector, id);
		
		for(int i = 0; i < vector.size(); i++) { 									//szukam paczki o najwiekszym priorytecie
			if(vector.elementAt(i).getState() == false) {
				if(vector.elementAt(i).getPriority() > maxPr) {
					maxPr = vector.elementAt(i).getPriority();
					nr = vector.elementAt(i).getTarget();
					load = 1;
					indexMax = i;
				}
			}	
		}
		
		thisCourse.addElement(vector.elementAt(indexMax));
		inProgress.add(0, vector.elementAt(indexMax).getTarget());					//wpisuje cel glownej paczki
		printStart(vector.elementAt(indexMax));										
		singleTmp = nr;
		
		while(singleTmp != -1) { 													//tworze tablice poprzednikow
			tmpParents[j] = parents[singleTmp];
			singleTmp = parents[singleTmp];
			j++;
		}
		
		
		while(a < tmpParents.length-1) { 											//tworze uporzadkowany vector poprzednikow
			if(tmpParents[a] != -1 && tmpParents[a] != 666) {
			passed.add(tmp, tmpParents[a]);
			tmp++;
			}
			a++;
		}
		
		vector.elementAt(indexMax).setState();										// ustawiam status paczki na "dostarczona"
		
		
		if(load < this.capacity) { 													//szukamy czy jest jakas paczka w tym samym miescie
			for(int i = 0; i < vector.size(); i++) { 								//iteruje po zestawie paczek
				 if(vector.elementAt(i).getTarget() == nr && load<this.capacity) {
					if(vector.elementAt(i).getState() == false) {
					    vector.elementAt(i).setState();
					    printStart(vector.elementAt(i));
					    load++;
					    inProgress.addElement(vector.elementAt(i).getTarget());
					    thisCourse.addElement(vector.elementAt(i));
					}
				}
			}
			
		}
		if(load < this.capacity) { 													// szukamy paczki w miastach-poprzednikach
			for(int i = 0; i < vector.size(); i++) {
				for(j=0; j < tmpParents.length; j++) {
					if(load < this.capacity && vector.elementAt(i).getTarget() == tmpParents[j]) {
						if(vector.elementAt(i).getState() == false) {
							vector.elementAt(i).setState();
							load++;
							printStart(vector.elementAt(i));
							inProgress.addElement(vector.elementAt(i).getTarget());
							thisCourse.addElement(vector.elementAt(i));
						}
					}
				}
			}
		}
		countCost(nr, passed);
		visual.updateGraph(passed, nr);
		delivered = delivered + load;
		int  tmpDriven = driven;
		//}																			//koniec bloku synchronizowanego
		orderedPassed = orderingPassed(nr, passed);
		
		
		for(int i =0; i < properCosts.size(); i++) {
			thisTimeDriven = thisTimeDriven + properCosts.elementAt(i);
				try {
					Thread.sleep(properCosts.elementAt(i));
					for(int y =0; y <= orderedPassed.size(); y++) {
						for(int t =0; t < inProgress.size(); t++) {
								if(inProgress.elementAt(t) == orderedPassed.elementAt(y)) {
									for(int n =0; n < thisCourse.size(); n++) {
										if(thisCourse.elementAt(n).getTarget() == orderedPassed.elementAt(y)) {
											for(int m = 0; m < y; m++) {
												tmpDriven = tmpDriven + properCosts.elementAt(m);
											}
											System.out.println(tmpDriven+" dostarczono paczke nr: "+thisCourse.elementAt(n).getNr()+" do"+graf.getPeaks().elementAt(thisCourse.elementAt(n).getTarget()));
											inProgress.removeElementAt(t);
											tmpDriven =0;
										}
									}
								}
							}
					} 
				} catch(InterruptedException e) {
					e.printStackTrace();
				}
			
			}
		
		/*for(int h: properCosts) {													// sumuje czas potrzebny na powrot samochodu
			toSleep = toSleep + properCosts.elementAt(h);
		}*/
		
		for(int h =0; h< properCosts.size(); h++) {									// sumuje czas potrzebny na powrot samochodu
			toSleep = toSleep + properCosts.elementAt(h);
		}
		
			try {
				System.out.println("Samochod nr " + id+" wraca do bazy");
				Thread.sleep(toSleep);
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
			
	
		this.driven = driven + thisTimeDriven*2;
		
	}
	
	public Vector<Integer> countCost(int nr, Vector<Integer> passed) { 				//tworze vector kosztow przejazdu, od bazy do miasta glownego
		Vector<Integer> koszty = new Vector<>();
		koszty.add(0);
		
										
				for(int k=0; k < graf.countPeaks(); k++) {							//obliczam koszt z miasta glownego do poprzednika
					for(int l =0; l <= graf.countConcretTargets(nr)+2; l++) {
						if(graf.getPeaks().elementAt(k).getConcretEdge(l) != null && koszty.elementAt(0) == 0) {
							if(nr == k && passed.elementAt(0) == l) {
								koszty.add(0, graf.getPeaks().elementAt(k).getConcretEdge(l).getTime());
							}
						} 
					}
				//}
			}	
			
		for(int m = 0; m < passed.size()-1; m++) {									 	//obliczam koszty reszty poprzednikow
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
	
	
	public void changeVector(Vector<Integer> koszty) { 									//ustawiam koszty w kolejnosci od bazy do miasta glownego
		int j =koszty.size() -2;
		for(int i =0; i < koszty.size()-1; i++) {
				properCosts.add(i, koszty.elementAt(j));
			j--;
		}
	}
	
	public void printStart(Package p) { 												//wypisuje komunikat o pobraniu paczki
		System.out.println(driven +" Pobrano paczke nr: " +p.getNr()+ " do"+graf.getPeaks().elementAt(p.getTarget()));
	}
	
	
	public Vector<Integer> orderingPassed(int nr, Vector<Integer> passed) {  			//ustawiam miasta-poprzednikow w porzadku "mijania"
		Vector<Integer> noName = new Vector<>();
		int j =passed.size()-1;
		while(j >= 0) {
			for(int i=0; i<= passed.size()-1;i++) {
				if(!(noName.contains(passed.elementAt(j)))) {
					noName.add(passed.elementAt(j));
				}
			}
			j--;
		}
		noName.addElement(nr);
		return noName;
	}
	
	public Vector<Integer> getCosts() {
		return this.properCosts;
	}
	
	public int getDelivered() {
		return delivered;
	}
}

