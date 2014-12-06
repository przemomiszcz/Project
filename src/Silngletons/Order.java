package Silngletons;

import java.util.Vector;
import Parts.Package;

public class Order {
	private static Order instance = null;
	private Vector<Package> orders = null;
	
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
	
	public void addPackage(Package p) {
		orders.addElement(p);
	}
	
	@Override
	public String toString() {
		return orders.toString();
	}
}
