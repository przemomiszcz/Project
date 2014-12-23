package Scheduler;


import java.util.Vector;

import Parts.Package;
import Singletons.Entry;
import Singletons.Graf;

public class Scheduler { //odpowiada za uruchamianie watkow-samochodow
	private int[] parents;
	private Graf graf;
	private Car[] cars; //tablica watkow
	private Vector<Package> vector; 
	private int charge = 0;
	private Entry enter;
	
	public Scheduler(Entry enter, int[] parents, Graf graf, Vector<Package> vector) {
		this.parents = parents;
		this.graf = graf;
		cars = new Car[enter.getCars()];
		this.vector = vector;
		this.charge = enter.getCharge();
	}
	
	public void start() {
		Visual visual = new Visual(vector);
		
		for(int i =0; i < cars.length; i++) { 		// tworzenie odpowiedniej ilosci watkow
			cars[i] = new Car(i, charge, vector, parents, graf, visual);
		}
		
		startScheduling();
		System.out.println("-------------KONIEC---------------");
	}
	
	public int getParent(int i) {
		return parents[i];
	}
	
	public void startScheduling() {
		int delivered = 0;
		while(true) {
			for(int i =0; i < cars.length; i++) { 	//uruchamianie kazdego z nich
				if(delivered <= vector.size()) {
					System.out.println("samochod nr : " +i);
					cars[i].run();
					delivered = delivered + cars[i].getDelivered();
					//System.out.println(delivered + "----- "+vector.size());
				} else {
					return ;
				}
			}	
		}
	}
}
