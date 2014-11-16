package Silngletons;

import java.util.Vector;
import Parts.*;


public class Graph {
	private static Graph instance = null;
	private Vector<Peak> peaks;
	
	private Graph() {
		peaks = new Vector<>();
	}
	
	public static synchronized Graph getInstance() {
		if(instance == null ) {
			instance = new Graph();
			return instance;
		}
		return instance;
	}
	
	public void addPeak(int i, Peak p) {
		peaks.add(i, p);
	}
	
	public void addEdge(int i, Edge e) {
		peaks.elementAt(i).addEdge(e);
	}
	
	@Override
	public String toString() {
		return peaks.toString();
	}
}
