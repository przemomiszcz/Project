package Parts;


public class Package {
	private int nr;
	private int base;
	private int target;
	private String name;
	private int priority;
	private boolean state;
	
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
	
	public void setState() {
		state = true;
	}
	
	@Override
	public String toString() {
		return " number: " + nr + " base: " + base + " target: " + target + " name: " + name + " priority " +priority;
	}
}
