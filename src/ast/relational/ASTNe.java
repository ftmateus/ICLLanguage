package ast.relational;

import ast.ASTNode;
import env.Environment;
import values.IValue;
import values.VBool;
import values.VInt;

public class ASTNe extends ASTRelational {

	public ASTNe(ASTNode e1, ASTNode e2) {
		super(e1, e2, "ifne", "!=");
	}

	@Override
	public IValue eval(Environment<IValue> env) {
    	IValue v1 = lhs.eval(env);
		IValue v2 = rhs.eval(env);
		if (v1.getClass().getName().equals(VInt.class.getName())) {
			int n1 = ((VInt) v1).get();
			int n2 = ((VInt) v2).get();
			return new VBool(n1 != n2);
		} else {
			int n1 = ((VBool) v1).get() ? 1 : 0;
			int n2 = ((VBool) v2).get() ? 1 : 0;
			return new VBool(n1 != n2);
		}
	}    
}
