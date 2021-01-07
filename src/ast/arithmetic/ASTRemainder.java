package ast.arithmetic;

import ast.ASTNode;
import env.Environment;
import values.IValue;
import values.VInt;

public class ASTRemainder extends ASTArithmetic 
{

	public ASTRemainder(ASTNode l, ASTNode r) {
		super(l, r, "irem", "%");
	}

	public IValue eval(Environment<IValue> env) {
		int n1 = ((VInt) lhs.eval(env)).get();
		int n2 = ((VInt) rhs.eval(env)).get();
		return new VInt(n1 % n2);
	}
}


