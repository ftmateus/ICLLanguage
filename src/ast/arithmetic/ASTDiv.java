package ast.arithmetic;

import ast.ASTNode;
import env.Environment;
import values.IValue;
import values.VInt;

public class ASTDiv extends ASTArithmetic {

	public ASTDiv(ASTNode l, ASTNode r) {
		super(l,r, "idiv", "/");
	}
	
	public IValue eval(Environment<IValue> env) {
		VInt v1 = (VInt) lhs.eval(env);
		int n1 = ((VInt) v1).get();
		VInt v2 = (VInt) rhs.eval(env);
		int n2 = ((VInt) v2).get();
		return new VInt(n1 / n2);
	}



}

