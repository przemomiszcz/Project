package Scheduler;

import java.util.Vector;
import Silngletons.Order;
import Parts.Package;


public class Car extends Thread {
	private int capacity;
	private int id;
	private Vector<Package> vector = new Vector<Package>();
	private int cost;
	
	public Car(int id, int capacity, Vector<Package> vector) {
		this.capacity = capacity;
		this.id = id;
		this.vector = vector;
		this.cost =0;
	}
	
	@Override
	public void run() {
		int maxPr  = 0;
		int nr = 0;
		int load =0;
		int target = 0;
		int tmp=0;
		
		for(int i = 0; i < vector.size(); i++) {
			if(vector.elementAt(i).getState() == false) {
				if(vector.elementAt(i).getPriority() > maxPr) {
					maxPr = vector.elementAt(i).getPriority();
					nr = i;
					load = 1;
				}
			}
		}
		
		vector.elementAt(nr).setState();
		
		if(load < this.capacity) {
			target = vector.elementAt(nr).getTarget();
			for(int i = 0; i < vector.size(); i++) {
				if(vector.elementAt(i).getTarget() == target) {
					if(vector.elementAt(i).getState() == false)
						vector.elementAt(i).setState();
						load++;
				} else if(scheduler.getParent(nr) != -1){
					tmp = nr;
					if(vector.elementAt(i).getTarget() == scheduler.getParent(tmp)) {
						if(vector.elementAt(i).getState() == false) {
							load++;
							vector.elementAt(i).setState();
						}
					}
				}
			}
		} 
		
	}
	
	
}
