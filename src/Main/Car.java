package Main;

import java.util.Vector;
import Silngletons.Order;
import Parts.Package;


public class Car extends Thread {
	private int capacity;
	private int id;
	private Vector<Package> vector = new Vector<Package>();
	
	public Car(int id, int capacity, Vector<Package> vector) {
		this.capacity = capacity;
		this.id = id;
		this.vector = vector;
	}
	
	@Override
	public void run() {
		int maxPr  = 0;
		int nr = 0;
		int load =0;
		
		for(int i = 0; i < vector.size(); i++) {
			if(vector.elementAt(i).getState() == false) {
				if(vector.elementAt(i).getPriority() > maxPr) {
					maxPr = vector.elementAt(i).getPriority();
					nr = i;
				}
			}
		}
		
		if(load < this.capacity) {
			
		}
		
	}
	
	
}
