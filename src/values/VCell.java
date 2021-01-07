package values;

public class VCell implements IValue {

	private IValue value;
	
	public VCell(IValue value) {
		this.value = value;
	}
	
	public IValue get() {
		return value;
	}
	
	public void set(IValue newVal) {
		value = newVal;
	}
	
	public String toString() {
		return "Cell[" + value.toString() + "]";
	}
}
