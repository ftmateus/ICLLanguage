package ast.logical;

import ast.ASTNode;
import env.Environment;
import values.IValue;
import values.VBool;

public class ASTAnd extends ASTLogical {
	
	private ASTNode t1, t2;
	
	public ASTAnd(ASTNode t1, ASTNode t2) {
		super(t1,t2,"iand","&&");
	}

	@Override
	public IValue eval(Environment<IValue> env) {
		IValue v1 = t1.eval(env);
		IValue v2 = t2.eval(env);
		boolean n1 = ((VBool) v1).get();
		boolean n2 = ((VBool) v2).get();
		return new VBool(n1 && n2);
	}

}
