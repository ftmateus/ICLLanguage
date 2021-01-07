package exceptions;

import java.util.List;

import types.IType;

public class TypeErrorException extends SyntaxException {

	private static final long serialVersionUID = 1L;

	public TypeErrorException(String symbol, String type) {
		super(symbol + " : expression of type " + type + " was expected.");
	}
	
	public TypeErrorException(String name, List<IType> original, List<IType> types) {
		super(getError(name, original,types));
	}

	private static String getError(String name, List<IType> original, List<IType> types) {
		String res = "The method " + name + "(";
		for(IType t : original)
			res += t.toString() + ",";
		res = res.substring(0,res.length()-1);
		res += ") is not applicable for the arguments (";
		for(IType t : types)
			res += t.toString() + ",";
		res = res.substring(0,res.length()-1);
		res += ")";
		return res;
	}
	
	public TypeErrorException(String name, String res, String original) {
		super(name + " : Cannot convert from " + res + " to " + original );
	}
	
}
    
