package types;

public interface IType {
	
	
	public static String compileFormatL(String typeStr) {
		if(typeStr.equals("int") || typeStr.equals("bool"))
			return "I";
		else if(typeStr.equals("ref int"))
			return "Lref_int;";
		return "Lref_class;";
	}
	
	public static IType parseType(String typeStr)
	{
		if(typeStr.equals("int"))
			return TInt.INSTANCE;
		if(typeStr.equals("bool"))
			return TBool.INSTANCE;

		String typeCut = typeStr.substring(4);
		IType t = parseType(typeCut);
		return new TRef(t);
	}
	
}
