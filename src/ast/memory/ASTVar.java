package ast.memory;

import java.io.IOException;

import ast.ASTNode;
import env.CodeBlock;
import env.Coordinates;
import env.Environment;
import types.IType;
import types.TRef;
import values.IValue;
import values.VCell;

public class ASTVar implements ASTNode {

	private ASTNode t;
	private IType type;
	
	public ASTVar(ASTNode t) {
		this.t = t;
	}
	
	@Override
	public IValue eval(Environment<IValue> env) {
		IValue val = t.eval(env);
		return new VCell(val);
	}
	
	@Override
	public void compile(CodeBlock c, Environment<Coordinates> env) throws IOException {
		if(type.toString().equals("int")) { 
			c.emitF("new ref_int");
			c.emitF("dup");
			c.emitF("invokespecial ref_int/<init>()V");
			c.emitF("dup");
			t.compile(c, env);
			c.emitF("putfield ref_int/v I");
		} else {
			c.emitF("new ref_class");
			c.emitF("dup");
			c.emitF("invokespecial ref_class/<init>()V");
			c.emitF("dup");
			t.compile(c,  env);
			c.emitF("putfield ref_class/v Ljava/lang/Object;");
		}
	}

	@Override
	public IType typecheck(Environment<IType> env) {
		type = t.typecheck(env);
		return new TRef(type);
	}

}
