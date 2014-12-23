package Singletons;

import java.util.Vector;
import Parts.Package;

public class Order { 						//singleton zawierajacy paczki
	private static Order instance = null;
	private Vector<Package> orders = null; //vector paczek
	
	private Order() {
		orders = new Vector<Package>();
	}
	
	public static synchronized Order getInstance() {
		if(instance == null) {
			instance = new Order();
			return instance;
		}
		return instance;
	}
	
	public void addPackage(Package p) { //dodawanie paczki
		orders.addElement(p);
	}
	
	@Override
	public String toString() {
		return orders.toString();
	}
	
	public Package getElem(int nr) {
		return orders.elementAt(nr);
	}
	
	public Vector<Package> getOrder() {
		return orders;
	}
	
	public int getPackageAmount() {
		return orders.size();
	}
	
}
