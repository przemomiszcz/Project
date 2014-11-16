
public class Package {
	private int nr;
	private int base;
	private int target;
	private String name;
	private int priority;
	
	public Package(int nr, int base, int target, String name, int priority) {
		nr = this.nr;
		base = this.base;
		target = this.target;
		name = this.name;
		priority = this.priority;
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
	
	@Override
	public String toString() {
		return " number: " + nr + " base: " + base + " target: " + target + " name: " + name + " priority " +priority;
	}
}
