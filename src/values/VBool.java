package values;

public class VBool implements IValue {

	private boolean value;
	
	public VBool(boolean v) {
		this.value = v;
	}
	
	public boolean get() {
		return value;
	}
	
	public String toString() {
		return Boolean.toString(value);
	}
}
