package Parts;


public class Package {
	private int nr;				//nr paczki
	private int base;			// nr bazy
	private int target;			//nr miasta-celu
	private String name;		//nazwa paczki
	private int priority;		
	private boolean state;		//dostaeczona == true
	
	public Package(int nr, int base, int target, String name, int priority) {
		this.nr = nr;
		this.base = base;
		this.target = target;
		this.name = name;
		this.priority = priority;
		state = false;
	}
	
	public int getNr() {
		return nr;
	}
	
	public int getBase() {
		return base;
	}
	
	public int getTarget() {
		return target;
	}
	
	public String getName() {
		return name;
	}
	
	public int getPriority() {
		return priority;
	}
	
	public boolean getState() {
		return state;
	}
	
	public void setState() {
		state = true;
	}
	
	@Override
	public String toString() {
		return " number: " + nr + " base: " + base + " target: " + target + " name: " + name + " priority " +priority + " state: "+state;
	}
}
