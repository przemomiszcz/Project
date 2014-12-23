package Scheduler;


import java.util.Vector;

import Parts.Package;
import Singletons.Graf;

public class Scheduler { //odpowiada za uruchamianie watkow-samochodow
	private int[] parents;
	private Graf graf;
	private Car[] cars; //tablica watkow
	private Vector<Package> vector; 
	private int amount = 0;
	private int charge = 0;
	
	public Scheduler(int amount, int charge, int[] parents, Graf graf, Vector<Package> vector) {
		this.parents = parents;
		this.graf = graf;
		cars = new Car[amount];
		this.vector = vector;
		this.charge = charge;
	}
	
	public void start() {
		Visual visual = new Visual(vector);
		int delivered =0;
		
		for(int i =0; i < cars.length; i++) { 		// tworzenie odpowiedniej ilosci watkow
			cars[i] = new Car(i, charge, vector, parents, graf, visual);
		}
		while(delivered < vector.size()) { 			//sprawdzam czy ilosc paczek dostarczonych jest mniejsza od poczatkowej ich ilosci
			for(int i =0; i < cars.length; i++) { 	//uruchamianie kazdego z nich
				System.out.println("samochod nr : " +i);
				cars[i].run();
				delivered = delivered + cars[i].getDelivered();
				//System.out.println(delivered + "----- "+vector.size());
			}
		}
		System.out.println("-------------KONIEC---------------");
	}
	
	public int getParent(int i) {
		return parents[i];
	}
	
}
