package Main;

import java.util.Vector;

import Parts.Peak;

public class Dijkstra {
	private Vector<Integer> S = new Vector<Integer>(); 
	private Vector<Peak> Q;
	
	public Dijkstra(Vector<Peak> Q){
		this.Q = Q;
	}
	
	public void wypisz() {
		System.out.println(Q);
	}
}
