package ast.methods;

import java.io.IOException;

import ast.ASTNode;
import env.CodeBlock;
import env.Coordinates;
import env.Environment;
import types.IType;
import values.IValue;

public class ASTSeq implements ASTNode {
	
	private ASTNode lhs, rhs;
	
	public ASTSeq(ASTNode lhs, ASTNode rhs) {
		this.lhs = lhs;
		this.rhs = rhs;
	}

	@Override
	public IValue eval(Environment<IValue> env) {
		lhs.eval(env);
		return rhs.eval(env);
	}

	@Override
	public void compile(CodeBlock c, Environment<Coordinates> env) throws IOException {
		lhs.compile(c, env);
		c.emitF("pop");
		rhs.compile(c, env);
	}

	@Override
	public IType typecheck(Environment<IType> env) {
		lhs.typecheck(env);
		return rhs.typecheck(env);
	}

}
