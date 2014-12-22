package Scheduler;


import java.util.Vector;

import Parts.Package;
import Singletons.Entry;
import Singletons.Graf;
import Singletons.Order;

public class Scheduler { //odpowiada za uruchamianie watkow-samochodow
	private int[] parents;
	private Graf graf;
	private Car[] cars; //tablica watkow
	private Vector<Package> vector; 
	private int amount = 0;
	private int charge = 0;
	private int all;
	
	public Scheduler(int amount, int charge, int[] parents, Graf graf, Vector<Package> vector) {
		this.parents = parents;
		this.graf = graf;
		cars = new Car[amount];
		this.vector = vector;
		this.charge = charge;
		this.all = 0;
	}
	
	public void start() {
		Visual visual = new Visual();
		for(int i =0; i < cars.length; i++) { // tworzenie odpowiedniej ilosci watkow
			cars[i] = new Car(i, charge, vector, parents, graf, visual);
		}
		for(int i =0; i < cars.length; i++) { //uruchamianie kazdego z nich
			System.out.println("samochod nr : " +i);
			cars[i].run();
		}
		
	}
	
	public int getParent(int i) {
		return parents[i];
	}
	
	public void allPlusOne() {
		this.all++;
	}
}
