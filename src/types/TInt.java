package types;

public class TInt implements IType {
	
	public static final TInt INSTANCE = new TInt();
	

	private TInt() {
		
	}
	
	public String toString() {
		return "int";
	}
	
}
