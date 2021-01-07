package ast.logical;

import java.io.IOException;

import ast.ASTNode;
import env.Coordinates;
import env.Environment;
import exceptions.TypeErrorException;
import types.TBool;
import types.IType;
import values.IValue;
import values.VBool;

public class ASTNot implements ASTNode {
	
	private ASTNode t;
	
	public ASTNot(ASTNode t) {
		this.t = t;
	}

	@Override
	public IValue eval(env.Environment<IValue> env) {
		IValue v1 = t.eval(env);
		boolean n1 = ((VBool) v1).get();
		return new VBool(!n1);
	}

	@Override
	public void compile(env.CodeBlock c, env.Environment<Coordinates> env) throws IOException {
		t.compile(c, env);
		String ts = c.getNewLabel();
		String fs = c.getNewLabel();
		c.emitF("ifeq " + ts);
		c.emitF("iconst_0");
		c.emitF("goto " + fs);
		c.emitO(ts + ":");
		c.emitF("iconst_1");
		c.emitO(fs + ":");
		
	}

	@Override
	public IType typecheck(Environment<IType> env) {
		IType t1 = t.typecheck(env);
		if(t1 instanceof TBool) 
			return TBool.INSTANCE;
		else 
			throw new TypeErrorException("~", "bool");
	}

}
