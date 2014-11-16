package Silngletons;

public class Entry {
	private static Entry enter = null;
	private int cars;
	private int charge;
	
	private Entry(int cars, int charge) {
		cars = this.cars;
		charge = this.charge;
	}
	
	public synchronized Entry getInstance(int ca, int ch) {
		if(enter == null) {
			enter = new Entry(ca, ch);
			return enter;
		}
		return enter;
	}
	
	public int getCars() {
		return cars;
	}
	
	public int getCharge() {
		return charge;
	}
}
