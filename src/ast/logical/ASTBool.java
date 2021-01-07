package ast.logical;

import java.io.IOException;

import ast.ASTNode;
import env.CodeBlock;
import env.Coordinates;
import env.Environment;
import types.TBool;
import types.IType;
import values.IValue;
import values.VBool;

public class ASTBool implements ASTNode {

	private boolean n;

	public ASTBool(boolean n) {
		this.n = n;
	}

	@Override
	public IValue eval(Environment<IValue> env) {
		return new VBool(n);
	}

	@Override
	public void compile(CodeBlock c, Environment<Coordinates> env) throws IOException {
		c.emitF("iconst_" + (n ? 1 : 0));
	}

	@Override
	public IType typecheck(Environment<IType> env) {
		return TBool.INSTANCE;
	}
	
}

