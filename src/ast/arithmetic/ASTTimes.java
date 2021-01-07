package ast.arithmetic;

import ast.ASTNode;
import env.Environment;
import values.IValue;
import values.VInt;

public class ASTTimes extends ASTArithmetic {

	public ASTTimes(ASTNode l, ASTNode r) {
		super(l,r,"imul", "*");
	}
	
	public IValue eval(Environment<IValue> env) {
		IValue v1 = lhs.eval(env);
		IValue v2 = rhs.eval(env);
		int n1 = ((VInt) v1).get();
		int n2 = ((VInt) v2).get();
		return new VInt(n1 * n2);
	}

	
}

