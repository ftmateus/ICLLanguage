package types;

public class TBool implements IType {

	public static final TBool INSTANCE = new TBool();

	private TBool() {}

	public String toString() {
		return "bool";
	}

}
