package types;

public class TRef implements IType {

	private IType type;

	public TRef(IType type) {
		this.type = type;
	}
	
	public String toString() {
		return "ref " + type;
	}

	public IType getRefType() {
		return type;
	}

}
