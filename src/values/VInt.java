package values;

public class VInt implements IValue {

	private int value;
	
	public VInt(int v) {
		value = v;
	}
	
	public int get() {
		return value;
	}
	
	public String toString() {
		return Integer.toString(value);
	}
	

}
