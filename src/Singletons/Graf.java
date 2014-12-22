package Singletons;

import java.util.Vector;
import Parts.*;


public class Graf {					//glowny graf zawierajacy miasta
	private static Graf instance = null;
	private Vector<Peak> peaks;			//vector miast
	
	private Graf() {
		peaks = new Vector<>();
	}
	
	public static synchronized Graf getInstance() {
		if(instance == null ) {
			instance = new Graf();
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
	
	public Vector<Peak> getPeaks() {
		return peaks;
	}
	
 	public int countPeaks() { // zwraca ilosc peakow
 		int tmp=0;
 		for(int i =0; i <peaks.size(); i++) {
 			tmp++;
 		}
 		return tmp;
 	}
 	
 	public int countConcretTargets(int nr) { //zwraca ilosc edgy konkretnego miasta
 		return peaks.elementAt(nr).getEdges().size();
 	}
}
