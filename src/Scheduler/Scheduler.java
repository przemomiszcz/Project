package Scheduler;


import java.util.Vector;

import Silngletons.Entry;
import Silngletons.Graph;
import Parts.Package;
import Silngletons.Order;

public class Scheduler { //odpowiada za uruchamianie watkow-samochodow
	private int[] parents;
	private Graph graph;
	private Car[] cars; //tablica watkow
	private Vector<Package> vector; 
	private int amount = 0;
	private int charge = 0;
	private int all;
	
	public Scheduler(int amount, int charge, int[] parents, Graph graph, Vector<Package> vector) {
		this.parents = parents;
		this.graph = graph;
		cars = new Car[amount];
		this.vector = vector;
		this.charge = charge;
		this.all = 0;
	}
	
	public void start() {
		
		for(int i =0; i < cars.length; i++) { // tworzenie odpowiedniej ilosci watkow
			cars[i] = new Car(i, charge, vector, parents, graph);
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
