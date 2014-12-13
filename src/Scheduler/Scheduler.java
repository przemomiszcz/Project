package Scheduler;


import java.util.Vector;

import Silngletons.Graph;
import Parts.Package;
import Silngletons.Order;

public class Scheduler {
	private int[] parents;
	private Graph graph;
	private Car[] cars;
	private int capacity = 0;
	private int amount = 0;
	private Vector<Package> vector; 
	//private Thread[] threads ;
	
	public Scheduler(int amount, int capacity, int[] parents, Graph graph, Vector<Package> vector) {
		this.parents = parents;
		this.graph = graph;
		cars = new Car[amount];
		this.capacity = capacity;
		this.vector = vector;
		//threads = new Thread[amount];
	}
	
	public void start() {
		for(int i =0; i < cars.length; i++) {
			cars[i] = new Car(i, capacity, vector, parents, graph);
		}
		for(int i =0; i < cars.length; i++) {
			System.out.println("samochod nr : " +i);
			cars[i].run();
		}
	}
	
	public int getParent(int i) {
		return parents[i];
	}
}
