package ast.methods;

import java.io.IOException;

import ast.ASTNode;
import env.CodeBlock;
import env.Coordinates;
import env.Environment;
import types.*;
import values.IValue;

public class ASTPrint implements ASTNode {

	private ASTNode t;
	private IType type;

	public ASTPrint(ASTNode t) {
		this.t = t;
	}

	@Override
	public IValue eval(Environment<IValue> env) {
		IValue val = t.eval(env);
		System.out.println(val);
		return val;

	}

	@Override
	public void compile(CodeBlock c, Environment<Coordinates> env) throws IOException {
		t.compile(c, env);
		c.emitF("dup");
		if (type.toString().startsWith("ref"))
			c.emitF("invokestatic functions/printO(Ljava/lang/Object;)V\t\t; Print reference");
		else
			c.emitF("invokestatic functions/printI(I)V\t\t\t\t\t\t; Print value");
	}

	@Override
	public IType typecheck(Environment<IType> env) {
		type = t.typecheck(env);
		return type;
	}
	

}
