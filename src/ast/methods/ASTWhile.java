package ast.methods;

import java.io.IOException;

import ast.ASTNode;
import env.CodeBlock;
import env.Coordinates;
import env.Environment;
import exceptions.TypeErrorException;
import types.TBool;
import types.IType;
import values.IValue;
import values.VBool;

public class ASTWhile implements ASTNode {

	private ASTNode t1, t2;
	
	public ASTWhile(ASTNode t1, ASTNode t2) {
		this.t1 = t1;
		this.t2 = t2;
	}
	
	@Override
	public IValue eval(Environment<IValue> env) {
		boolean c = ((VBool) t1.eval(env)).get(); 
		IValue result = null; 
		while(c) 
		{ 
			env = env.beginScope(); 
			result = t2.eval(env); 
			c = ((VBool) t1.eval(env)).get(); 
			env = env.endScope(); 
		} 
		return result; 
	}

	@Override
	public void compile(CodeBlock c, Environment<Coordinates> env) throws IOException {
		String w1 = c.getNewLabel();
		String w2 = c.getNewLabel();
		
		c.emitO(w1 + ":");
		t1.compile(c, env);
		c.emitF("ifeq " + w2);
		t2.compile(c, env);
		c.emitF("pop");
		c.emitF("goto " + w1);
		c.emitO(w2 + ":");
		c.emitF("iconst_0");
	}

	@Override
	public IType typecheck(Environment<IType> env) {
		IType t = t1.typecheck(env);
		t2.typecheck(env);
		if (t instanceof TBool)
			return TBool.INSTANCE;
		throw new TypeErrorException("while","bool");
	}

}
